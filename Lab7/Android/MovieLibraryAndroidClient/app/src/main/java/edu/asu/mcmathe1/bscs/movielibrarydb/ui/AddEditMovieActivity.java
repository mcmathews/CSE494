package edu.asu.mcmathe1.bscs.movielibrarydb.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.Arrays;

import edu.asu.mcmathe1.bscs.movielibrarydb.MovieDescription;
import edu.asu.mcmathe1.bscs.movielibrarydb.MovieLibraryDaoFactory;
import edu.asu.mcmathe1.bscs.movielibrarydb.R;

/**
 * Copyright 2016 Michael Mathews
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Michael Mathews    mailto:Michael.C.Mathews@asu.edu
 * @version 3/28/2016
 */
public class AddEditMovieActivity extends AppCompatActivity {

	private EditText titleET;
	private EditText yearET;
	private EditText ratedET;
	private EditText releasedET;
	private EditText runtimeET;
	private Spinner genreSpinner;
	private EditText actorsET;
	private EditText plotET;

	private int movieIndex;
	private String movieTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_movie);

		Bundle extras = getIntent().getExtras();
		movieTitle = null;
		if (extras != null) {
			movieIndex = extras.getInt(LibraryActivity.MOVIE_INDEX_KEY, -1);
			movieTitle = extras.getString(LibraryActivity.MOVIE_TITLE_KEY);
		} else {
			movieIndex = -1;
		}

		titleET = (EditText) findViewById(R.id.titleET);
		yearET = (EditText) findViewById(R.id.yearET);
		ratedET = (EditText) findViewById(R.id.ratedET);
		releasedET = (EditText) findViewById(R.id.releasedET);
		runtimeET = (EditText) findViewById(R.id.runtimeET);
		genreSpinner = (Spinner) findViewById(R.id.genreSpinner);
		actorsET = (EditText) findViewById(R.id.actorsET);
		plotET = (EditText) findViewById(R.id.plotET);

		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		genreSpinner.setAdapter(spinnerAdapter);

		if (movieTitle != null) {
			new GetMovieAsyncTask().execute(movieTitle);
		}
	}

	public void handleSave(View view) {
		Log.w(getClass().getSimpleName(), "Save button clicked");

		MovieDescription movie = new MovieDescription();
		movie.setTitle(titleET.getText().toString());
		movie.setYear(yearET.getText().length() > 0 ? Integer.parseInt(yearET.getText().toString()) : -1);
		movie.setRated(ratedET.getText().toString());
		movie.setReleased(releasedET.getText().toString());
		movie.setRuntime(runtimeET.getText().toString());
		movie.setGenres(Arrays.asList((String) genreSpinner.getSelectedItem()));
		movie.setActors(Arrays.asList(actorsET.getText().toString()));
		movie.setPlot(plotET.getText().toString());

		if (movieIndex > -1) {
			new EditMovieAsyncTask().execute(new Pair<>(movieTitle, movie));
		} else {
			new AddMovieAsyncTask().execute(movie);
		}

		Intent resultIntent = new Intent();
		resultIntent.putExtra(LibraryActivity.MOVIE_TITLE_KEY, movie.getTitle());
		resultIntent.putExtra(LibraryActivity.MOVIE_INDEX_KEY, movieIndex);

		setResult(RESULT_OK, resultIntent);
		finish();
	}

	public void handleDelete(View view) {
		Log.w(getClass().getSimpleName(), "Delete button clicked");

		new DeleteMovieAsyncTask().execute(movieTitle);

		Intent resultIntent = new Intent();
		resultIntent.putExtra(LibraryActivity.MOVIE_INDEX_KEY, movieIndex);

		setResult(RESULT_OK, resultIntent);
		finish();
	}

	private class GetMovieAsyncTask extends AsyncTask<String, Void, MovieDescription> {

		@Override
		protected MovieDescription doInBackground(String... params) {
			try {
				return MovieLibraryDaoFactory.getInstance().getDao().get(params[0]);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(MovieDescription movie) {
			super.onPostExecute(movie);

			Log.w(getClass().getSimpleName(), "postExecute called: " + movie);

			if (movie != null) {
				titleET.setText(movie.getTitle());
				if (movie.getYear() >= 0) {
					yearET.setText(String.format("%d", movie.getYear()));
				}
				ratedET.setText(movie.getRated());
				releasedET.setText(movie.getReleased());
				runtimeET.setText(movie.getRuntime());
				genreSpinner.setSelection(((ArrayAdapter<String>) genreSpinner.getAdapter()).getPosition(movie.getGenres().get(0)));

				StringBuilder actors = new StringBuilder();
				boolean first = true;
				for (String actor : movie.getActors()) {
					if (!first) {
						actors.append(", ");
					} else {
						first = false;
					}
					actors.append(actor);
				}
				actorsET.setText(actors.toString());
				plotET.setText(movie.getPlot());
			}
		}
	}

	private class AddMovieAsyncTask extends AsyncTask<MovieDescription, Void, Void> {

		@Override
		protected Void doInBackground(MovieDescription... params) {
			try {
				MovieLibraryDaoFactory.getInstance().getDao().add(params[0]);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return null;
		}
	}

	private class EditMovieAsyncTask extends AsyncTask<Pair<String, MovieDescription>, Void, Void> {

		@Override
		protected Void doInBackground(Pair<String, MovieDescription>... params) {
			try {
				MovieLibraryDaoFactory.getInstance().getDao().edit(params[0].first, params[0].second);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return null;
		}
	}

	private class DeleteMovieAsyncTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			try {
				MovieLibraryDaoFactory.getInstance().getDao().remove(params[0]);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return null;
		}
	}
}

package edu.asu.mcmathe1.bscs.movielibraryandroidclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2016 Michael Mathews
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Michael Mathews    mailto:Michael.C.Mathews@asu.edu
 * @version 2/23/2016
 */
public class LibraryActivity extends AppCompatActivity {

	public static final int ADD_EDIT_MOVIE_REQUEST_CODE = 4305;
	public static final String MOVIE_TITLE_KEY = "movie-title";
	public static final String MOVIE_INDEX_KEY = "movie-index";

	private RecyclerView libraryView;
	private List<String> movieTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

	    new GetTitlesAsyncTask().execute();

	    libraryView = (RecyclerView) findViewById(R.id.movie_recycler);
	    libraryView.setLayoutManager(new LinearLayoutManager(this));
    }

	public void handleMovieClick(View view) {
		Log.w(getClass().getSimpleName(), "Movie clicked");

		String titleClicked = ((TextView) view.findViewById(R.id.movie_list_title)).getText().toString();

		int i;
		for (i = 0; i < movieTitles.size(); i++) {
			if (movieTitles.get(i).equalsIgnoreCase(titleClicked)) {
				break;
			}
		}

		Intent addEditIntent = new Intent(this, AddEditMovieActivity.class);
		addEditIntent.putExtra(MOVIE_TITLE_KEY, movieTitles.get(i));
		addEditIntent.putExtra(MOVIE_INDEX_KEY, i);

		startActivityForResult(addEditIntent, ADD_EDIT_MOVIE_REQUEST_CODE);
	}

	public void handleReset(View view) {
		Log.w(getClass().getSimpleName(), "Reset button clicked");

		new ResetLibraryAsyncTask().execute();
	}

	public void handleLibrarySave(View view) {
		Log.w(getClass().getSimpleName(), "Save button clicked");

		new SaveLibraryAsyncTask().execute();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == RESULT_OK) {
			if (requestCode == ADD_EDIT_MOVIE_REQUEST_CODE) {
				Bundle extras = intent.getExtras();

				int movieIndex = extras.getInt(MOVIE_INDEX_KEY, -1);
				String movieTitle = extras.getString(MOVIE_TITLE_KEY);

				Log.w(getClass().getSimpleName(), "onActivityResult called with result: " + movieTitle);

				if (movieTitle != null) {
					if (movieIndex >= 0) {
						movieTitles.set(movieIndex, movieTitle);
						libraryView.getAdapter().notifyItemChanged(movieIndex);
					} else {
						movieTitles.add(movieTitle);
						libraryView.getAdapter().notifyItemInserted(libraryView.getAdapter().getItemCount());
					}
				} else {
					if (movieIndex >= 0) {
						movieTitles.remove(movieIndex);
						libraryView.getAdapter().notifyItemRemoved(movieIndex);
					}
				}
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.library_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.w(getClass().getSimpleName(), "called onOptionsItemSelected: " + item.getTitle());

		if (item.getItemId() == R.id.action_create) {
			startActivityForResult(new Intent(this, AddEditMovieActivity.class), ADD_EDIT_MOVIE_REQUEST_CODE);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private class GetTitlesAsyncTask extends AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... params) {
			try {
				return MovieLibraryDaoFactory.getInstance(LibraryActivity.this).getTitles();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(List<String> titles) {
			super.onPostExecute(titles);

			Log.w(getClass().getSimpleName(), "postExecute called: " + titles);

			movieTitles = titles;
			libraryView.setAdapter(new MovieRecyclerAdapter(movieTitles));
		}
	}

	private class ResetLibraryAsyncTask extends AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... params) {
			try {
				MovieLibraryDao dao = MovieLibraryDaoFactory.getInstance(LibraryActivity.this);
				dao.reset();
				return dao.getTitles();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(List<String> titles) {
			super.onPostExecute(titles);

			Log.w(getClass().getSimpleName(), "ResetLibraryAsyncTask finished: " + titles);

			movieTitles = titles;
			libraryView.setAdapter(new MovieRecyclerAdapter(movieTitles));
		}
	}

	private class SaveLibraryAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				return MovieLibraryDaoFactory.getInstance(LibraryActivity.this).save();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}

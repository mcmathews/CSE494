package edu.asu.bscs.mcmathe1.movielibraryandroid.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.asu.bscs.mcmathe1.movielibraryandroid.MovieDescription;
import edu.asu.bscs.mcmathe1.movielibraryandroid.R;
import edu.asu.bscs.mcmathe1.movielibraryandroid.dao.MovieLibraryDaoFactory;

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
 * @version 4/26/16
 */
public class LibraryActivity extends AppCompatActivity {

	public static final int ADD_EDIT_MOVIE_REQUEST_CODE = 4305;
	public static final int SEARCH_OMBD_REQUEST_CODE = 8456;
	public static final String MOVIE_TITLE_KEY = "movie-title";
	public static final String MOVIE_INDEX_KEY = "movie-index";
	public static final String MOVIE_FILENAME_KEY = "movie-filename";

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == RESULT_OK) {
			if (requestCode == ADD_EDIT_MOVIE_REQUEST_CODE) {
				Bundle extras = intent.getExtras();

				int movieIndex = extras.getInt(MOVIE_INDEX_KEY, -1);
				String movieTitle = extras.getString(MOVIE_TITLE_KEY);

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
			} else if (requestCode == SEARCH_OMBD_REQUEST_CODE) {
				Bundle extras = intent.getExtras();

				String movieTitle = extras.getString(MOVIE_TITLE_KEY);
				movieTitles.add(movieTitle);
				libraryView.getAdapter().notifyItemInserted(libraryView.getAdapter().getItemCount());
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
		if (item.getItemId() == R.id.action_create) {
			startActivityForResult(new Intent(this, AddEditMovieActivity.class), ADD_EDIT_MOVIE_REQUEST_CODE);

			return true;
		} else if (item.getItemId() == R.id.action_search) {
			startActivityForResult(new Intent(this, SearchOmdbActivity.class), SEARCH_OMBD_REQUEST_CODE);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private class LibraryMovieClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			String titleClicked = ((TextView) view.findViewById(R.id.movie_list_title)).getText().toString();

			int i;
			for (i = 0; i < movieTitles.size(); i++) {
				if (movieTitles.get(i).equalsIgnoreCase(titleClicked)) {
					break;
				}
			}

			Intent addEditIntent = new Intent(LibraryActivity.this, AddEditMovieActivity.class);
			addEditIntent.putExtra(MOVIE_TITLE_KEY, movieTitles.get(i));
			addEditIntent.putExtra(MOVIE_INDEX_KEY, i);

			startActivityForResult(addEditIntent, ADD_EDIT_MOVIE_REQUEST_CODE);
		}
	}

	private class GetTitlesAsyncTask extends AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... params) {
			try {
				List<String> localTitles = MovieLibraryDaoFactory.getInstance().getLocalDao().getTitles();
				List<String> remoteTitles = MovieLibraryDaoFactory.getInstance().getRemoteDao().getTitles();
				Set<String> remoteTitlesSet = new LinkedHashSet<>(remoteTitles);
				remoteTitlesSet.removeAll(localTitles);
				List<String> allTitles = new ArrayList<>(localTitles);
				for (String title : remoteTitlesSet) {
					allTitles.add(title);
					MovieDescription movie = MovieLibraryDaoFactory.getInstance().getRemoteDao().get(title);
					MovieLibraryDaoFactory.getInstance().getLocalDao().add(movie);
				}

				return allTitles;

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(List<String> titles) {
			super.onPostExecute(titles);

			movieTitles = titles;
			libraryView.setAdapter(new MovieRecyclerAdapter(movieTitles, new LibraryMovieClickListener()));
		}
	}
}

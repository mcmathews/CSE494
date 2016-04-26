package edu.asu.bscs.mcmathe1.movielibraryandroid.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.asu.bscs.mcmathe1.movielibraryandroid.MovieDescription;
import edu.asu.bscs.mcmathe1.movielibraryandroid.dao.MovieLibraryDaoFactory;
import edu.asu.bscs.mcmathe1.movielibraryandroid.OmdbApiClient;
import edu.asu.bscs.mcmathe1.movielibraryandroid.R;

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
public class SearchOmdbActivity extends AppCompatActivity {

	private List<String> searchResults;
	private RecyclerView searchResultsView;
	private TextView noResultsView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_omdb);

		searchResults = new ArrayList<>();

		searchResultsView = (RecyclerView) findViewById(R.id.search_recycler);
		searchResultsView.setLayoutManager(new LinearLayoutManager(this));
		searchResultsView.setAdapter(new MovieRecyclerAdapter(searchResults, new SearchResultClickListener()));

		noResultsView = (TextView) findViewById(R.id.noResults);
	}

	public void handleSearch(View view) {
		new SearchOmdbAsyncTask().execute(((TextView) findViewById(R.id.searchText)).getText().toString());
	}

	private class SearchResultClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			String resultText = ((TextView) view.findViewById(R.id.movie_list_title)).getText().toString();
			Matcher titleMatcher = Pattern.compile("^(.*) \\(\\d{4}\\)$").matcher(resultText);
			if (!titleMatcher.matches()) {
				throw new RuntimeException("title did not match");
			}
			String title = titleMatcher.group(1);

			Log.w(getClass().getSimpleName(), "\"" + title + "\" selected");
			new ImportMovieAsyncTask().execute(title);

			Intent resultIntent = new Intent();
			resultIntent.putExtra(LibraryActivity.MOVIE_TITLE_KEY, title);

			setResult(RESULT_OK, resultIntent);
			finish();
		}
	}

	private class SearchOmdbAsyncTask extends AsyncTask<String, Void, List<Pair<String, Integer>>> {

		@Override
		protected List<Pair<String, Integer>> doInBackground(String... params) {
			return new OmdbApiClient().search(params[0]);
		}

		@Override
		protected void onPostExecute(List<Pair<String, Integer>> searchResults) {
			super.onPostExecute(searchResults);

			SearchOmdbActivity.this.searchResults.clear();

			if (!searchResults.isEmpty()) {
				noResultsView.setVisibility(View.GONE);
				for (Pair<String, Integer> result : searchResults) {
					Log.w(getClass().getSimpleName(), "Search result: " + result.first);
					SearchOmdbActivity.this.searchResults.add(result.first + " (" + result.second + ")");
				}
			} else {
				noResultsView.setVisibility(View.VISIBLE);
			}

			searchResultsView.getAdapter().notifyDataSetChanged();
		}
	}

	private class ImportMovieAsyncTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			try {
				MovieDescription movie = new OmdbApiClient().get(params[0]);
				MovieLibraryDaoFactory.getInstance().getLocalDao().add(movie);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			return null;
		}
	}
}

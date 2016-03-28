package edu.asu.mcmathe1.bscs.movielibrarydb.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.asu.mcmathe1.bscs.movielibrarydb.MovieDescription;
import edu.asu.mcmathe1.bscs.movielibrarydb.MovieLibraryDaoFactory;
import edu.asu.mcmathe1.bscs.movielibrarydb.MovieRecyclerAdapter;
import edu.asu.mcmathe1.bscs.movielibrarydb.OmdbApiClient;
import edu.asu.mcmathe1.bscs.movielibrarydb.R;

public class SearchOmdbActivity extends AppCompatActivity {

	private RecyclerView searchResultsView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_omdb);

		searchResultsView = (RecyclerView) findViewById(R.id.search_recycler);
		searchResultsView.setLayoutManager(new LinearLayoutManager(this));
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

			List<String> displayResults = new ArrayList<>();
			for (Pair<String, Integer> result : searchResults) {
				Log.w(getClass().getSimpleName(), "Search result: " + result.first);
				displayResults.add(result.first + " (" + result.second + ")");
			}
			searchResultsView.setAdapter(new MovieRecyclerAdapter(displayResults, new SearchResultClickListener()));
		}
	}

	private class ImportMovieAsyncTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			try {
				MovieDescription movie = new OmdbApiClient().get(params[0]);
				MovieLibraryDaoFactory.getInstance().getDao().add(movie);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			return null;
		}
	}
}

package edu.asu.mcmathe1.bscs.movielibraryandroidclient;

import android.content.Intent;
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
import java.io.InputStreamReader;

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
	public static final String MOVIE_DESCRIPTION_KEY = "movie-description";
	public static final String MOVIE_INDEX_KEY = "movie-index";

	private RecyclerView libraryView;
	private MovieLibrary library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.library)))) {
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
		        sb.append(line.trim());
	        }

	        JSONObject jo = new JSONObject(sb.toString());
	        JSONArray jsonLibrary = jo.getJSONArray("library");
			library = new MovieLibrary();
	        for (int i = 0; i < jsonLibrary.length(); i++) {
		        library.getMovieDescriptions().add(new MovieDescription(jsonLibrary.getJSONObject(i)));
	        }

	        libraryView = (RecyclerView) findViewById(R.id.movie_recycler);
	        libraryView.setAdapter(new MovieRecyclerAdapter(library));
	        libraryView.setLayoutManager(new LinearLayoutManager(this));

        } catch (Exception e) {
	        throw new RuntimeException("Uh oh", e);
        }
    }

	public void handleMovieClick(View view) {
		Log.w(getClass().getSimpleName(), "Movie clicked");

		String titleClicked = ((TextView) view.findViewById(R.id.movie_list_title)).getText().toString();

		int i;
		for (i = 0; i < library.getMovieDescriptions().size(); i++) {
			if (library.getMovieDescriptions().get(i).getTitle().equalsIgnoreCase(titleClicked)) {
				break;
			}
		}

		Intent addEditIntent = new Intent(this, AddEditMovieActivity.class);
		addEditIntent.putExtra(MOVIE_DESCRIPTION_KEY, library.getMovieDescriptions().get(i).toJsonString());
		addEditIntent.putExtra(MOVIE_INDEX_KEY, i);

		startActivityForResult(addEditIntent, ADD_EDIT_MOVIE_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == RESULT_OK) {
			if (requestCode == ADD_EDIT_MOVIE_REQUEST_CODE) {
				Bundle extras = intent.getExtras();

				int movieIndex = extras.getInt(MOVIE_INDEX_KEY, -1);
				String movieJson = extras.getString(MOVIE_DESCRIPTION_KEY);

				Log.w(getClass().getSimpleName(), "onActivityResult called with result: " + movieJson);

				if (movieJson != null) {
					MovieDescription movie;
					try {
						movie = new MovieDescription(new JSONObject(movieJson));
					} catch (JSONException e) {
						throw new RuntimeException(e);
					}

					if (movieIndex >= 0) {
						library.getMovieDescriptions().set(movieIndex, movie);
						libraryView.getAdapter().notifyItemChanged(movieIndex);
					} else {
						library.getMovieDescriptions().add(movie);
						libraryView.getAdapter().notifyItemInserted(libraryView.getAdapter().getItemCount());
					}
				} else {
					if (movieIndex >= 0) {
						library.getMovieDescriptions().remove(movieIndex);
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
}

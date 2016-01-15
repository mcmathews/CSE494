package edu.asu.bscs.mcmathe1.moviedescription;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Copyright 2015 Michael Mathews
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
 * @version 1/14/2016
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MovieDescription md = new MovieDescription("{\"Title\":\"The Dark Knight\",\"Year\":\"2008\",\"Rated\":\"PG-13\",\"Released\":\"18 Jul 2008\",\"Runtime\":\"152 min\",\"Genre\":\"Action, Crime, Drama\",\"Director\":\"Christopher Nolan\",\"Writer\":\"Jonathan Nolan (screenplay), Christopher Nolan (screenplay), Christopher Nolan (story), David S. Goyer (story), Bob Kane (characters)\",\"Actors\":\"Christian Bale, Heath Ledger, Aaron Eckhart, Michael Caine\",\"Plot\":\"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.\",\"Language\":\"English, Mandarin\",\"Country\":\"USA, UK\",\"Awards\":\"Won 2 Oscars. Another 141 wins & 126 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg\",\"Metascore\":\"82\",\"imdbRating\":\"9.0\",\"imdbVotes\":\"1,564,829\",\"imdbID\":\"tt0468569\",\"Type\":\"movie\",\"Response\":\"True\"}");
		((TextView) findViewById(R.id.titleView)).append(md.getTitle());
		((TextView) findViewById(R.id.yearView)).append(Integer.toString(md.getYear()));
		((TextView) findViewById(R.id.ratedView)).append(md.getRated());
		((TextView) findViewById(R.id.releasedView)).append(md.getReleased());
		((TextView) findViewById(R.id.runtimeView)).append(md.getRuntime());
		((TextView) findViewById(R.id.genreView)).append(md.getGenre());
		((TextView) findViewById(R.id.actorsView)).append(md.getActors());
		((TextView) findViewById(R.id.plotView)).append(md.getPlot());
		((TextView) findViewById(R.id.jsonView)).append(md.toJsonString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}

package edu.asu.bscs.mcmathe1.moviedescription;

import org.json.JSONException;
import org.json.JSONObject;

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
 * @version 2/2/2016
 */
public class MovieDescription {
	private String title;
	private int year;
	private String rated;
	private String released;
	private String runtime;
	private String genre;
	private String actors;
	private String plot;

	public MovieDescription(String json) {
		try {
			JSONObject jo = new JSONObject(json);
			this.title = jo.getString("Title");
			this.year = Integer.parseInt(jo.getString("Year"));
			this.rated = jo.getString("Rated");
			this.released = jo.getString("Released");
			this.runtime = jo.getString("Runtime");
			this.genre = jo.getString("Genre");
			this.actors = jo.getString("Actors");
			this.plot = jo.getString("Plot");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String toJsonString() {
		try {
			return new JSONObject()
					       .accumulate("Title", title)
					       .accumulate("Year", year)
						   .accumulate("Rated", rated)
						   .accumulate("Released", released)
						   .accumulate("Runtime", runtime)
						   .accumulate("Genre", genre)
						   .accumulate("Actors", actors)
						   .accumulate("Plot", plot)
					       .toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}
}

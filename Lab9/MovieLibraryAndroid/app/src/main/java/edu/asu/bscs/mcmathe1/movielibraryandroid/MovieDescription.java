package edu.asu.bscs.mcmathe1.movielibraryandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class MovieDescription {
    private String title;
    private int year;
    private String rated;
    private String released;
    private String runtime;
    private List<String> genres;
    private List<String> actors;
	private String filename;
    private String plot;

    public MovieDescription() {
    }

    public MovieDescription(JSONObject jo) {
        try {
            this.title = jo.getString("Title");
            try {
                this.year = Integer.parseInt(jo.getString("Year"));
            } catch (JSONException e) {
                this.year = jo.getInt("Year");
            }
            this.rated = jo.getString("Rated");
            this.released = jo.getString("Released");
            this.runtime = jo.getString("Runtime");
            try {
                JSONArray genresJson = jo.getJSONArray("Genre");
                List<String> genres = new ArrayList<>();
                for (int i = 0; i < genresJson.length(); i++) {
                    genres.add(genresJson.getString(i));
                }
                this.genres = genres;
            } catch (JSONException e) {
                this.genres = Arrays.asList(jo.getString("Genre").split(", ?"));
            }
            try {
                JSONArray actorsJson = jo.getJSONArray("Actors");
                List<String> actors = new ArrayList<>();
                for (int i = 0; i < actorsJson.length(); i++) {
                    actors.add(actorsJson.getString(i));
                }
                this.actors = actors;
            } catch (JSONException e) {
                this.actors = Arrays.asList(jo.getString("Actors").split(", ?"));
            }
	        if (jo.has("Filename")) {
		        this.filename = jo.getString("Filename");
	        }
            this.plot = jo.getString("Plot");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJsonString() {
        return toJsonObject().toString();
    }

    public JSONObject toJsonObject() {
        try {
            return new JSONObject()
		            .accumulate("Title", title)
		            .accumulate("Year", year)
		            .accumulate("Rated", rated)
		            .accumulate("Released", released)
		            .accumulate("Runtime", runtime)
		            .accumulate("Genre", genres)
		            .accumulate("Actors", actors)
					.accumulate("Filename", filename)
		            .accumulate("Plot", plot);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}

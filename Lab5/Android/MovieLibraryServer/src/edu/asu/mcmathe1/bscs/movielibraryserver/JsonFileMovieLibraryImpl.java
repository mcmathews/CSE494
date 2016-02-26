package edu.asu.mcmathe1.bscs.movielibraryserver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
 * @version 2/26/2016
 */
public class JsonFileMovieLibraryImpl implements MovieLibrary {

	public Map<String, MovieDescription> library;
	private static final String movieJsonFileName = "movies.json";

	public JsonFileMovieLibraryImpl() {
		library = new LinkedHashMap<>();
		this.reset();
	}

	@Override
	public boolean reset() {
		try (FileInputStream is = new FileInputStream(new File(movieJsonFileName))) {
			library.clear();

			JSONObject jo = new JSONObject(new JSONTokener(is));
			JSONArray libraryArray = jo.getJSONArray("library");
			for (int i = 0; i < libraryArray.length(); i++) {
				MovieDescription movie = new MovieDescription(libraryArray.getJSONObject(i));
				library.put(movie.getTitle(), movie);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return true;
	}

	public boolean save() {
		try (PrintWriter out = new PrintWriter(movieJsonFileName)) {
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			for (Map.Entry<String, MovieDescription> entry : library.entrySet()) {
				array.put(entry.getValue().toJsonObject());
			}
			obj.put("library", array);
			out.println(obj.toString(2));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return true;
	}

	@Override
	public boolean add(MovieDescription movie) {
		library.put(movie.getTitle(), movie);
		return true;
	}

	@Override
	public boolean edit(String title, MovieDescription movie) {
		library.remove(title);
		library.put(movie.getTitle(), movie);
		return true;
	}

	@Override
	public boolean remove(String title) {
		return library.remove(title) != null;
	}

	@Override
	public List<String> getTitles() {
		return Arrays.asList(library.keySet().toArray(new String[library.size()]));
	}

	@Override
	public MovieDescription get(String title) {
		MovieDescription movie = library.get(title);
		return movie != null ? movie : new MovieDescription();
	}

}

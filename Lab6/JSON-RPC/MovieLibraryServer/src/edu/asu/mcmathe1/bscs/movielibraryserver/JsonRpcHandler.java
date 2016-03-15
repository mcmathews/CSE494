package edu.asu.mcmathe1.bscs.movielibraryserver;

import org.json.JSONArray;
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
 * @version 2/26/2016
 */
public class JsonRpcHandler {

	private MovieLibrary library;

	public JsonRpcHandler(MovieLibrary library) {
		this.library = library;
	}

	public String callMethod(String requestStr) {
		JSONObject result = new JSONObject();
		try {
			JSONObject request = new JSONObject(requestStr);
			String method = request.getString("method");
			int id = request.getInt("id");

			JSONArray params = request.optJSONArray("params");

			result.put("id", id);
			result.put("jsonrpc", "2.0");

			if (method.equals("reset")) {
				library.reset();
				result.put("result", true);
				
			} else if (method.equals("save")) {
				boolean saved = library.save();
				result.put("result", saved);
				
			} else if (method.equals("remove")) {
				String title = params.getString(0);
				boolean removed = library.remove(title);
				result.put("result", removed);
				
			} else if (method.equals("add")) {
				JSONObject movieJson = params.getJSONObject(0);
				MovieDescription movie = new MovieDescription(movieJson);
				boolean added = library.add(movie);
				result.put("result", added);

			} else if (method.equals("edit")) {
				String title = params.getString(0);
				JSONObject movieJson = params.getJSONObject(1);
				MovieDescription movie = new MovieDescription(movieJson);
				boolean edited = library.edit(title, movie);
				result.put("result", edited);
				
			} else if (method.equals("get")) {
				String title = params.getString(0);
				MovieDescription movie = library.get(title);
				result.put("result", movie.toJsonObject());
				
			} else if (method.equals("getTitles")) {
				JSONArray resArr = new JSONArray();
				for (String title : library.getTitles()) {
					resArr.put(title);
				}
				result.put("result", resArr);
			}
		} catch (Exception ex) {
			System.out.println("exception in callMethod: " + ex.getMessage());
		}
		System.out.println("returning: " + result.toString());
		return "HTTP/1.0 200 Data follows\nServer:localhost:8080\nContent-Type:text/plain\nContent-Length:" + (result.toString()).length() + "\n\n" + result.toString();
	}
}


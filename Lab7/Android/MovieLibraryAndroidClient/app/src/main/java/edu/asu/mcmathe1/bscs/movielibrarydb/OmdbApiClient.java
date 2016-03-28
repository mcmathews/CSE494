package edu.asu.mcmathe1.bscs.movielibrarydb;

import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * @version 3/28/2016
 */
public class OmdbApiClient {

	private static String URL = "http://www.omdbapi.com/";

	protected JSONObject makeRequest(Map<String, Object> params) throws IOException {
		StringBuilder urlBuilder = new StringBuilder(URL).append("?");
		for (Map.Entry<String, Object> param : params.entrySet()) {
			urlBuilder
					.append(param.getKey())
					.append("=")
					.append(URLEncoder.encode(param.getValue().toString(), "UTF-8"));
		}
		Log.w(getClass().getSimpleName(), "Request to: " + urlBuilder.toString());
		URL url = new URL(urlBuilder.toString());

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.connect();

		StringBuilder responseBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				responseBuilder.append(line).append('\n');
			}
		}

		Log.w(getClass().getSimpleName(), "Server response: " + responseBuilder.toString());
		try {
			return new JSONObject(responseBuilder.toString());
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Pair<String, Integer>> search(String query) {
		Map<String, Object> params = new HashMap<>();
		params.put("s", query);

		try {
			JSONObject response = makeRequest(params);

			List<Pair<String, Integer>> results = new ArrayList<>();
			if (response.getBoolean("Response")) {
				JSONArray searchArray = response.getJSONArray("Search");

				for (int i = 0; i < searchArray.length(); i++) {
					JSONObject result = searchArray.getJSONObject(i);
					if ("movie".equals(result.getString("Type"))) {
						results.add(new Pair<>(result.getString("Title"), Integer.parseInt(result.getString("Year"))));
					}
				}
			}

			return results;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public MovieDescription get(String title) {
		Map<String, Object> params = new HashMap<>();
		params.put("t", title);

		try {
			JSONObject response = makeRequest(params);

			if (response.getBoolean("Response")) {
				return new MovieDescription(response);
			} else {
				return null;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

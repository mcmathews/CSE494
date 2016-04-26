package edu.asu.bscs.mcmathe1.movielibraryandroid.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.asu.bscs.mcmathe1.movielibraryandroid.MovieDescription;

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
public class JsonRpcMovieLibraryDaoImpl implements MovieLibraryDao {

	private static int requestId = 1;

	protected String host;
	protected int port;

	public JsonRpcMovieLibraryDaoImpl(String host, int port) {
		this.host = host;
		this.port = port;
	}

	protected JSONObject makeJsonRpcRequest(String method, Object... params) throws IOException {
		URL url = new URL("http://" + this.host + ":" + this.port);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);

		try (OutputStream out = connection.getOutputStream()) {
			JSONObject jo = new JSONObject();
			jo.put("method", method);
			jo.put("id", requestId++);
			if (params != null) {
				JSONArray paramsArray = new JSONArray();
				for (Object param : params) {
					paramsArray.put(param);
				}
				jo.put("params", paramsArray);
			}

			String jsonStr = jo.toString();
			Log.w(getClass().getSimpleName(), "Request: " + jsonStr);
			out.write(jsonStr.getBytes());

		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

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

	@Override
	public void add(MovieDescription movie) throws IOException {
		makeJsonRpcRequest("add", movie.toJsonObject());
	}

	@Override
	public void edit(String title, MovieDescription movie) throws IOException {
		makeJsonRpcRequest("edit", title, movie.toJsonObject());
	}

	@Override
	public void remove(String title) throws IOException {
		makeJsonRpcRequest("remove", title);
	}

	@Override
	public MovieDescription get(String title) throws IOException {
		try {
			return new MovieDescription(makeJsonRpcRequest("get", title).getJSONObject("result"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String> getTitles() throws IOException {
		JSONObject response = makeJsonRpcRequest("getTitles");
		List<String> titles = new ArrayList<>();
		try {
			JSONArray result = response.getJSONArray("result");
			for (int i = 0; i < result.length(); i++) {
				titles.add(result.getString(i));
			}

			return titles;

		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}

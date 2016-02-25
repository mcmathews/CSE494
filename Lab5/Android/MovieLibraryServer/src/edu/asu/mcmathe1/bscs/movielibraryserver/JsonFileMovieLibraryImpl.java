package edu.asu.mcmathe1.bscs.movielibraryserver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonFileMovieLibraryImpl implements MovieLibrary {

	public Map<String, MovieDescription> library;
	private static final String movieJsonFileName = "movies.json";

	public JsonFileMovieLibraryImpl() {
		library = new ConcurrentHashMap<>();
		this.reset();
	}

	@Override
	public void reset() {
		try (FileInputStream is = new FileInputStream(new File(movieJsonFileName))) {
			library.clear();

			JSONObject jo = new JSONObject(new JSONTokener(is));
			JSONArray libraryArray = jo.getJSONArray("library");
			for (int i = 0; i < libraryArray.length(); i++) {
				MovieDescription movie = new MovieDescription(libraryArray.getJSONObject(i));
				library.put(movie.getTitle(), movie);
			}
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	public boolean save() {
		try (PrintWriter out = new PrintWriter(movieJsonFileName)) {
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			for (Map.Entry<String, MovieDescription> entry : library.entrySet()) {
				array.put(entry.getValue().toJsonString());
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

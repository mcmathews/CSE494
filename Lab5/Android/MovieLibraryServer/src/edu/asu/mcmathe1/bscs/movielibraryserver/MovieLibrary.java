package edu.asu.mcmathe1.bscs.movielibraryserver;

import java.util.List;

public interface MovieLibrary {
	void reset();
	boolean save();
	boolean add(MovieDescription movie);
	boolean remove(String title);
	MovieDescription get(String title);
	List<String> getTitles();
}

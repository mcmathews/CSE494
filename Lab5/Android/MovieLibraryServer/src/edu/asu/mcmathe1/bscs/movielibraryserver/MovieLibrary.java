package edu.asu.mcmathe1.bscs.movielibraryserver;

import java.util.List;

public interface MovieLibrary {
	boolean reset();
	boolean save();
	boolean add(MovieDescription movie);
	boolean edit(String title, MovieDescription movie);
	boolean remove(String title);
	MovieDescription get(String title);
	List<String> getTitles();
}

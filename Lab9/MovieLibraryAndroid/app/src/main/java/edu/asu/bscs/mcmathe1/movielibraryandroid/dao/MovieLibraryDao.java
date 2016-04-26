package edu.asu.bscs.mcmathe1.movielibraryandroid.dao;

import java.io.IOException;
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
public interface MovieLibraryDao {
	void add(MovieDescription movie) throws IOException;
	void edit(String title, MovieDescription movie) throws IOException;
	void remove(String title) throws IOException;
	MovieDescription get(String title) throws IOException;
	List<String> getTitles() throws IOException;
}

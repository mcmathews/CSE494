package edu.asu.mcmathe1.bscs.movielibraryserver;

import java.util.List;

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
public interface MovieLibrary {
	boolean reset();
	boolean save();
	boolean add(MovieDescription movie);
	boolean edit(String title, MovieDescription movie);
	boolean remove(String title);
	MovieDescription get(String title);
	List<String> getTitles();
}

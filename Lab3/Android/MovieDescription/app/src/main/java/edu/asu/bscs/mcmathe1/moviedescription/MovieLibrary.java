package edu.asu.bscs.mcmathe1.moviedescription;

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
 * @version 2/2/2016
 */
public class MovieLibrary {

    private List<MovieDescription> movieDescriptions;

    public List<MovieDescription> getMovieDescriptions() {
        return movieDescriptions;
    }

    public MovieLibrary setMovieDescriptions(List<MovieDescription> movieDescriptions) {
        this.movieDescriptions = movieDescriptions;
        return this;
    }
}

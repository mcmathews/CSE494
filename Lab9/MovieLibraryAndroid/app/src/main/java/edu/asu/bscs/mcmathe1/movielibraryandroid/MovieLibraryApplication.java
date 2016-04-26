package edu.asu.bscs.mcmathe1.movielibraryandroid;

import android.app.Application;

import edu.asu.bscs.mcmathe1.movielibraryandroid.dao.MovieLibraryDaoFactory;

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
public class MovieLibraryApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		MovieLibraryDaoFactory.init(this);
	}
}

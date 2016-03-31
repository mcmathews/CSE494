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
 * @author   Michael Mathews    mailto:Michael.C.Mathews@asu.edu
 * @version 3/16/16
 */

import Foundation

class MovieLibraryDao {
    
    private static var instance: MovieLibraryDao = MovieLibraryDao()
    
    var url: String
    var id: Int = 1
    
    init () {
        url = NSBundle.mainBundle().infoDictionary!["serverUrl"] as! String
    }
    
    static func getInstance() -> MovieLibraryDao {
        return instance
    }
    
    func findByTitle(title: String, callback: (MovieDescription) -> Void) -> Void {
        // TODO
    }
    
    
    func getTitles(callback: ([String]) -> Void) -> Void {
        // TODO
    }
    
    func add(movie: MovieDescription, callback: (Bool) -> Void) {
        // TODO
    }
    
    func edit(title: String, movie: MovieDescription, callback: (Bool) -> Void) {
        // TODO
    }
    
    func remove(title: String, callback: (Bool) -> Void) {
        // TODO
    }
}

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
 * @version 2/11/16
 */

import Foundation

class MovieLibrary {
    var movieDescriptions: [MovieDescription]
    
    init() {
        movieDescriptions = [MovieDescription]()
        
        let jsonPath = NSBundle.mainBundle().pathForResource("movies", ofType: "json")

        if let data: NSData = NSData(contentsOfFile: jsonPath!) {
            do {
                let dict = try NSJSONSerialization.JSONObjectWithData(data, options:.MutableContainers) as? [String:AnyObject]
                
                let movies = (dict!["library"] as? [AnyObject])!
                for movie in movies {
                    self.movieDescriptions.append(MovieDescription(json: movie))
                }
                
            } catch {
                print("unable to convert to dictionary")
                
            }
        }
    }
    
    func findIndexByTitle(title: String) -> Int? {
        for var i = 0; i < movieDescriptions.count; i++ {
            if movieDescriptions[i].title == title {
                return i
            }
        }
        return nil
    }
}
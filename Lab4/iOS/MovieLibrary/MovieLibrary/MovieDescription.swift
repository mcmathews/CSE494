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

class MovieDescription {
    var title: String
    var year: Int
    var rated: String
    var released: String
    var runtime: String
    var genre: String
    var actors: String
    var plot: String
    
    init() {
        title = ""
        year = -1
        rated = ""
        released = ""
        runtime = ""
        genre = ""
        actors = ""
        plot = ""
    }
    
    init(json: AnyObject) {
        self.title = json["Title"] as! String
        self.year = Int(json["Year"] as! String)!
        self.rated = json["Rated"] as! String
        self.released = json["Released"] as! String
        self.runtime = json["Runtime"] as! String
        self.genre = json["Genre"] as! String
        self.actors = json["Actors"] as! String
        self.plot = json["Plot"] as! String
    }
    
    func toJsonString() -> String {
        var jsonStr = "";
        let dict = ["Title": title, "Year": year, "Rated": rated, "Released": released, "Runtime": runtime, "Genre": genre, "Actors": actors, "Plot": plot]
        do {
            let jsonData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions.PrettyPrinted)
            // here "jsonData" is the dictionary encoded in JSON data
            jsonStr = NSString(data: jsonData, encoding: NSUTF8StringEncoding)! as String
        } catch let error as NSError {
            print(error)
        }
        return jsonStr
    }
}
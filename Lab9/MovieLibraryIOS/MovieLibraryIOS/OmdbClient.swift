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
 * @version 4/27/16
 */

import Foundation

class OmdbClient {
    
    private let URL: String = "http://www.omdbapi.com/"
    
    private func makeRequest(params: [String: String], callback: ([String: AnyObject]) -> Void) {
        let queryParams = params.map{"\($0.stringByAddingPercentEncodingWithAllowedCharacters(.URLHostAllowedCharacterSet())!)=\($1.stringByAddingPercentEncodingWithAllowedCharacters(.URLHostAllowedCharacterSet())!)"}.joinWithSeparator("&")
        let request = NSMutableURLRequest(URL: NSURL(string: "\(self.URL)?\(queryParams)")!)
        
        request.HTTPMethod = "GET"
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        
        // task.resume causes the shared session http request to be posted in the background (non-UI Thread)
        // the use of the dispatch_async on the main queue causes the callback to be performed on the UI Thread
        // after the result of the post is received.
        let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
            (data, response, error) -> Void in
            if let e = error {
                callback(["error": e.localizedDescription])
            } else {
                do {
                    let json = try NSJSONSerialization.JSONObjectWithData(data!, options:.MutableContainers) as! [String: AnyObject]
                    dispatch_async(dispatch_get_main_queue(), { callback(json) })
                } catch {
                    callback(["error": "Could not deserialize response"])
                }
            }
        }
        task.resume()
    }
    
    func search(query: String, callback: ([(title: String, year: Int)]) -> Void) {
        makeRequest(["s": query]) {
            response in
            var searchResults: [(title: String, year: Int)] = []
            if (response["Response"] as! String) == "True" {
                for result in response["Search"] as! [[String: AnyObject]] {
                    if (result["Type"] as! String) == "movie" {
                        searchResults.append((title: result["Title"] as! String, year: Int(result["Year"] as! String)!))
                    }
                }
            }
            callback(searchResults)
        }
    }
    
    func get(title: String, callback: (MovieDescription -> Void)) {
        makeRequest(["t": title]) { response in callback(MovieDescription(json: response)) }
    }
}

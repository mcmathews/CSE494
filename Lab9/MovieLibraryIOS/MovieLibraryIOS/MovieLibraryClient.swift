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

class MovieLibraryClient {
    
    private static var instance: MovieLibraryClient = MovieLibraryClient()
    
    var url: String
    var id: Int = 1
    
    init () {
        url = NSBundle.mainBundle().infoDictionary!["serverUrl"] as! String
    }
    
    static func getInstance() -> MovieLibraryClient {
        return instance
    }
    
    private func callJsonRpcMethod(method: String, params: [AnyObject], callback: ([String: AnyObject]) -> Void) {
        let request = NSMutableURLRequest(URL: NSURL(string: self.url)!)
        let payloadDict = ["jsonrpc": "2.0", "id": self.id++, "method": method, "params": params]
        let payload: NSData
        do {
            payload = try NSJSONSerialization.dataWithJSONObject(payloadDict, options: NSJSONWritingOptions(rawValue: 0))
        } catch let error as NSError {
            print(error)
            return
        }
        
        request.HTTPMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        request.HTTPBody = payload
        
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
    
    func findByTitle(title: String, callback: (MovieDescription) -> Void) -> Void {
        callJsonRpcMethod("get", params: [title]) { response in callback(MovieDescription(json: response["result"]!))}
    }
    
    
    func getTitles(callback: ([String]) -> Void) -> Void {
        callJsonRpcMethod("getTitles", params: []) { response in callback(response["result"] as! [String]) }
    }
    
    func add(movie: MovieDescription, callback: (Bool) -> Void) {
        callJsonRpcMethod("add", params: [movie.toDict()]) { response in callback(response["result"] as! Bool) }
    }
    
    func edit(title: String, movie: MovieDescription, callback: (Bool) -> Void) {
        callJsonRpcMethod("edit", params: [title, movie.toDict()]) { response in callback(response["result"] as! Bool) }
    }
    
    func remove(title: String, callback: (Bool) -> Void) {
        callJsonRpcMethod("remove", params: [title]) { response in callback(response["result"] as! Bool) }
    }
    
    func save(callback: (Bool) -> Void) {
        callJsonRpcMethod("save", params: []) { response in callback(response["result"] as! Bool) }
    }
    
    func reset(callback: (Bool) -> Void) {
        callJsonRpcMethod("reset", params: []) { response in callback(response["result"] as! Bool) }
    }
}

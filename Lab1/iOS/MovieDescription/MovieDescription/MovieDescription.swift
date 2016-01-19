//
//  MovieDescription.swift
//  MovieDescription
//
//  Created by mcmathe1 on 1/19/16.
//  Copyright © 2016 mcmathe1. All rights reserved.
//

import Foundation

class MovieDescription {
    var title: String
    var year: Int
    var rated: String
    var released: String
    var runtime: String
    var genre: String
    var actors: [String]
    var plot: String
    
    init (jsonStr: String){
        if let data: NSData = jsonStr.dataUsingEncoding(NSUTF8StringEncoding) {
            do {
                let dict = try NSJSONSerialization.JSONObjectWithData(data, options:.MutableContainers) as?[String:AnyObject]

                self.title = (dict!["Title"] as? String)!
                self.year = (dict!["Year"] as? Int)!
                self.rated = (dict!["Rated"] as? String)!
                self.released = (dict!["Released"] as? String)!
                self.runtime = (dict!["Runtime"] as? String)!
                self.genre = (dict!["Genre"] as? String)!
                self.actors = (dict!["Actors"] as? [String])!
                self.plot = (dict!["Plot"] as? String)!
                
            } catch {
                print("unable to convert to dictionary")
                
            }
        }
    }
    
    func toJsonString() -> String {
        var jsonStr = "";
        let dict = ["name": name, "studentid": studentid, "takes":takes]
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
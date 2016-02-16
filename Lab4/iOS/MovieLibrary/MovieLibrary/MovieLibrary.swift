//
//  MovieLibrary.swift
//  MovieLibrary
//
//  Created by mcmathe1 on 2/11/16.
//  Copyright © 2016 mcmathe1. All rights reserved.
//

import Foundation

class MovieLibrary {
    var movieDescriptions: [MovieDescription]
    
    init(json: String) {
        movieDescriptions = [MovieDescription]()
        
        if let data: NSData = json.dataUsingEncoding(NSUTF8StringEncoding) {
            do {
                let dict = try NSJSONSerialization.JSONObjectWithData(data, options:.MutableContainers) as?[String:AnyObject]
                
                let movies = (dict!["library"] as? [AnyObject])!
                for movie in movies {
                    self.movieDescriptions.append(MovieDescription(json: movie))
                }
                
            } catch {
                print("unable to convert to dictionary")
                
            }
        }
    }
}
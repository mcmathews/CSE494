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
import CoreData
import UIKit

class MovieLibraryDao {
    
    private static var instance: MovieLibraryDao = MovieLibraryDao()
    
    init () {
    }
    
    static func getInstance() -> MovieLibraryDao {
        return instance
    }
    
    private func getContext() -> NSManagedObjectContext {
        return (UIApplication.sharedApplication().delegate as! AppDelegate).managedObjectContext
    }
    
    func findByTitle(title: String) -> MovieDescription {
        let fetchRequest = NSFetchRequest(entityName: "Movie")
        
        var titles: [String] = []
        do {
            let results = try getContext().executeFetchRequest(fetchRequest) as! [NSManagedObject]
            for result in results {
                titles.append(result.valueForKey("title") as! String)
            }
            
            
        } catch let error as NSError {
            print("Could not fetch \(error), \(error.userInfo)")
        }
        
        return titles
    }
    
    func getTitles() -> [String] {
        let fetchRequest = NSFetchRequest(entityName: "Movie")
        
        var titles: [String] = []
        do {
            let results = try getContext().executeFetchRequest(fetchRequest) as! [NSManagedObject]
            for result in results {
                titles.append(result.valueForKey("title") as! String)
            }
            
            
        } catch let error as NSError {
            print("Could not fetch \(error), \(error.userInfo)")
        }
        
        return titles
    }
    
    func add(movie: MovieDescription) {
        // TODO
    }
    
    func edit(title: String, movie: MovieDescription) {
        // TODO
    }
    
    func remove(title: String) {
        // TODO
    }
}

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
        fetchRequest.predicate = NSPredicate(format: "title == %@", title)
        
        let movie = MovieDescription()
        do {
            let results = try getContext().executeFetchRequest(fetchRequest) as! [NSManagedObject]
            let movieResult = results[0]
            
            movie.title = movieResult.valueForKey("title") as! String
            movie.year = movieResult.valueForKey("year") as! Int
            movie.rated = movieResult.valueForKey("rated") as! String
            movie.runtime = movieResult.valueForKey("runtime") as! String
            movie.released = movieResult.valueForKey("released") as! String
            movie.plot = movieResult.valueForKey("plot") as! String
            
            movie.actors = movieResult.mutableSetValueForKey("actors").map{String($0.valueForKey("name")!)}
            movie.genres = movieResult.mutableSetValueForKey("genres").map{String($0.valueForKey("genre")!)}
            
        } catch {
            print("Could not fetch \(error)")
        }
        
        return movie
    }
    
    func getTitles() -> [String] {
        let fetchRequest = NSFetchRequest(entityName: "Movie")
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: "creation", ascending: true)]
        
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
        let context = getContext()
        let movieEntity = NSEntityDescription.entityForName("Movie", inManagedObjectContext: context)
        let movieObj = NSManagedObject(entity: movieEntity!, insertIntoManagedObjectContext: context)
        
        movieObj.setValue(movie.title, forKey: "title")
        movieObj.setValue(movie.year, forKey: "year")
        movieObj.setValue(movie.rated, forKey: "rated")
        movieObj.setValue(movie.runtime, forKey: "runtime")
        movieObj.setValue(movie.released, forKey: "released")
        movieObj.setValue(movie.plot, forKey: "plot")
        movieObj.setValue(NSDate(), forKey: "creation")
        
        var actors: [NSManagedObject] = []
        let actorEntity = NSEntityDescription.entityForName("Actor", inManagedObjectContext: context)
        for actor in movie.actors {
            let actorObj = NSManagedObject(entity: actorEntity!, insertIntoManagedObjectContext: context)
            actorObj.setValue(actor, forKey: "name")
            actors.append(actorObj)
        }
        
        var genres: [NSManagedObject] = []
        let genreEntity = NSEntityDescription.entityForName("Genre", inManagedObjectContext: context)
        for genre in movie.genres {
            let genreObj = NSManagedObject(entity: genreEntity!, insertIntoManagedObjectContext: context)
            genreObj.setValue(genre, forKey: "genre")
            genres.append(genreObj)
        }
        
        movieObj.mutableSetValueForKey("actors").addObjectsFromArray(actors)
        movieObj.mutableSetValueForKey("genres").addObjectsFromArray(genres)
        
        do {
            try context.save()
        } catch let error as NSError {
            print("Could not add movie \(error)")
        }
    }
    
    func edit(title: String, movie: MovieDescription) {
        let context = getContext()
        let fetchRequest = NSFetchRequest(entityName: "Movie")
        fetchRequest.predicate = NSPredicate(format: "title == %@", title)
        
        do {
            let results = try context.executeFetchRequest(fetchRequest) as! [NSManagedObject]
            let movieObj = results[0]
            movieObj.setValue(movie.title, forKey: "title")
            movieObj.setValue(movie.year, forKey: "year")
            movieObj.setValue(movie.rated, forKey: "rated")
            movieObj.setValue(movie.runtime, forKey: "runtime")
            movieObj.setValue(movie.released, forKey: "released")
            movieObj.setValue(movie.plot, forKey: "plot")
            
            var actors: [NSManagedObject] = []
            let actorEntity = NSEntityDescription.entityForName("Actor", inManagedObjectContext: context)
            for actor in movie.actors {
                let actorObj = NSManagedObject(entity: actorEntity!, insertIntoManagedObjectContext: context)
                actorObj.setValue(actor, forKey: "name")
                actors.append(actorObj)
            }
            
            var genres: [NSManagedObject] = []
            let genreEntity = NSEntityDescription.entityForName("Genre", inManagedObjectContext: context)
            for genre in movie.genres {
                let genreObj = NSManagedObject(entity: genreEntity!, insertIntoManagedObjectContext: context)
                genreObj.setValue(genre, forKey: "genre")
                genres.append(genreObj)
            }

            movieObj.mutableSetValueForKey("actors").removeAllObjects()
            movieObj.mutableSetValueForKey("genres").removeAllObjects()
            movieObj.mutableSetValueForKey("actors").addObjectsFromArray(actors)
            movieObj.mutableSetValueForKey("genres").addObjectsFromArray(genres)
        
            try context.save()
        } catch let error as NSError {
            print("Could not add movie \(error)")
        }
    }
    
    func remove(title: String) {
        let context = getContext()
        let fetchRequest = NSFetchRequest(entityName: "Movie")
        fetchRequest.predicate = NSPredicate(format: "title == %@", title)
        
        do {
            let results = try context.executeFetchRequest(fetchRequest) as! [NSManagedObject]
            let movieObj = results[0]
            
            context.deleteObject(movieObj)
        } catch let error as NSError {
            print("Could not add movie \(error)")
        }
    }
}

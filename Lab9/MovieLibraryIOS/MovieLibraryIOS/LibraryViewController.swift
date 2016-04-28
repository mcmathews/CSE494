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

import UIKit

class LibraryViewController: UITableViewController {
    
    var titles: [String] = []

    @IBOutlet var libraryTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        loadTitles()

        self.navigationItem.leftBarButtonItem = self.editButtonItem()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return titles.count
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("MovieCell", forIndexPath: indexPath)
        
        cell.textLabel?.text = titles[indexPath.row]

        return cell
    }

    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }

    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            MovieLibraryDao.getInstance().remove(titles[indexPath.row])
            self.titles.removeAtIndex(indexPath.row)
            self.tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    
    // used for exit segue
    @IBAction func saveMovie(segue: UIStoryboardSegue) {}
    
    // used for exit segue
    @IBAction func selectSearchResult(segue: UIStoryboardSegue) {}

    func loadTitles() {
        MovieLibraryClient.getInstance().getTitles() {
            clientTitles in
            let clientTitlesSet = Set<String>(clientTitles)
            let daoTitles = MovieLibraryDao.getInstance().getTitles()
            let daoTitlesSet = Set<String>(daoTitles)
            
            let newTitles = clientTitlesSet.subtract(daoTitlesSet)
            self.titles = [String](daoTitlesSet)
            for newTitle in newTitles {
                MovieLibraryClient.getInstance().findByTitle(newTitle) {
                    MovieLibraryDao.getInstance().add($0)
                }
                self.titles.append(newTitle)
            }
            
            self.libraryTableView.reloadData()
        }
    }

    // MARK: - Navigation

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "MovieDetail" {
            let movieViewController = segue.destinationViewController as! MovieViewController
            
            let cell = sender! as! UITableViewCell
            movieViewController.titles = titles
            movieViewController.index = libraryTableView.indexPathForCell(cell)!.row
            
        } else if segue.identifier == "AddMovie" {
            (segue.destinationViewController as! AddEditMovieController).titles = titles;
        }
    }

}

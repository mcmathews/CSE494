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
 * @version 2/17/16
 */

import UIKit

class LibraryViewController: UITableViewController {
    
    var library:MovieLibrary = MovieLibrary()

    @IBOutlet var libraryTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        self.navigationItem.leftBarButtonItem = self.editButtonItem()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    /*@IBAction func handleEdit(sender: UIBarButtonItem) {
        self.setEditing(true, animated: true)
    }*/
    
    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return library.movieDescriptions.count
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("MovieCell", forIndexPath: indexPath)
        
        let movie = library.movieDescriptions[indexPath.row]
        cell.textLabel?.text = movie.title
        cell.detailTextLabel?.text = movie.year > 0 ? String(movie.year) : nil

        return cell
    }

    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }

    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            library.movieDescriptions.removeAtIndex(indexPath.row)
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    
    @IBAction func saveMovie(segue: UIStoryboardSegue) {
        if let addEditController = segue.sourceViewController as? AddEditMovieController {
            if let i = addEditController.index {
                let indexPath = NSIndexPath(forRow: i, inSection: 0)
                self.libraryTableView.reloadRowsAtIndexPaths([indexPath], withRowAnimation: .Automatic)
            } else {
                let indexPath = NSIndexPath(forRow: library.movieDescriptions.count - 1, inSection: 0)
                self.libraryTableView.insertRowsAtIndexPaths([indexPath], withRowAnimation: .Automatic)
            }
        }
    }

    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "MovieDetail" {
            let movieViewController = segue.destinationViewController as! MovieViewController
            
            let title = (sender! as! UITableViewCell).textLabel!.text!
            movieViewController.library = self.library
            movieViewController.index = self.library.findIndexByTitle(title)
            
        } else if segue.identifier == "AddMovie" {
            let addEditController = segue.destinationViewController as! AddEditMovieController
            
            addEditController.library = self.library
        }
    }

}

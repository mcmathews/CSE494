//
//  SearchOmdbController.swift
//  MovieLibraryCoreData
//
//  Created by BB-9 on 4/7/16.
//  Copyright © 2016 mcmathe1. All rights reserved.
//

import UIKit

class SearchOmdbController: UITableViewController, UISearchBarDelegate {
    
    var searchResults: [(title: String, year: Int)] = []

    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet var searchResultsView: UITableView!
    @IBOutlet weak var noResultsText: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        searchBar.delegate = self
        searchBar.becomeFirstResponder()
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
        return searchResults.count
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("SearchResultCell", forIndexPath: indexPath)

        cell.textLabel?.text = searchResults[indexPath.row].title
        cell.detailTextLabel?.text = String(searchResults[indexPath.row].year)

        return cell
    }
    
    // MARK: - Search Bar
    
    func searchBarSearchButtonClicked(searchBar: UISearchBar) {
        OmdbClient().search(searchBar.text!) {
            results in
            if results.count > 0 {
                self.noResultsText.hidden = true
            } else {
                self.noResultsText.hidden = false
            }
            self.searchResults = results
            self.searchResultsView.reloadData()
        }
    }

    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        let cell = sender! as! UITableViewCell
        let title = cell.textLabel!.text!
        
        OmdbClient().get(title) {
            movie in
            MovieLibraryDao.getInstance().add(movie)
            
            let libraryViewController = segue.destinationViewController as! LibraryViewController
            
            libraryViewController.titles.append(title)
            let indexPath = NSIndexPath(forRow: libraryViewController.titles.count - 1, inSection: 0)
            libraryViewController.libraryTableView.insertRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        }
    }

}

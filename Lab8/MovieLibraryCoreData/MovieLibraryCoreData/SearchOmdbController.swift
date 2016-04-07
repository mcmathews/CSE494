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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        searchBar.delegate = self
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
            self.searchResults = results
            self.searchResultsView.reloadData()
        }
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}

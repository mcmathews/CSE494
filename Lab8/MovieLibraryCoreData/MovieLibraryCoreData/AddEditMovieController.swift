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

import UIKit

class AddEditMovieController: UIViewController, UIPickerViewDelegate {
    
    let genres = ["Action", "Adventure", "Children/Family", "Comedy", "Crime", "Drama", "Epic", "Fantasy", "Historical", "Horror", "Musical", "Mystery", "Romance", "Science Fiction", "Spy", "Thriller", "War", "Western"]
    
    var index: Int? = nil
    var titles: [String]? = nil
    var movie: MovieDescription? = nil
    
    @IBOutlet weak var titleT: UITextField!
    @IBOutlet weak var yearT: UITextField!
    @IBOutlet weak var ratedT: UITextField!
    @IBOutlet weak var runtimeT: UITextField!
    @IBOutlet weak var releasedT: UITextField!
    @IBOutlet weak var genreT: UITextField!
    @IBOutlet weak var genrePicker: UIPickerView!
    @IBOutlet weak var actorsT: UITextField!
    @IBOutlet weak var plotT: UITextView!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        if movie == nil {
            movie = MovieDescription()
        }

        titleT.text = movie!.title
        yearT.text = movie!.year > 0 ? String(movie!.year) : nil
        ratedT.text = movie!.rated
        runtimeT.text = movie!.runtime
        releasedT.text = movie!.released
        genreT.text = movie!.genres.joinWithSeparator(", ")
        actorsT.text = movie!.actors.joinWithSeparator(", ")
        plotT.text = movie!.plot
        
        self.view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: "closeKeyboard"))
        
        plotT.layer.cornerRadius = 5
        plotT.layer.borderColor = UIColor.lightGrayColor().CGColor
        plotT.layer.borderWidth = 1
        
        genrePicker.delegate = self
        genrePicker.removeFromSuperview()
        genreT.inputView = genrePicker
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        self.genreT.text = genres[row]
        self.genreT.resignFirstResponder()
        
    }
    
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return genres.count
    }
    
    func pickerView (pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return genres[row]
    }
    
    func closeKeyboard() {
        self.view.endEditing(true)
    }

    // MARK: - Navigation

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        movie!.title = titleT.text!
        if let year = Int(yearT.text!) {
            movie!.year = year
        }
        movie!.rated = ratedT.text!
        movie!.released = releasedT.text!
        movie!.runtime = runtimeT.text!
        movie!.genres = genreT.text!.characters.split(",").map{String.init($0).stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceCharacterSet())}
        movie!.actors = actorsT.text!.characters.split(",").map{String.init($0).stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceCharacterSet())}
        movie!.plot = plotT.text! != "Plot" ? plotT.text! : ""
        
        let libraryController = segue.destinationViewController as! LibraryViewController
        if index != nil {
            MovieLibraryDao.getInstance().edit(titles![index!], movie: movie!)
            libraryController.titles[self.index!] = self.movie!.title
            let indexPath = NSIndexPath(forRow: self.index!, inSection: 0)
            libraryController.libraryTableView.reloadRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else {
            MovieLibraryDao.getInstance().add(movie!)
            libraryController.titles.append(self.movie!.title)
            let indexPath = NSIndexPath(forRow: self.titles!.count, inSection: 0)
            libraryController.libraryTableView.insertRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        }
    }

}

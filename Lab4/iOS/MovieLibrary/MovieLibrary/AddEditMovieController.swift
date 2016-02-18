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
 * @version 2/11/16
 */

import UIKit

class AddEditMovieController: UIViewController, UIPickerViewDelegate {
    
    let genres = ["Action", "Adventure", "Children/Family", "Comedy", "Crime", "Drama", "Epic", "Fantasy", "Historical", "Horror", "Musical", "Mystery", "Romance", "Science Fiction", "Spy", "Thriller", "War", "Western"]
    
    var library: MovieLibrary? = nil
    var index: Int? = nil
    
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

        if let i = index {
            let movie = library!.movieDescriptions[i]
            
            titleT.text = movie.title
            yearT.text = movie.year > 0 ? String(movie.year) : nil
            ratedT.text = movie.rated
            runtimeT.text = movie.runtime
            releasedT.text = movie.released
            genreT.text = movie.genre
            actorsT.text = movie.actors
            plotT.text = movie.plot
        }
        
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

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        var movie: MovieDescription
        if let i = self.index {
            movie = library!.movieDescriptions[i]
        } else {
            movie = MovieDescription()
        }
        
        movie.title = titleT.text!
        if let year = Int(yearT.text!) {
            movie.year = year
        }
        movie.rated = ratedT.text!
        movie.released = releasedT.text!
        movie.runtime = runtimeT.text!
        movie.genre = genreT.text!
        movie.actors = actorsT.text!
        movie.plot = plotT.text! != "Plot" ? plotT.text! : ""
        
        if let i = self.index {
            library!.movieDescriptions[i] = movie
        } else {
            library!.movieDescriptions.append(movie)
        }
    }

}

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

class MovieViewController: UIViewController {
    
    var index: Int? = nil
    var movieTitle: String? = nil
    var movie: MovieDescription? = nil

    @IBOutlet weak var titleL: UILabel!
    @IBOutlet weak var yearL: UILabel!
    @IBOutlet weak var ratedL: UILabel!
    @IBOutlet weak var runtimeL: UILabel!
    @IBOutlet weak var releasedL: UILabel!
    @IBOutlet weak var genreL: UILabel!
    @IBOutlet weak var actorsL: UILabel!
    @IBOutlet weak var plotL: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        movie = nil // TODO: get movie from datasource
        
        titleL.text! += movie!.title
        yearL.text! += movie!.year > 0 ? String(movie!.year) : ""
        ratedL.text! += movie!.rated
        runtimeL.text! += movie!.runtime
        releasedL.text! += movie!.released
        genreL.text! += movie!.genre
        actorsL.text! += movie!.actors
        plotL.text! += movie!.plot
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "EditMovie" {
            let addEditController = segue.destinationViewController as! AddEditMovieController
            
            addEditController.movie = self.movie
            addEditController.movieTitle = self.movieTitle
            addEditController.index = self.index
        }
    }

}


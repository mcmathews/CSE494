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

class MovieViewController: UIViewController {
    
    var index: Int? = nil
    var titles: [String]? = nil
    var movie: MovieDescription? = nil

    @IBOutlet weak var titleL: UILabel!
    @IBOutlet weak var yearL: UILabel!
    @IBOutlet weak var ratedL: UILabel!
    @IBOutlet weak var runtimeL: UILabel!
    @IBOutlet weak var releasedL: UILabel!
    @IBOutlet weak var genresL: UILabel!
    @IBOutlet weak var actorsL: UILabel!
    @IBOutlet weak var plotL: UILabel!
    
    @IBOutlet weak var playButton: UIBarButtonItem!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.movie = MovieLibraryDao.getInstance().findByTitle(titles![index!])
        
        self.titleL.text! += self.movie!.title
        self.yearL.text! += self.movie!.year > 0 ? String(self.movie!.year) : ""
        self.ratedL.text! += self.movie!.rated
        self.runtimeL.text! += self.movie!.runtime
        self.releasedL.text! += self.movie!.released
        self.genresL.text! += self.movie!.genres.joinWithSeparator(", ")
        self.actorsL.text! += self.movie!.actors.joinWithSeparator(", ")
        self.plotL.text! += self.movie!.plot
        
        if let _ = movie?.filename {
            playButton.enabled = true
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "EditMovie" {
            let addEditController = segue.destinationViewController as! AddEditMovieController
            
            addEditController.movie = self.movie
            addEditController.titles = self.titles
            addEditController.index = self.index
        } else if segue.identifier == "PlayMovie" {
            let moviePlayerController = segue.destinationViewController as! MoviePlayerController
            
            moviePlayerController.filename = movie?.filename!
        }
    }

}


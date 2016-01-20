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
 * @version 1/20/16
 */

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var yearLabel: UILabel!
    @IBOutlet weak var ratedLabel: UILabel!
    @IBOutlet weak var releasedLabel: UILabel!
    @IBOutlet weak var runtimeLabel: UILabel!
    @IBOutlet weak var genreLabel: UILabel!
    @IBOutlet weak var actorsLabel: UILabel!
    @IBOutlet weak var plotLabel: UILabel!
    @IBOutlet weak var jsonLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let md = MovieDescription(jsonStr: "{\"Title\":\"The Dark Knight\",\"Year\":\"2008\",\"Rated\":\"PG-13\",\"Released\":\"18 Jul 2008\",\"Runtime\":\"152 min\",\"Genre\":\"Action, Crime, Drama\",\"Director\":\"Christopher Nolan\",\"Writer\":\"Jonathan Nolan (screenplay), Christopher Nolan (screenplay), Christopher Nolan (story), David S. Goyer (story), Bob Kane (characters)\",\"Actors\":\"Christian Bale, Heath Ledger, Aaron Eckhart, Michael Caine\",\"Plot\":\"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.\",\"Language\":\"English, Mandarin\",\"Country\":\"USA, UK\",\"Awards\":\"Won 2 Oscars. Another 141 wins & 126 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg\",\"Metascore\":\"82\",\"imdbRating\":\"9.0\",\"imdbVotes\":\"1,564,829\",\"imdbID\":\"tt0468569\",\"Type\":\"movie\",\"Response\":\"True\"}")
        
        titleLabel.text = titleLabel.text! + md.title
        yearLabel.text = yearLabel.text! + String(md.year)
        ratedLabel.text = ratedLabel.text! + md.rated
        releasedLabel.text = releasedLabel.text! + md.released
        runtimeLabel.text = runtimeLabel.text! + md.runtime
        genreLabel.text = genreLabel.text! + md.genre
        actorsLabel.text = actorsLabel.text! + md.actors
        plotLabel.text = plotLabel.text! + md.plot
        jsonLabel.text = jsonLabel.text! + md.toJsonString()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}


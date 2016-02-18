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

import Foundation

class MovieLibrary {
    var movieDescriptions: [MovieDescription]
    
    init(json: String) {
        movieDescriptions = [MovieDescription]()
        
        if let data: NSData = json.dataUsingEncoding(NSUTF8StringEncoding) {
            do {
                let dict = try NSJSONSerialization.JSONObjectWithData(data, options:.MutableContainers) as?[String:AnyObject]
                
                let movies = (dict!["library"] as? [AnyObject])!
                for movie in movies {
                    self.movieDescriptions.append(MovieDescription(json: movie))
                }
                
            } catch {
                print("unable to convert to dictionary")
                
            }
        }
    }
    
    func findIndexByTitle(title: String) -> Int? {
        for var i = 0; i < movieDescriptions.count; i++ {
            if movieDescriptions[i].title == title {
                return i
            }
        }
        return nil
    }
    
    static var libraryJson = "{\r\n  \"library\": [\r\n    {\"Title\":\"The Dark Knight\",\"Year\":\"2008\",\"Rated\":\"PG-13\",\"Released\":\"18 Jul 2008\",\"Runtime\":\"152 min\",\"Genre\":\"Action\",\"Director\":\"Christopher Nolan\",\"Writer\":\"Jonathan Nolan (screenplay), Christopher Nolan (screenplay), Christopher Nolan (story), David S. Goyer (story), Bob Kane (characters)\",\"Actors\":\"Christian Bale, Heath Ledger, Aaron Eckhart, Michael Caine\",\"Plot\":\"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.\",\"Language\":\"English, Mandarin\",\"Country\":\"USA, UK\",\"Awards\":\"Won 2 Oscars. Another 141 wins & 126 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg\",\"Metascore\":\"82\",\"imdbRating\":\"9.0\",\"imdbVotes\":\"1,564,829\",\"imdbID\":\"tt0468569\",\"Type\":\"movie\",\"Response\":\"True\"},\r\n    {\"Title\":\"The Dark Knight Rises\",\"Year\":\"2012\",\"Rated\":\"PG-13\",\"Released\":\"20 Jul 2012\",\"Runtime\":\"164 min\",\"Genre\":\"Action\",\"Director\":\"Christopher Nolan\",\"Writer\":\"Jonathan Nolan (screenplay), Christopher Nolan (screenplay), Christopher Nolan (story), David S. Goyer (story), Bob Kane (characters)\",\"Actors\":\"Christian Bale, Gary Oldman, Tom Hardy, Joseph Gordon-Levitt\",\"Plot\":\"Eight years after the Joker's reign of anarchy, the Dark Knight, with the help of the enigmatic Selia, is forced from his imposed exile to save Gotham City, now on the edge of total annihilation, from the brutal guerrilla terrorist Bane.\",\"Language\":\"English\",\"Country\":\"USA, UK\",\"Awards\":\"Nominated for 1 BAFTA Film Award. Another 44 wins & 98 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTk4ODQzNDY3Ml5BMl5BanBnXkFtZTcwODA0NTM4Nw@@._V1_SX300.jpg\",\"Metascore\":\"78\",\"imdbRating\":\"8.5\",\"imdbVotes\":\"1,070,247\",\"imdbID\":\"tt1345836\",\"Type\":\"movie\",\"Response\":\"True\"},\r\n    {\"Title\":\"Gladiator\",\"Year\":\"2000\",\"Rated\":\"R\",\"Released\":\"05 May 2000\",\"Runtime\":\"155 min\",\"Genre\":\"Action\",\"Director\":\"Ridley Scott\",\"Writer\":\"David Franzoni (story), David Franzoni (screenplay), John Logan (screenplay), William Nicholson (screenplay)\",\"Actors\":\"Russell Crowe, Joaquin Phoenix, Connie Nielsen, Oliver Reed\",\"Plot\":\"When a Roman general is betrayed and his family murdered by an emperor's corrupt son, he comes to Rome as a gladiator to seek revenge.\",\"Language\":\"English\",\"Country\":\"USA, UK\",\"Awards\":\"Won 5 Oscars. Another 53 wins & 101 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTgwMzQzNTQ1Ml5BMl5BanBnXkFtZTgwMDY2NTYxMTE@._V1_SX300.jpg\",\"Metascore\":\"64\",\"imdbRating\":\"8.5\",\"imdbVotes\":\"930,107\",\"imdbID\":\"tt0172495\",\"Type\":\"movie\",\"Response\":\"True\"},\r\n    {\"Title\":\"Rambo\",\"Year\":\"2008\",\"Rated\":\"R\",\"Released\":\"25 Jan 2008\",\"Runtime\":\"92 min\",\"Genre\":\"War\",\"Director\":\"Sylvester Stallone\",\"Writer\":\"Art Monterastelli, Sylvester Stallone, David Morrell (character)\",\"Actors\":\"Sylvester Stallone, Julie Benz, Matthew Marsden, Graham McTavish\",\"Plot\":\"In Thailand, John Rambo joins a group of mercenaries to venture into war-torn Burma, and rescue a group of Christian aid workers who were kidnapped by the ruthless local infantry unit.\",\"Language\":\"English, Burmese, Thai\",\"Country\":\"USA, Germany\",\"Awards\":\"1 win & 1 nomination.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTI5Mjg1MzM4NF5BMl5BanBnXkFtZTcwNTAyNzUzMw@@._V1_SX300.jpg\",\"Metascore\":\"46\",\"imdbRating\":\"7.1\",\"imdbVotes\":\"173,064\",\"imdbID\":\"tt0462499\",\"Type\":\"movie\",\"Response\":\"True\"},\r\n    {\"Title\":\"The Avengers\",\"Year\":\"2012\",\"Rated\":\"PG-13\",\"Released\":\"04 May 2012\",\"Runtime\":\"143 min\",\"Genre\":\"Science Fiction\",\"Director\":\"Joss Whedon\",\"Writer\":\"Joss Whedon (screenplay), Zak Penn (story), Joss Whedon (story)\",\"Actors\":\"Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth\",\"Plot\":\"Earth's mightiest heroes must come together and learn to fight as a team if they are to stop the mischievous Loki and his alien army from enslaving humanity.\",\"Language\":\"English, Russian\",\"Country\":\"USA\",\"Awards\":\"Nominated for 1 Oscar. Another 35 wins & 76 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTk2NTI1MTU4N15BMl5BanBnXkFtZTcwODg0OTY0Nw@@._V1_SX300.jpg\",\"Metascore\":\"69\",\"imdbRating\":\"8.1\",\"imdbVotes\":\"920,458\",\"imdbID\":\"tt0848228\",\"Type\":\"movie\",\"Response\":\"True\"},\r\n    {\"Title\":\"Frozen\",\"Year\":\"2013\",\"Rated\":\"PG\",\"Released\":\"27 Nov 2013\",\"Runtime\":\"102 min\",\"Genre\":\"Comedy\",\"Director\":\"Chris Buck, Jennifer Lee\",\"Writer\":\"Jennifer Lee (screenplay), Hans Christian Andersen (story), Chris Buck (story), Jennifer Lee (story), Shane Morris (story)\",\"Actors\":\"Kristen Bell, Idina Menzel, Jonathan Groff, Josh Gad\",\"Plot\":\"When the newly crowned Queen Elsa accidentally uses her power to turn things into ice to curse her home in infinite winter, her sister, Anna, teams up with a mountain man, his playful reindeer, and a snowman to change the weather condition.\",\"Language\":\"English, Icelandic\",\"Country\":\"USA\",\"Awards\":\"Won 2 Oscars. Another 69 wins & 56 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTQ1MjQwMTE5OF5BMl5BanBnXkFtZTgwNjk3MTcyMDE@._V1_SX300.jpg\",\"Metascore\":\"74\",\"imdbRating\":\"7.6\",\"imdbVotes\":\"390,405\",\"imdbID\":\"tt2294629\",\"Type\":\"movie\",\"Response\":\"True\"},\r\n    {\"Title\":\"Iron Man\",\"Year\":\"2008\",\"Rated\":\"PG-13\",\"Released\":\"02 May 2008\",\"Runtime\":\"126 min\",\"Genre\":\"Action\",\"Director\":\"Jon Favreau\",\"Writer\":\"Mark Fergus (screenplay), Hawk Ostby (screenplay), Art Marcum (screenplay), Matt Holloway (screenplay), Stan Lee (characters), Don Heck (characters), Larry Lieber (characters), Jack Kirby (characters)\",\"Actors\":\"Robert Downey Jr., Terrence Howard, Jeff Bridges, Gwyneth Paltrow\",\"Plot\":\"After being held captive in an Afghan cave, an industrialist creates a unique weaponized suit of armor to fight evil.\",\"Language\":\"English, Persian, Urdu, Arabic, Hungarian\",\"Country\":\"USA\",\"Awards\":\"Nominated for 2 Oscars. Another 19 wins & 58 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_SX300.jpg\",\"Metascore\":\"79\",\"imdbRating\":\"7.9\",\"imdbVotes\":\"650,667\",\"imdbID\":\"tt0371746\",\"Type\":\"movie\",\"Response\":\"True\"},\r\n    {\"Title\":\"Unbroken\",\"Year\":\"2014\",\"Rated\":\"PG-13\",\"Released\":\"25 Dec 2014\",\"Runtime\":\"137 min\",\"Genre\":\"Drama\",\"Director\":\"Angelina Jolie\",\"Writer\":\"Joel Coen (screenplay), Ethan Coen (screenplay), Richard LaGravenese (screenplay), William Nicholson (screenplay), Laura Hillenbrand (book)\",\"Actors\":\"Jack O'Connell, Domhnall Gleeson, Garrett Hedlund, Miyavi\",\"Plot\":\"After a near-fatal plane crash in WWII, Olympian Louis Zamperini spends a harrowing 47 days in a raft with two fellow crewmen before he's caught by the Japanese navy and sent to a prisoner-of-war camp.\",\"Language\":\"English, Japanese, Italian\",\"Country\":\"USA\",\"Awards\":\"Nominated for 3 Oscars. Another 12 wins & 25 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTY3ODg2OTgyOF5BMl5BanBnXkFtZTgwODk1OTAwMzE@._V1_SX300.jpg\",\"Metascore\":\"59\",\"imdbRating\":\"7.2\",\"imdbVotes\":\"92,834\",\"imdbID\":\"tt1809398\",\"Type\":\"movie\",\"Response\":\"True\"}\r\n  ]\r\n}\r\n"
}
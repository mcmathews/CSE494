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
 * @version Jan 26, 2016
 */
import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var button: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func handleButtonPress(sender: UIButton) {
        print("A thing will be done")
        
        let alert = UIAlertController(title: "The thing", message: "This is the thing you wanted to do", preferredStyle: UIAlertControllerStyle.Alert)
        alert.addAction(UIAlertAction(title: "I'm done with the thing", style: UIAlertActionStyle.Default, handler: nil))
        self.presentViewController(alert, animated: true, completion: nil)
    }

}


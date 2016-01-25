//
//  ViewController.swift
//  LifeCycle
//
//  Created by BB-9 on 1/25/16.
//  Copyright © 2016 mcmathe1. All rights reserved.
//

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


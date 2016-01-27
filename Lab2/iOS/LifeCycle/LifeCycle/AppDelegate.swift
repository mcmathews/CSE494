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

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        // called when the app is first launched
        NSLog("\(self.dynamicType): didFinishLaunchWithOptions")
        return true
    }
    
    func applicationWillResignActive(application: UIApplication) {
        // called when the app leaves the foreground, such as when you press the home button or drag down the notifications bar.
        NSLog("\(self.dynamicType): applicationWillResignActive")
    }

    func applicationDidEnterBackground(application: UIApplication) {
        // called when the app is minimized, such as when you press the home button.
        NSLog("\(self.dynamicType): applicationDidEnterBackground")
    }

    func applicationWillEnterForeground(application: UIApplication) {
        // Called when the app is first launched, and also any time you leave the app and come back to it.
        NSLog("\(self.dynamicType): applicationWillEnterForeground")
    }

    func applicationDidBecomeActive(application: UIApplication) {
        // called any time the app takes the foreground, such as on first launch, or rentering from the home screen, or putting away the notifications slider.
        NSLog("\(self.dynamicType): applicationDidBecomeActive")
    }

    func applicationWillTerminate(application: UIApplication) {
        // called when the system terminates the app to reclaim resources.  NOT called when the user force quits the app or when the device is rebooted.  I was unable to get this function to be called, though I tried openning many apps at once and forcing the device to close it.
        NSLog("\(self.dynamicType): applicationWillTerminate")
    }


}


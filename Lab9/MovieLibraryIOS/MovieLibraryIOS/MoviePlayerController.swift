//
//  MoviePlayerController.swift
//  MovieLibraryIOS
//
//  Created by mcmathe1 on 4/21/16.
//  Copyright © 2016 mcmathe1. All rights reserved.
//

import UIKit
import AVKit
import AVFoundation

class MoviePlayerController: AVPlayerViewController, NSURLSessionDelegate {
    
    var filename: String?
    
    var streamerHost: String?
    var streamerPort: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let path = NSBundle.mainBundle().pathForResource("Server", ofType: "plist")!
        if let dict = NSDictionary(contentsOfFile: path) as? [String: AnyObject] {
            streamerHost = dict["streamer_host"] as? String
            streamerPort = dict["streamer_port"] as? String
        }
        
        let urlString = "http://\(streamerHost!):\(streamerPort!)/\(filename!)"
        downloadVideo(urlString)
    }
    
    override func viewWillDisappear(animated: Bool) {
        if self.player != nil {
            self.player?.pause()
        }
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    // download the video in the background using NSURLSession.
    func downloadVideo(urlString: String) {
        let sessionConfig = NSURLSessionConfiguration.backgroundSessionConfigurationWithIdentifier("bgSession")
        let session = NSURLSession(configuration: sessionConfig, delegate: self, delegateQueue: NSOperationQueue.mainQueue())
        session.downloadTaskWithURL(NSURL(string: urlString)!).resume()
    }
    
    // play the movie from a file url
    func playMovieAtURL(fileUrl: NSURL) {
        print("\nplay\n")
        if (self.player != nil && self.player!.status == AVPlayerStatus.ReadyToPlay) {
            self.player?.replaceCurrentItemWithPlayerItem(AVPlayerItem(URL: fileUrl))
        } else {
            self.player = AVPlayer(URL: fileUrl)
        }
        
        self.videoGravity = AVLayerVideoGravityResizeAspectFill
        self.player!.play()
    }
    
    // functions for NSURLSessionDelegate
    func URLSession(session: NSURLSession, downloadTask: NSURLSessionDownloadTask, didFinishDownloadingToURL location: NSURL) {
        let path = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)
        let documentDirectoryPath: String = path[0]
        let fileMgr = NSFileManager()
        let destinationURLForFile = NSURL(fileURLWithPath: documentDirectoryPath.stringByAppendingString("/\(filename)"))
        
        if fileMgr.fileExistsAtPath(destinationURLForFile.path!) {
            NSLog("destination file url: \(destinationURLForFile.path!) exists. Deleting")
            do {
                try fileMgr.removeItemAtURL(destinationURLForFile)
            } catch {
                NSLog("error removing file at: \(destinationURLForFile)")
            }
        }
        do {
            try fileMgr.moveItemAtURL(location, toURL: destinationURLForFile)
            NSLog("download and save completed to: \(destinationURLForFile.path!)")
            session.invalidateAndCancel()
            playMovieAtURL(destinationURLForFile)
        } catch {
            NSLog("An error occurred while moving file to destination url")
        }
    }
    
//    func URLSession(session: NSURLSession, downloadTask: NSURLSessionDownloadTask, didWriteData bytesWritten: Int64, totalBytesWritten: Int64, totalBytesExpectedToWrite: Int64) {
//        NSLog("did write portion of file: \(Float(totalBytesWritten)/Float(totalBytesExpectedToWrite))")
//    }
}

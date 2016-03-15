//
//  MovieLibraryClient.swift
//  MovieLibraryIOSClient
//
//  Created by BB-9 on 3/15/16.
//  Copyright © 2016 mcmathe1. All rights reserved.
//

import Foundation

class MovieLibraryClient {
    
    private var url: String
    
    init () {
        url = NSBundle.mainBundle().infoDictionary!["serverUrl"] as! String
    }
}

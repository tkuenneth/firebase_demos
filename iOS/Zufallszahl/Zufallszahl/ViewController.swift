//
//  ViewController.swift
//  Zufallszahl
//
//  Created by Thomas Künneth on 29.06.18.
//  Copyright © 2018 Thomas Künneth. All rights reserved.
//

import UIKit
import FirebaseFunctions

class ViewController: UIViewController {
    
    @IBOutlet weak var label: UILabel!
    
    lazy var functions = Functions.functions()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func buttonClicked(_ sender: Any) {
        functions.httpsCallable("zufallszahl").call(["max": 10 as Int16]) { (result, error) in
            if let error = error as NSError? {
                if error.domain == FunctionsErrorDomain {
                    // ...
                }
            }
            if let result = (result?.data as? [String: Any])?["result"] as? Int16 {
                self.label.text = String(result)
            }
        }
    }
}


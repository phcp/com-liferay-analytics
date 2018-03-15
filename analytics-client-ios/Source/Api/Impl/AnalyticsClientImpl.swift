/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import Foundation

/**
* @author Allan Melo
*/
class AnalyticsClientImpl: AnalyticsClient {
    
    init() {
		let bundle = Bundle(for: type(of: self))
		let path = bundle.path(forResource: "settings", ofType:"plist")!
		let settings = NSDictionary(contentsOfFile: path) as! [String: String]
		
		GATEWAY_HOST = settings["ANALYTICS_GATEWAY_HOST"]!
		GATEWAY_PATH = settings["ANALYTICS_GATEWAY_PATH"]!
		GATEWAY_PORT = settings["ANALYTICS_GATEWAY_PORT"]!
		GATEWAY_PROCOTOL = settings["ANALYTICS_GATEWAY_PROTOCOL"]!
		
        baseUrl = URL(string: String(format:"%@://%@:%@%@" , GATEWAY_PROCOTOL,
									 GATEWAY_HOST, GATEWAY_PORT, GATEWAY_PATH))!
    }
    
    internal func sendAnalytics(analyticsMessage: AnalyticsEventsMessage)
		throws -> String {
			
		let encoder = JSONEncoder()
		encoder.dateEncodingStrategy = .iso8601
		let data = try! encoder.encode(analyticsMessage)
		
		let result = URLSession.sendPost(url: baseUrl, body: data)

		if let error = result.2 {
			throw error
		}
			
		return String(data: result.0!, encoding: .utf8)!
    }
    
	private let baseUrl: URL

	private let GATEWAY_HOST: String
	private let GATEWAY_PATH: String
	private let GATEWAY_PORT: String
	private let GATEWAY_PROCOTOL: String

}

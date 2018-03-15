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

import XCTest

/**
* @author Allan Melo
*/
class IdentityClientImplTest: XCTestCase {
	func testGetUserId() {
		let identityCtxMessage =
			IdentityContextMessage(analyticsKey: "liferay.com") {
				$0.dataSourceIdentifier = "Liferay"
				$0.dataSourceIndividualIdentifier = "12345"
				$0.domain = "liferay.com"
				$0.language = "en-US"
				$0.protocolVersion = "1.0"
				
				$0.identityFields = ["email":"joe.blogs@liferay.com",
									  "name": "Joe Bloggs"]
		}
		
		let userId = try! _identityClient.getUserId(
			identityContextMessage: identityCtxMessage)
		
		XCTAssert(!userId.isEmpty)
	}
	
	let _identityClient = IndentityClientImpl()
}

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

package com.liferay.analytics.client.osgi.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * @author Eduardo Garcia
 */
public class IdentityThreadLocal {

	public static String getUserId() {
		String userId = _userId.get();

		if (_log.isDebugEnabled()) {
			_log.debug("getUserId " + userId);
		}

		return userId;
	}

	public static void setUserId(String userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("setUserId " + userId);
		}

		_userId.set(userId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IdentityThreadLocal.class);

	private static final ThreadLocal<String> _userId =
		new AutoResetThreadLocal<>(IdentityThreadLocal.class + "._userId");

}
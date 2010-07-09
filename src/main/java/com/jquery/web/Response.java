/*
 * jQuery web framework
 * 
 * Copyright (c) 2007 Jörn Zaefferer
 * Dual licensed under the MIT and GPL licenses.
 * 
 * $Id: Response.java 2533 2007-07-31 08:06:48Z joern.zaefferer $
 */
package com.jquery.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * Provides a threadlocal reference to the current response.
 * 
 * Encapsulates creation of action and render URLs for portlets.
 */
public class Response {
	
	private static final ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<HttpServletResponse>();
	
	static void set(HttpServletResponse response) {
		responseHolder.set(response);
	}
	
	private static HttpServletResponse servletResponse() {
		return (HttpServletResponse) responseHolder.get();
	}
	
	public static void redirect(String location) {
		try {
			servletResponse().sendRedirect(location);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

/*
 * jQuery web framework
 * 
 * Copyright (c) 2007 Jörn Zaefferer
 * Dual licensed under the MIT and GPL licenses.
 * 
 * $Id: Globals.java 2533 2007-07-31 08:06:48Z joern.zaefferer $
 */
package com.jquery.web;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.tools.shell.Main;

/**
 * Provides global properties and functions.
 * 
 *
 */
public class Globals {

	/**
	 * load(String location) can be called by scripts at runtime to load dependencies.
	 * 
	 * Example:
	 * <tt>load(request.realPath + "/lib.js")</tt>
	 */
	public static void load(Context cx, Scriptable thisObj, Object[] args, Function funObj) {
		for (int i = 0; i < args.length; i++) {
			Main.processFile(cx, thisObj, Context.toString(args[i]));
			// return cx.compileString(scriptSource, path, lineno,
			// securityDomain);
		}
	}

	public final String contextPath;
	public final String realPath;
	public final String page;
	public final String method;

	public Globals(String contextPath, String realPath, String page, String method) {
		this.contextPath = contextPath;
		this.realPath = realPath;
		this.page = page;
		this.method = method;
	}

	public static void init(ScriptableObject scope, String contextPath, String realPath, String page, String method) {
		scope.defineFunctionProperties(new String[] { "load" }, Globals.class, ScriptableObject.DONTENUM);
		scope.defineProperty("request", new Globals(contextPath, realPath, page, method), ScriptableObject.DONTENUM);
	}

}

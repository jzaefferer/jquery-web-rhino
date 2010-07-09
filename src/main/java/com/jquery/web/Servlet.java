package com.jquery.web;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.tools.ToolErrorReporter;

public class Servlet extends HttpServlet {
	
//	private final static Log log = LogFactory.getLog(Servlet.class);
//
//	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		log.warn("servicing request");
//		response.getWriter().write("Hello world");
//	}
	
private final static Log log = LogFactory.getLog(Servlet.class);
	
	private static final long serialVersionUID = 1L;
	
	private String views = "views/";
	
	private ScriptableObject shared;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if(config.getInitParameter("views") != null) {
			views = config.getInitParameter("views");
		}
		shared = new ImporterTopLevel(Context.enter());
		Context.exit();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) {
		log.info("request!");
		Request.set(request);
		Response.set(response);
		// TODO allow other content types, eg. xml for rss feed
		response.setContentType("text/html; charset=UTF-8");
		Context cx = Context.enter();
		//ScriptableObject scope = new ImporterTopLevel(Context.enter());
		ScriptableObject scope = (ScriptableObject) cx.newObject(shared);
		scope.setPrototype(shared);
		scope.setParentScope(null);
		Context.getCurrentContext().getWrapFactory().setJavaPrimitiveWrap(false);
		Context.getCurrentContext().setErrorReporter(new ToolErrorReporter(true, System.err));
		Globals.init(scope, request.getContextPath(), realPath(), views + page(request), request.getMethod().toLowerCase());
		try {
			Object result = eval(scope, views + page(request));
			response.getWriter().write(result.toString());
		} catch (Throwable e) {
			try {
				response.getWriter().write("<h1>The following exception occcured:</h1><pre>");
				e.printStackTrace(response.getWriter());
				response.getWriter().write("</pre>");
				log.error(e.getMessage(), e);
			} catch (IOException ex) {
				e.printStackTrace();
			}
		} finally {
			Context.exit();
		}
	}

	private String realPath() {
		return getServletContext().getRealPath("WEB-INF/");
	}
	
	// TODO move to application
	private String page(HttpServletRequest request) {
		String page = request.getParameter("post");
		if ( page != null)
			return "post";
		page = request.getParameter("category");
		if ( page != null)
			return "category";
		page = request.getParameter("feed");
		if ( page != null)
			return "feed";
		return "index";
	}

	private Object eval(Scriptable scope, String name) {
		try {
			return Context.getCurrentContext().evaluateReader(scope, findFile(realPath() + "/" + name), name + ".js", 1, null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Reader findFile(String filename) {
		try {
			return new FileReader(filename + ".js");
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
}

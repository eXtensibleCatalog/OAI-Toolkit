/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.struts;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;

/**
 * This servlet is load first by Tomcat and it launch the application's
 * static variables by launcheng ApplInfo class
 * @author Peter Kiraly
 */
public class InitializerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** Constructor of the object. */
	public InitializerServlet() {
		super();
	}

	/** Destruction of the servlet. */
	public void destroy() {
		super.destroy();
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	/**
	 * Initialization of the servlet. <br>
	 * @throws ServletException if an error occure
	 */
	public void init(ServletConfig cfg) throws ServletException {
		super.init(cfg);
		
		File binDir = null;
        File webAppDir = null;
        String webAppName = null;
		try {
			binDir = new File(cfg.getServletContext().getRealPath("../../bin"))
				.getCanonicalFile();
            webAppDir = new File(cfg.getServletContext().getRealPath("."))
				.getCanonicalFile();
            webAppName = webAppDir.getName();
            log(" The Name for webapp Dir is "+ webAppName);
            log("The new Tomcat/bin directory is " + webAppDir.getCanonicalPath());

			log("The Tomcat/bin directory is " + binDir.getCanonicalPath());
			if(!binDir.exists()) {
				log("ERROR: The computed Tomcat/bin directory doesn't exist.");
			} else {
				log("The computed Tomcat/bin directory exists.");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		log("Initializer Servlet loaded, initializing ...");
		try {
			ApplInfo.init(binDir, webAppName);
		} catch (Exception e) {
			log("Configuration error occured:" + e.getMessage());
		}
	}
}

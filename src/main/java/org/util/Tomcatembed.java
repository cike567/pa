package org.util;

import java.io.File;

import javax.servlet.http.HttpServlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * 
 * @author cike
 *
 */
public class Tomcatembed {

	public void addServlet(HttpServlet servlet, String path) {
		String name = servlet.getClass().getSimpleName();
		tomcat.addServlet(root, name, servlet);
		root.addServletMappingDecoded("/" + path, name);
	}

	public void addServlet(HttpServlet servlet) {
		String path = servlet.getClass().getSimpleName().replace("Servlet", "").toLowerCase();
		addServlet(servlet, path);
	}

	public void startup() throws LifecycleException {
		startup(PORT);
	}

	public void startup(int port) throws LifecycleException {
		tomcat.setPort(port);
		tomcat.start();
		tomcat.getServer().await();
	}

	private Tomcatembed() {
		tomcat = new Tomcat();
		root = tomcat.addContext("/", new File(".").getAbsolutePath());
		tomcat.getConnector();
	}

	public static Tomcatembed tomcat() {
		return embed;
	}

	private int PORT = 8080;

	private static Tomcatembed embed = new Tomcatembed();

	private Tomcat tomcat;

	private Context root;
}

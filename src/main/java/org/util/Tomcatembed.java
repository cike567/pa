package org.util;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * 
 * @author cike
 *
 */
public class Tomcatembed {
	static Jar jar = null;
	static {
		Thread.currentThread().setContextClassLoader(jar);

	}
	/*
	 * public void addServlet(javax.servlet.http.HttpServlet servlet, String path) {
	 * String name = servlet.getClass().getSimpleName(); tomcat.addServlet(root,
	 * name, servlet); root.addServletMappingDecoded("/" + path, name); }
	 * 
	 * public void addServlet(javax.servlet.http.HttpServlet servlet) { String path
	 * = servlet.getClass().getSimpleName().replace("Servlet", "").toLowerCase();
	 * addServlet(servlet, path); }
	 */

	public void startup() throws Exception {

		startup(PORT);
	}

	public void startup(int port) throws LifecycleException {
		tomcat.setPort(port);
		tomcat.start();
		tomcat.getServer().await();
	}

	public Tomcatembed() {
		try {
			jar = new Jar();
			System.out.println(jar.findClass("org.apache.catalina.startup.Tomcat"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tomcat = new Tomcat();
		root = tomcat.addContext("/", new File(".").getAbsolutePath());
		tomcat.getConnector();
	}

	public static Tomcatembed tomcat() throws IOException, InterruptedException {
		return embed;
	}

	private int PORT = 8080;

	private static Tomcatembed embed = new Tomcatembed();

	private Tomcat tomcat;

	private Context root;
}

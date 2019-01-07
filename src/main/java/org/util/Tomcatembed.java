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

	public Tomcatembed addServlet(Object servlet, String path) {
		String name = servlet.getClass().getSimpleName();
		System.out.println("" + name);
		tomcat.addServlet(root, name, (javax.servlet.Servlet) servlet);
		root.addServletMappingDecoded("/" + path, name);
		return this;
	}

	public Tomcatembed addServlet(Object servlet) {
		String path = servlet.getClass().getSimpleName().replace("Servlet", "").toLowerCase();
		return addServlet(servlet, path);
	}

	public void startup(int port) throws LifecycleException {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "{}");
		tomcat.setPort(port);
		tomcat.start();
		tomcat.getServer().await();
	}

	public Tomcatembed() {
		tomcat = new Tomcat();
		root = tomcat.addContext("/", new File(".").getAbsolutePath());

	}

	public static Tomcatembed tomcat() throws IOException, InterruptedException {
		return embed;
	}

	private static Tomcatembed embed = new Tomcatembed();

	private Tomcat tomcat;

	private Context root;
}

package org.chrome.endpoint;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.util.CMD;
import org.util.Jar;
import org.util.Tomcatembed;

/**
 * 
 * @author cike
 *
 */
public class App {
	/*
	 * -c 9222 -t 8080
	 */
	public static void main(String[] args) throws IOException {
		App app = new App(args);
		try {
			HttpServlet servlet = new EndpointServlet(app.PORT_CHROME);
			Tomcatembed tomcat = Tomcatembed.tomcat().addServlet(servlet, "json");
			tomcat.startup(app.PORT_TOMCAT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private App(String[] arg) throws IOException {
		new Jar().classpath();
		args = CMD.args(arg);
		if (args.containsKey(CHEOME)) {
			PORT_CHROME = (Integer) args.get(CHEOME);
		}
		if (args.containsKey(TOMCAT)) {
			PORT_TOMCAT = (Integer) args.get(TOMCAT);
		}
	}

	private String CHEOME = "c";
	private String TOMCAT = "t";

	private int PORT_CHROME = 9222;
	private int PORT_TOMCAT = 9000;

	private Map<String, Object> args;

}

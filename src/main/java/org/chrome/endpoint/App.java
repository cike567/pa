package org.chrome.endpoint;

import java.util.Map;

import org.apache.catalina.LifecycleException;
import org.chrome.EndpointServlet;
import org.util.CMD;
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
	public static void main(String[] args) throws LifecycleException {
		App app = new App(args);
		Tomcatembed tomcat = Tomcatembed.tomcat();
		tomcat.addServlet(new EndpointServlet(app.PORT_CHROME));
		tomcat.startup(app.PORT_TOMCAT);
	}

	private App(String[] arg) {
		args = CMD.args(arg);
		if (args.containsKey(CHEOME)) {
			PORT_CHROME = (Integer) args.get(CHEOME);
		}
		if (args.containsKey(TOMCAT)) {
			PORT_TOMCAT = (Integer) args.get(TOMCAT);
		}
	}

	String CHEOME = "c";
	String TOMCAT = "t";

	int PORT_CHROME = 9222;
	int PORT_TOMCAT = 8080;

	private Map<String, Object> args;

}

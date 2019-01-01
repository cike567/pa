package org.chrome.endpoint;

import java.util.Map;

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
	public static void main(String[] args) {
		// App app = new App(args);

		try {

			// Jar.run("org.util.Tomcatembed", "startup");
			Tomcatembed.tomcat().startup();
			;
		} catch (Exception e) {

		}
		// jar.load();

		// org.util.Tomcatembed tomcat = org.util.Tomcatembed.tomcat();
		// tomcat.startup();

		// jar.load();
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
	int PORT_TOMCAT = 8090;

	private Map<String, Object> args;

}

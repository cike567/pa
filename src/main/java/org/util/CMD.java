package org.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author cike
 *
 */
public class CMD {

	public static void exec(String exe) throws IOException {
		Runtime.getRuntime().exec(exe);
	}

	public static void kill(String exe) throws IOException {
		exec(String.format(KILL, exe));
	}

	public static Map<String, Object> args(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < args.length; i = i + 2) {
			if (args[i].startsWith("-")) {
				map.put(args[i].substring(1), args[i + 1]);
			}
		}
		return map;
	}

	private static String KILL = "taskkill /f /t /im %s.exe";

}

package org.util;

import java.io.IOException;

public class Exec {

	public static void run(String exe) throws IOException {
		Runtime.getRuntime().exec(exe);
	}

	public static void kill(String exe) throws IOException {
		run(String.format(KILL, exe));
	}

	private static String KILL = "taskkill /f /t /im %s.exe";

}

package org.util.io;

import java.io.InputStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class Stream {

	public static String read(InputStream inputStream) {
		return read(inputStream, "UTF-8");
	}

	public static String read(InputStream inputStream, String charset) {
		Scanner scanner = new Scanner(inputStream, charset);
		return scanner.useDelimiter("\\A").next();
	}

	private static final Logger log = LoggerFactory.getLogger(Stream.class);

}

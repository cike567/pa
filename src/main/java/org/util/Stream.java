package org.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author cike
 *
 */
public class Stream {

	public static String read(InputStream inputStream) {
		return read(inputStream, Http.UTF8);
	}

	public static String read(InputStream inputStream, String charset) {
		Scanner scanner = new Scanner(inputStream, charset);
		return scanner.useDelimiter("\\A").next();
	}

	public static List<String> readLine(InputStream inputStream) throws IOException {
		List<String> lines = new ArrayList<String>();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		return lines;
	}

	public static void write(InputStream input, File file) throws IOException {
		RandomAccessFile rf = new RandomAccessFile(file, "rw");
		rf.write(toByte(input));
		rf.close();
	}

	public static byte[] toByte(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUF];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		buffer = output.toByteArray();
		output.close();
		return buffer;
	}

	private static final int BUF = 1024 * 8;

	// private static final Logger log = LoggerFactory.getLogger(Stream.class);

}

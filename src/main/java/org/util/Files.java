package org.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cike
 *
 */
public class Files {

	public static File create(String fileName) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			return file;
		}
		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (file.isFile()) {
			file.createNewFile();
		}

		return file;
	}

	public static String read(String fileName) {
		return Stream.read(inputStream(fileName));
	}

	public static List<String> read(File file) throws IOException {
		List<String> rs = new ArrayList<String>();
		if (!file.exists()) {
			return rs;
		}
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		while ((tempString = reader.readLine()) != null) {
			rs.add(tempString);
		}
		reader.close();
		return rs;
	}

	public static void write(String txt, File file) throws IOException {
		RandomAccessFile rf = new RandomAccessFile(file, "rw");
		rf.write(txt.getBytes());
		rf.close();
	}

	public static File write(String txt, String suf) throws IOException {
		File file = File.createTempFile(String.valueOf(System.currentTimeMillis()), "." + suf);
		write(txt, file);
		return file;
	}

	private static InputStream inputStream(String fileName) {
		ClassLoader classLoader = Files.class.getClassLoader();
		return classLoader.getResourceAsStream(fileName);
	}

}

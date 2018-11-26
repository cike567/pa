package org.util;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.util.io.Files;

/**
 * 
 * @author cike
 *
 */
public class FileTest {

	@Test
	public void testCreate() throws IOException {
		String name = "1";
		File file = Files.create(String.format("%s/%s.%s", "logs", name, "csv"));
		System.out.println(file.getAbsolutePath());
	}

	@Test
	public void testRead() throws IOException {
		String fileName = "Samestyle.csv";
		System.out.println(Files.read(fileName));
	}

}

package org.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.util.Files;

/**
 * Unit test for simple App.
 */
public class FileTest {

	@Test
	public void testCreate() throws IOException {
		String name = "1";
		File file = Files.create(String.format("%s/%s.%s", "taobao", name, "csv"));
		System.out.println(file.getAbsolutePath());
	}

	@Test
	public void testRead() throws IOException {
		String fileName = "Samestyle.csv";
		System.out.println(Files.read(fileName));
		List list = Files.read(new File(fileName));
		System.out.println(list.size());
	}

}

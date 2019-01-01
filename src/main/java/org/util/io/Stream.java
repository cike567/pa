package org.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.util.html.Http;

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

	public static void save(InputStream is, File temp) {
		System.out.println(temp.getAbsolutePath());
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(new FileOutputStream(temp)); // 把文件流转为文件，保存在临时目录
			int len = 0;
			byte[] buf = new byte[10 * 1024]; // 缓冲区
			while ((len = bis.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
			bos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (bis != null)
					bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// private static final Logger log = LoggerFactory.getLogger(Stream.class);

}

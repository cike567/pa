package org.chrome;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Files;

/**
 * 
 * @author cike
 *
 */
public class ProtocolTest {

	// @Test
	public void testRequest() throws IOException, InterruptedException {
		// Exec.kill("chrome");
		String url = "https://www.baidu.com/";
		url = "https://s.taobao.com/";
		Protocol client = Devtools.protocol();
		Request request = new Request("Page.enable");
		String rs = client.send(request);
		System.out.println(rs);
		/*
		 * BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		 * while (true) { String line = r.readLine(); if ("quit".equals(line)) { break;
		 * } client.send(line); }
		 */
	}

	@Test
	public void testDocument() throws IOException, InterruptedException {
		// Exec.kill("chrome");
		String url = "https://www.12306.cn";
		// url = "https://www.baidu.com/";
		Protocol client = Devtools.protocol();
		String rs = client.navigate(url).document();
		File file = Files.write(rs, "html");
		System.out.println(file.getAbsolutePath());

	}

	// @Test
	public void testProtocol() throws IOException, InterruptedException {
		// Exec.kill("chrome");
		String url = "https://www.baidu.com/";
		Protocol client = Devtools.protocol();
		url = "https://www.163.com/";
		Protocol p = Devtools.open(url);
		for (int i = 0; i < 10; i++) {
			log.info("id########{}", i);
			client.navigate(url);
			client.document();

			p.document();
		}

		// Devtools.chrome().get(HttpEndpoints.ACTIVATE, id);
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

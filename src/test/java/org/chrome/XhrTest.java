package org.chrome;

import java.io.IOException;
import java.util.List;

import org.chrome.request.Xhr;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class XhrTest {

	@Test
	public void testRequest() throws IOException, InterruptedException {
		// Exec.kill("chrome");
		Protocol client = Devtools.protocol();
		String url = "https://www.baidu.com/";
		url = "https://s.taobao.com/";
		url = "http://www.96900.com.cn/views/Home/index.html";
		client.navigate(url);

		List list = Xhr.all(client);
		System.out.println(list);
		/*
		 * BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		 * while (true) { String line = r.readLine(); if ("quit".equals(line)) { break;
		 * } client.send(line); }
		 */
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

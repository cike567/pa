package org.chrome;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Files;

/**
 * 
 * @author cike
 *
 */
public class DevtoolsTest {

	@Test
	public void testProtocols() throws IOException, InterruptedException {
		// Exec.kill("chrome");
		String[] url = { "https://www.baidu.com/", "https://s.taobao.com/search",
				"http://www.96900.com.cn/views/Home/index.html" };
		Arrays.asList(url).forEach((v) -> {
			try {
				Protocol client = Devtools.protocol();// chrome.open(v);//
				// client.navigate(v);
				String html = client.document();
				Files.write(html, "html");
				// log.info(html);
			} catch (IOException | InterruptedException e) {
				log.debug(e.getMessage());
			}
		});

	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

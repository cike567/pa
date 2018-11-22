package org.chrome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.WebSocketClient;

/**
 * Unit test for simple App.
 */
public class WSTest {

	// @Test
	public void testRun() throws IOException {
		String url = "https://s.taobao.com/search?q=";
		// Http.curl(url);
		String uri = "ws://localhost:9222/devtools/page/4E6642397DC4AF82B36B5C6AFC35211A";
		WebSocketClient client = new WebSocketClient(uri);
		client.send("{\"method\":\"Runtime.enable\",\"params\":{},\"id\":1}");
		// {"method":"DOM.getDocument","params":{},"id":21}
		// {"method":"DOM.resolveNode","params":{"nodeId":1},"id":22}
		// {"method":"Runtime.callFunctionOn","params":{"returnByValue":true,"silent":false,"generatePreview":false,"awaitPromise":false,"userGesture":false,"arguments":[{"value":"documentElement.outerHTML"}],"functionDeclaration":"function(property)
		// { return property.split('.').reduce((o, i) => o[i], this);
		// }","objectId":"{\"injectedScriptId\":3,\"id\":1}"},"id":23}
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = r.readLine();
			if (line.equals("quit"))
				break;
			client.send(line);
		}
	}

	@Test
	public void testCDP() throws IOException, InterruptedException {
		// Exec.kill("chrome");
		Domains d = new Domains();
		Devtools client = new Devtools(9222);
		client.send(d.navigate("https://www.baidu.com/"));
		new Thread(client).start();
		client.send(d.navigate("https://www.baidu.com/"));
		client.close();

	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

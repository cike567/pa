package org.chrome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ws.WebSocketClient;

/**
 * 
 * @author cike
 *
 */
public class WSTest {

	// @Test
	public void testRun() throws IOException {
		String url = "https://s.taobao.com/search?q=";
		// Http.curl(url);
		String uri = "ws://localhost:9222/devtools/page/A883AA0FD10347516A447653AAADCCBF";
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
			if ("quit".equals(line)) {
				break;
			}
			client.send(line);
		}
	}

	// @Test
	public void testRequest() throws IOException, InterruptedException {
		// Exec.kill("chrome");
		String url = "https://www.baidu.com/";
		url = "https://s.taobao.com/";
		Devtools client = Devtools.chrome();
		Request request = new Request("Page.enable");
		client.send(request);

		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = r.readLine();
			if ("quit".equals(line)) {
				break;
			}
			client.send(line);
		}

	}

	@Test
	public void testDomains() throws IOException, InterruptedException {
		// Exec.kill("chrome");
		String url = "https://www.baidu.com/";
		Domains d = new Domains();
		d.navigate(url);
		String html = d.document();
		System.out.print(html);
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

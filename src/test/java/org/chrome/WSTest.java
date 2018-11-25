package org.chrome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Json;
import org.ws.WebSocketClient;

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

	// @Test
	public void testRequest() throws IOException, InterruptedException {
		// Exec.kill("chrome");
		String url = "https://www.baidu.com/";
		url = "https://s.taobao.com/";
		Domains d = Domains.navigate(url);
		Devtools client = new Devtools(9222);
		client.send(d);
		Request request = new Request("Page.enable");
		client.send(request);

		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = r.readLine();
			if (line.equals("quit"))
				break;
			client.send(line);
		}

	}

	@Test
	public void testResponse() throws IOException, InterruptedException {
		// Exec.kill("chrome");
		String url = "https://www.baidu.com/";
		Domains d = Domains.navigate(url);
		Devtools client = new Devtools(9222);
		client.send(d);

		d = new Domains(new Request("DOM.getDocument"));
		String rs = client.send(d);

		Request resolveNode = new Request("DOM.resolveNode");
		Json json = new Json(rs);
		int nodeId = Integer.parseInt(json.select(new String[] { "result", "root" }).object().get(NODE_ID).toString());
		resolveNode.setParams(NODE_ID, nodeId);
		rs = client.send(new Domains(resolveNode));

		Request callFunctionOn = callFunctionOn(new Json(rs));
		rs = client.send(new Domains(new Response(), callFunctionOn));
		String html = new Json(rs).select(new String[] { "result", "result" }).object().get("value").toString();
		System.out.print(html);
	}

	private Request callFunctionOn(Json json) throws IOException, InterruptedException {
		Request callFunctionOn = new Request("Runtime.callFunctionOn");
		String objectId = json.select(new String[] { "result", "object" }).object().getString(OBJECT_ID);

		Map<String, Object> arguments = new HashMap() {
			{
				put("value", "documentElement.outerHTML");
			}
		};

		Map<String, Object> params = new HashMap<String, Object>() {
			{
				put("returnByValue", true);
				put("silent", false);
				put("generatePreview", false);
				put("awaitPromise", false);
				put("userGesture", false);
				put("arguments", Arrays.asList(arguments));
				put("functionDeclaration",
						"function(property) { return property.split('.').reduce((o, i) => o[i], this); }");
				put(OBJECT_ID, objectId);

			}
		};
		callFunctionOn.setParams(params);
		return callFunctionOn;
	}

	private String NODE_ID = "nodeId";

	private String OBJECT_ID = "objectId";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

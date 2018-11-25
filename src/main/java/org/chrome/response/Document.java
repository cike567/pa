package org.chrome.response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.chrome.Devtools;
import org.chrome.Request;
import org.chrome.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Json;

import lombok.Getter;

@Getter
public class Document extends Response {

	@Override
	public void handle(String message) throws IOException {
		Json json = new Json(message);
		JSONObject response = json.object();
		log.info("{}", response);
		boolean flag = false;
		/*
		 * if (response.has(NODE_ID)) { resolveNode(json); }
		 */

		if (response.has(ID) && id.contains(response.get(ID))) {
			Integer id = response.getInt("id");
			if (id.equals(peek(0))) {
				// resolveNode(json);
				// flag = true;
			} else if (id.equals(peek(1))) {
				// callFunctionOn(json);
				flag = true;
			}

		}
		if (flag) {
			latch.countDown();
		}
	}

	private void resolveNode(Json json) throws IOException, InterruptedException {
		Request resolveNode = new Request("DOM.resolveNode");
		id.add(resolveNode.getId());
		int nodeId = Integer.parseInt(json.select(new String[] { "result", "root" }).object().get(NODE_ID).toString());
		resolveNode.setParams(NODE_ID, nodeId);
		// TODO
		// request.add(resolveNode);
		client.send(resolveNode);
	}

	private void callFunctionOn(Json json) throws IOException, InterruptedException {
		Request callFunctionOn = new Request("Runtime.callFunctionOn");
		id.add(callFunctionOn.getId());
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
				put("arguments", arguments);
				put("functionDeclaration",
						"function(property) { return property.split('.').reduce((o, i) => o[i], this); }");
				put(OBJECT_ID, objectId);

			}
		};
		callFunctionOn.setParams(params);
		client.send(callFunctionOn);
	}

	public Document(Devtools client) {
		this.client = client;
		request = new Request("DOM.getDocument");
		// Request getDocument = new Request("DOM.getDocument");
		// Request resolveNode = new Request("DOM.resolveNode");
		// Request callFunctionOn = new Request("Runtime.callFunctionOn");
		// request = new ArrayList<Request>(Arrays.asList(getDocument));// ,
		// resolveNode, callFunctionOn
	}

	Devtools client;

	private Request request;// List<Request>

	private String NODE_ID = "nodeId";

	private String OBJECT_ID = "objectId";

	public final Logger log = LoggerFactory.getLogger(this.getClass());

}

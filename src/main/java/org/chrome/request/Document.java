package org.chrome.request;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.chrome.Devtools;
import org.chrome.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Json;

import lombok.Getter;

@Getter
public class Document {

	public String html(Devtools client) throws IOException, InterruptedException {
		String rs = client.send(getDocument());
		rs = client.send(resolveNode(rs));
		rs = client.send(callFunctionOn(rs));
		String html = new Json(rs).select(new String[] { "result", "result" }).object().get("value").toString();
		return html;
	}

	public Request getDocument() {
		return new Request("DOM.getDocument");
	}

	public Request resolveNode(String message) throws IOException, InterruptedException {
		Request resolveNode = new Request("DOM.resolveNode");
		Json json = new Json(message);
		int nodeId = Integer.parseInt(json.select(new String[] { "result", "root" }).object().get(NODE_ID).toString());
		resolveNode.setParams(NODE_ID, nodeId);
		return resolveNode;
	}

	public Request callFunctionOn(String message) throws IOException, InterruptedException {
		Json json = new Json(message);
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

	public final Logger log = LoggerFactory.getLogger(this.getClass());

}

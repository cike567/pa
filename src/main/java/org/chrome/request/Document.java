package org.chrome.request;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.chrome.Protocol;
import org.chrome.Request;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Json;

import lombok.Getter;

/**
 * 
 * @author cike
 *
 */
@Getter
public class Document {

	public String html(Protocol client) throws IOException, InterruptedException {
		// Protocol client = Devtools.chrome().getProtocol();
		String rs = "";
		do {
			rs = client.send(getDocument());
			rs = client.send(resolveNode(rs));
		} while (new Json(rs).object().has("error"));
		rs = client.send(callFunctionOn(rs));
		String html = new Json(rs).select(new String[] { "result", "result" }).object().get("value").toString();
		return html;
	}

	// TODO
	int nodeId(Protocol client) {
		boolean flag = true;
		int nodeId = 0;
		while (flag) {
			String rs = client.result();
			log.info("nodeId#{}", rs);
			JSONObject json = new Json(rs).object();
			if (json.has("params")) {
				json = (JSONObject) json.get("params");
				if (json.has("nodeId")) {
					nodeId = json.getInt("nodeId");
					flag = false;
				}
			}
		}
		return nodeId;
	}

	private Request getDocument() {
		return new Request("DOM.getDocument");
	}

	private Request resolveNode(String message) throws IOException, InterruptedException {
		Json json = new Json(message);
		int nodeId = Integer.parseInt(json.select(new String[] { "result", "root" }).object().get(NODE_ID).toString());
		return resolveNode(nodeId);
	}

	private Request resolveNode(int nodeId) throws IOException, InterruptedException {
		Request resolveNode = new Request("DOM.resolveNode");
		resolveNode.setParams(NODE_ID, nodeId);
		return resolveNode;
	}

	private Request callFunctionOn(String message) throws IOException, InterruptedException {
		Json json = new Json(message);
		Request callFunctionOn = new Request("Runtime.callFunctionOn");
		String objectId = json.select(new String[] { "result", "object" }).object().getString(OBJECT_ID);

		Map<String, Object> arguments = new HashMap(1) {
			{
				put("value", "documentElement.outerHTML");
			}
		};

		Map<String, Object> params = new HashMap<String, Object>(8) {
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

	private final String NODE_ID = "nodeId";

	private final String OBJECT_ID = "objectId";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

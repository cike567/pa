package org.chrome;

import java.util.HashMap;
import java.util.Map;

public class Domains {

	public Request navigate(String url) {
		Map<String, Object> params = new HashMap() {
			{
				put("url", url);
			}
		};
		Request request = request("Page.navigate", params);
		return request;
	}

	public void document() {

	}

	public Request request(String method, Map<String, Object> params) {
		Request request = new Request();
		request.setMethod(method);
		request.setParams(params);
		return request;
	}

	public static Map<String, String[]> METHOD = new HashMap<String, String[]>() {
		{
			put("Page.navigate", new String[] { "url" });
		}
	};

}

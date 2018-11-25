package org.chrome;

import java.io.IOException;

import org.util.html.Http;

public enum HttpEndpoints {
	VERSION("/json/version"), LIST("/json"), PROTOCOL("/json/protocol/"), NEW("/json/new?"), // {url}
	ACTIVATE("/json/activate/"), // {targetId}
	CLOSE("/json/close/");// {targetId}

	String path;

	String host = "http://localhost:9222";

	HttpEndpoints(String path) {
		this.path = path;
	}

	public String get() throws IOException {
		return get(host, "");
	}

	public String get(String query) throws IOException {
		return get(host, query);
	}

	public String get(String host, String query) throws IOException {
		return Http.get(host + path + query, "UTF-8");
	}

}

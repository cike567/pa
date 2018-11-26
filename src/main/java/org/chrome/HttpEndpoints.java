package org.chrome;

import java.io.IOException;

import org.util.html.Http;

/**
 * 
 * @author cike
 *
 */
public enum HttpEndpoints {
	//
	VERSION("/json/version"),

	//
	LIST("/json"),

	//
	PROTOCOL("/json/protocol/"),

	// {url}
	NEW("/json/new?"),

	// {targetId}
	ACTIVATE("/json/activate/"),

	// {targetId}
	CLOSE("/json/close/");

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

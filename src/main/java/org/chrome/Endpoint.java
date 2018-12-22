package org.chrome;

/**
 * 
 * @author cike
 *
 */
public enum Endpoint {
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

	Endpoint(String path) {
		this.path = path;
	}

	public String path(String... query) {
		StringBuffer sb = new StringBuffer();
		sb.append(path);
		for (int i = 0; i < query.length; i++) {
			sb.append(query[i]);
		}
		return sb.toString();
	}

}

package org.chrome;

import java.util.HashMap;
import java.util.Map;

import org.util.html.Json;

import lombok.Data;

/**
 * 
 * @author cike
 *
 */
@Data
public class Request {

	private String method;
	private Map<String, Object> params;
	private int id;

	public void setParams(String k, Object v) {
		params = new HashMap<String, Object>(1) {
			{
				put(k, v);
			}
		};
	}

	@Override
	public String toString() {
		return Json.toString(this);
	}

	public Request(String method) {
		this.method = method;
		this.id = Devtools.id();
	}

	public Request(String method, Map<String, Object> params) {
		this(method);
		this.params = params;
	}

}

package org.chrome;

import java.util.HashMap;
import java.util.Map;

import org.util.html.Json;

import lombok.Data;

@Data
public class Request {

	String method;
	Map<String, Object> params;
	int id;

	public void setParams(String k, Object v) {
		params = new HashMap<String, Object>() {
			{
				put(k, v);
			}
		};
	}

	public String toString() {
		return Json.toString(this);
	}
}

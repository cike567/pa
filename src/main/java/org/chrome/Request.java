package org.chrome;

import java.util.Map;

import org.util.html.Json;

import lombok.Data;

@Data
public class Request {

	String method;
	Map<String, Object> params;
	int id;

	public String toString() {
		return Json.toString(this);
	}
}

package org.chrome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ws.Message;

import lombok.Data;

@Data
public class Domains {

	public static Domains navigate(String url) {
		Map<String, Object> params = new HashMap<String, Object>() {
			{
				put("url", url);
			}
		};
		Request navigate = request("Page.navigate", params);
		List<Request> request = new ArrayList<Request>();
		request.add(navigate);
		return new Domains(request);

	}

	public void document() {

	}

	public static Request request(String method, Map<String, Object> params) {
		Request request = new Request();
		request.setMethod(method);
		request.setParams(params);
		return request;
	}

	List<Request> request;
	Message response;

	Domains(List<Request> request) {
		this.request = request;
		List<Integer> id = request.stream().map(Request::getId).collect(Collectors.toList());
		response = new Response(id);
	}

	public static Map<String, String[]> METHOD = new HashMap<String, String[]>() {
		{
			put("Page.navigate", new String[] { "url" });
		}
	};

}

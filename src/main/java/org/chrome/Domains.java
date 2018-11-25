package org.chrome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.chrome.response.Document;
import org.ws.Message;

import lombok.Data;

@Data
public class Domains {

	public static Domains navigate(String url) {
		Request navigate = new Request("Page.navigate");
		navigate.setParams("url", url);
		return new Domains(navigate);
	}

	public static Domains document(Devtools client) {
		Document response = new Document(client);
		Request request = response.getRequest();
		return new Domains(response, request);
	}

	Domains(Request... request) {
		this(new ArrayList<Request>(Arrays.asList(request)));
	}

	Domains(List<Request> request) {
		this(new Response(), request);
	}

	Domains(Response response, Request... request) {
		this(response, new ArrayList<Request>(Arrays.asList(request)));
	}

	Domains(Response response, List<Request> request) {
		this.request = request;
		List<Integer> id = request.stream().map(o -> {
			// o.setId(Devtools.id());
			return o.getId();
		}).collect(Collectors.toList());
		response.setId(id);
		this.response = response;
	}

	private List<Request> request;
	private Message response;

	public static Map<String, String[]> METHOD = new HashMap<String, String[]>() {
		{
			put("Page.navigate", new String[] { "url" });
		}
	};

}

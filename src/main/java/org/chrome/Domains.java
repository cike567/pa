package org.chrome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ws.Message;

import lombok.Data;

@Data
public class Domains {

	public static Domains navigate(String url) {
		Request navigate = new Request("Page.navigate");
		navigate.setParams("url", url);
		return new Domains(new ArrayList<Request>(Arrays.asList(navigate)));
	}

	public void document() {

	}

	List<Request> request;
	Message response;

	Domains(List<Request> request) {
		this.request = request;
		List<Integer> id = request.stream().map(o -> {
			o.setId(Devtools.id());
			return o.getId();
		}).collect(Collectors.toList());
		response = new Response(id);
	}

	public static Map<String, String[]> METHOD = new HashMap<String, String[]>() {
		{
			put("Page.navigate", new String[] { "url" });
		}
	};

}

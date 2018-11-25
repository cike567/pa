package org.chrome;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.chrome.request.Document;

import lombok.Data;

@Data
public class Domains {

	public String send(Request request) throws IOException, InterruptedException {
		return client.send(request);
	}

	public void navigate(String url) throws IOException, InterruptedException {
		Request navigate = new Request("Page.navigate");
		navigate.setParams("url", url);
		client.send(navigate);
	}

	public String document() throws IOException, InterruptedException {
		return new Document().html(client);
	}

	/*
	 * public Domains(Request request) { this(new Response(request.getId()),
	 * request); }
	 * 
	 * public Domains(Response response, Request request) { this.request = request;
	 * this.response = response; }
	 * 
	 * private Request request; private Message response;
	 */

	public Domains(Devtools client) {
		this.client = client;
	}

	private Devtools client;

	public static Map<String, String[]> METHOD = new HashMap<String, String[]>() {
		{
			put("Page.navigate", new String[] { "url" });
		}
	};

}

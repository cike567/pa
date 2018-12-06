package org.chrome;

import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.chrome.request.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ws.WebSocketClient;

import lombok.Getter;

/**
 * 
 * @author cike
 *
 */
@Getter
public class Protocol implements Runnable {

	public void navigate(String url) throws IOException, InterruptedException {
		Request navigate = new Request("Page.navigate");
		navigate.setParams("url", url);
		send(navigate);
	}

	public String document() throws IOException, InterruptedException {
		return new Document().html(this);
	}

	public void run() {
		while (true) {
			try {
				Domains d = domains.take();
				send(d.getRequest().toString());
			} catch (IOException | InterruptedException e) {
				log.error(e.getMessage());
			}
		}
	}

	void send(String text) throws IOException {
		client.send(text);
	}

	public String send(Request request) throws IOException, InterruptedException {
		return send(new Domains(request, id));
	}

	public String send(Domains d) throws IOException, InterruptedException {
		Response response = d.getResponse();
		client.setMessage(response);
		// send(request.toString());
		domains.put(d);
		return response.result();
	}

	public void result(String e) {
		rs.add(e);
	}

	public String result() {
		return rs.pop();
	}

	public Protocol(String uri, String id) {
		client = new WebSocketClient(uri);
		this.id = id;
	}

	private String id;

	private WebSocketClient client;

	private BlockingQueue<Domains> domains = new ArrayBlockingQueue<Domains>(1);

	private Stack<String> rs = new Stack<String>();

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

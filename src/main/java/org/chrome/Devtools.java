package org.chrome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Exec;
import org.util.html.Json;
import org.ws.Message;
import org.ws.WebSocketClient;

public class Devtools implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				send(request.take().toString());
			} catch (IOException | InterruptedException e) {
				log.error(e.getMessage());
			}
		}
	}

	Devtools(int port) {
		String exe = String.format(CHROME_HEADLESS, port);
		try {
			Exec.run(exe);
			String version = HttpEndpoints.LIST.get();
			String uri = new Json(version).object().getString(URL);
			client = new WebSocketClient(uri);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void send(String text) throws IOException {
		client.send(text);
	}

	public void send(Request request) throws IOException {
		send(new Domains(new ArrayList<Request>(Arrays.asList(request))));
	}

	public void send(Domains domains) throws IOException {
		List<Request> request = domains.getRequest();
		Message response = domains.getResponse();
		client.setMessage(response);
		request.stream().forEach((v) -> {
			// v.setId(id());
			this.request.add(v);
		});
		response.close();
	}

	public static Integer id() {
		return id.incrementAndGet();
	}

	private static AtomicInteger id = new AtomicInteger(0);

	private BlockingQueue<Request> request = new LinkedBlockingQueue<Request>();

	private String CHROME_HEADLESS = "chrome.exe --remote-debugging-port=%s --headless";

	private String URL = "webSocketDebuggerUrl";

	private WebSocketClient client;

	public final Logger log = LoggerFactory.getLogger(this.getClass());

}

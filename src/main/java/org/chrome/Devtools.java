package org.chrome;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Exec;
import org.util.html.Json;
import org.ws.WebSocketClient;

public class Devtools {

	public Devtools(int port) {
		String exe = String.format(CHROME_HEADLESS, port);
		try {
			Exec.run(exe);
			String version = get(HttpEndpoints.LIST);
			String uri = new Json(version).object().getString(URL);
			client = new WebSocketClient(uri);
			new Thread(() -> {
				while (true) {
					try {
						Request r = requests.take();
						send(r.toString());
					} catch (IOException | InterruptedException e) {
						log.error(e.getMessage());
					}
				}
			}).start();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public String get(HttpEndpoints endpoints, String... args) throws IOException {
		if (args.length == 0) {
			args = new String[] { "" };
		}
		return endpoints.get(args[0]);
	}

	public void send(String text) throws IOException {
		client.send(text);
	}

	public String send(Request request) throws IOException, InterruptedException {
		Integer id = request.getId();
		if (id == null || id == 0) {
			id = id();
			request.setId(id);
		}
		Response response = new Response(id);
		requests.add(request);
		client.setMessage(response);
		return response.result();
	}

	public static Integer id() {
		return id.incrementAndGet();
	}

	private static AtomicInteger id = new AtomicInteger(0);

	private BlockingQueue<Request> requests = new LinkedBlockingQueue<Request>();

	private String CHROME_HEADLESS = "chrome.exe --remote-debugging-port=%s --headless";

	private String URL = "webSocketDebuggerUrl";

	private WebSocketClient client;

	public final Logger log = LoggerFactory.getLogger(this.getClass());

}

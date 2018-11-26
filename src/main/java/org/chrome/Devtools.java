package org.chrome;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Exec;
import org.util.html.Json;
import org.ws.WebSocketClient;

/**
 * 
 * @author cike
 *
 */
public class Devtools {

	private static Devtools chrome = new Devtools(9222);

	public static Devtools chrome() {
		return chrome;
	}

	private Devtools(int port) {
		String exe = String.format(CHROME_HEADLESS, port);
		try {
			Exec.run(exe);
			String version = get(HttpEndpoints.LIST);
			String uri = new Json(version).object().getString(URL);
			client = new WebSocketClient(uri);
			run();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void run() {
		ThreadFactory factory = new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "sendRequest");
			}
		};
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), factory);
		executor.execute(() -> {
			while (true) {
				try {
					Request r = requests.take();
					send(r.toString());
				} catch (IOException | InterruptedException e) {
					log.error(e.getMessage());
				}
			}
		});
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

	private final String CHROME_HEADLESS = "chrome.exe --remote-debugging-port=%s --headless";

	private final String URL = "webSocketDebuggerUrl";

	private WebSocketClient client;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

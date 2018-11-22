package org.chrome;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Exec;
import org.util.WebSocketClient;
import org.util.html.Json;

public class Devtools implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				client.send(request.take().toString());
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
			/*
			 * BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
			 * while (true) { String line = r.readLine(); if (line.equals("quit")) break;
			 * client.send(line); }
			 */

		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

	public void send(String text) throws IOException {
		client.send(text);
	}

	public void send(Request e) throws IOException {
		this.request.add(e);
	}

	public void close() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(request.size());
		client.setLatch(latch);
		latch.await();
	}

	WebSocketClient client;
	public BlockingQueue<Request> request = new LinkedBlockingQueue<Request>();

	String CHROME_HEADLESS = "chrome.exe --remote-debugging-port=%s --headless";
	String URL = "webSocketDebuggerUrl";
	public static final Logger log = LoggerFactory.getLogger(Devtools.class);

}

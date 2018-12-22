package org.chrome;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.CMD;
import org.util.html.Http;
import org.util.html.Json;

import lombok.Getter;

/**
 * 
 * @author cike
 *
 */
@Getter
public class Devtools {

	private Devtools(int port) {
		String exe = String.format(CHROME_HEADLESS, port);
		try {
			CMD.exec(exe);
			String version = get(Endpoint.LIST);
			execute(version);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private Protocol execute(String rs) throws IOException {
		JSONObject object = new Json(rs).object();
		String uri = object.getString(URL);
		String id = object.getString("id");
		Protocol p = new Protocol(uri, id);
		protocol.put(id, p);
		executor.execute(p);
		return p;
	}

	private String get(Endpoint endpoints, String... args) throws IOException {
		return Http.get(endpoints, PORT, args);
	}

	private static Devtools chrome() {
		return chrome;
	}

	public static Devtools chrome(int port) {
		chrome = new Devtools(port);
		return chrome;
	}

	public static Protocol protocol() {
		return chrome.protocol.values().iterator().next();
	}

	public static Protocol protocol(String id) {
		return chrome.protocol.get(id);
	}

	// TODO
	public static Protocol open(String url) throws IOException {
		String rs = chrome.get(Endpoint.NEW, url);
		return chrome.execute(rs);
	}

	public static Integer id() {
		return id.incrementAndGet();
	}

	private final String CHROME_HEADLESS = "chrome.exe --remote-debugging-port=%s --headless";

	private final String URL = "webSocketDebuggerUrl";

	private static int PORT = 9222;

	private static Devtools chrome = new Devtools(PORT);

	private static AtomicInteger id = new AtomicInteger(0);

	private Map<String, Protocol> protocol = new ConcurrentHashMap<>();

	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1L, TimeUnit.MINUTES,
			new LinkedBlockingQueue<Runnable>());

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

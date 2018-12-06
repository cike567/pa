package org.chrome;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Exec;
import org.util.html.Json;

import lombok.Getter;

/**
 * 
 * @author cike
 *
 */
@Getter
public class Devtools {

	private static Devtools chrome = new Devtools(9222);

	private Devtools(int port) {
		ThreadFactory factory = new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "sendRequest");
			}
		};
		executor = new ThreadPoolExecutor(0, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
				factory);
		String exe = String.format(CHROME_HEADLESS, port);
		try {
			Exec.run(exe);
			String version = get(HttpEndpoints.LIST);
			execute(version);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public Protocol execute(String rs) throws IOException {
		JSONObject object = new Json(rs).object();
		String uri = object.getString(URL);
		String id = object.getString("id");
		Protocol p = new Protocol(uri, id);
		protocol.put(id, p);
		executor.execute(p);
		return p;
	}

	public String get(HttpEndpoints endpoints, String... args) throws IOException {
		if (args.length == 0) {
			args = new String[] { "" };
		}
		return endpoints.get(args[0]);
	}

	public static Devtools chrome() {
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
		String rs = chrome.get(HttpEndpoints.NEW, url);
		return chrome.execute(rs);
	}

	public static Integer id() {
		return id.incrementAndGet();
	}

	private static AtomicInteger id = new AtomicInteger(0);

	private static ThreadPoolExecutor executor;

	private Map<String, Protocol> protocol = new ConcurrentHashMap<>();

	private final String CHROME_HEADLESS = "chrome.exe --remote-debugging-port=%s --headless";

	private final String URL = "webSocketDebuggerUrl";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}

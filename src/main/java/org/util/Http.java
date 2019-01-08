package org.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.chrome.Devtools;
import org.chrome.Endpoint;
import org.chrome.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class Http {

	public static String curl(String url) throws IOException, InterruptedException {
		Protocol client = Devtools.protocol();
		client.navigate(url);
		String html = client.document();
		// File file = Files.write(html, "html");
		return html;
	}

	public static String get(String http) throws IOException {
		return get(http, GBK);
	}

	public static String get(String http, String charset) throws IOException {
		String rs = null;
		URL url = new URL(http);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream in = connection.getInputStream();
		rs = Stream.read(in, charset);
		in.close();
		return rs;
	}

	public static String get(Endpoint endpoint, int port, String... query) throws IOException {
		String http = String.format("http://localhost:%d%s", port, endpoint.path(query));
		return get(http, UTF8);
	}

	public static Map<String, String> args(String url) {
		String fix = "?";
		String temp = new Strings(url).sub(fix).value();
		Map<String, String> args = new HashMap<String, String>(10);
		String[] arg = temp.split("&");
		for (String t : arg) {
			String[] val = t.split("=");
			args.put(val[0], val[1]);
		}
		return args;
	}

	public final static String GBK = "GBK";

	public final static String UTF8 = "UTF-8";

	public static final Logger logger = LoggerFactory.getLogger(Http.class);

}

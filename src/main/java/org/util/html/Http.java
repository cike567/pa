package org.util.html;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.chrome.Domains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Strings;
import org.util.io.Stream;

/**
 * 
 * @author cike
 *
 */
public class Http {

	public static String curl(String url) throws IOException, InterruptedException {
		Domains d = new Domains();
		d.navigate(url);
		String html = d.document();
		// File file = Files.write(html, "html");
		return html;
	}

	public static String get(String http) throws IOException {
		return get(http, "GBK");
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

	public static final Logger logger = LoggerFactory.getLogger(Http.class);

}

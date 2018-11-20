package org.util.html;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Strings;
import org.util.io.Files;
import org.util.io.Stream;

public class Http {

	public static File curl(String url) throws IOException {
		String html = ChromeDevTools.html(url);
		File file = Files.write(html, "html");
		return file;
	}

	public static String get(String http) throws IOException {
		String rs = null;
		URL url = new URL(http);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream in = connection.getInputStream();
		rs = Stream.read(in, "GBK");
		in.close();
		return rs;
	}

	public static Map<String, String> args(String url) {
		String fix = "?";
		String temp = new Strings(url).sub(fix).value();
		Map<String, String> args = new HashMap<String, String>();
		String[] arg = temp.split("&");
		for (String t : arg) {
			String[] val = t.split("=");
			args.put(val[0], val[1]);
		}
		return args;
	}

	public static final Logger logger = LoggerFactory.getLogger(Http.class);

}

package org.util.html;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO
public class Jsoups {

	public static void select(String html, String keys) {
		Elements e = Jsoup.parse(html).select(keys);
		System.out.println(e);
	}

	public static String body(String http) throws IOException {
		return Jsoup.connect(http).get().toString();
	}

	/*
	 * public static Response response(String http) throws IOException { return
	 * Jsoup.connect(http).ignoreContentType(true).header("User-Agent",
	 * userAgent).execute(); }
	 */

	static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0";

	public static final Logger logger = LoggerFactory.getLogger(Jsoups.class);

}

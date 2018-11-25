package org.taobao;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.util.Foreach;
import org.util.html.Http;
import org.util.html.Json;
import org.util.io.Files;

public class Search {

	/*
	 * public static File searchJson(String url, String[] keys) throws IOException {
	 * String html = ChromeDevTools.html(url); Json json = new Json(html); String
	 * content = json.sub("g_page_config").select(keys).array().toString(); File
	 * file = Files.write(content, "json"); return file; }
	 */

	public static String samestyle(String q) throws IOException, InterruptedException {
		String[] keys = { "mods", "itemlist", "data", "auctions", "i2iTags", "samestyle", "url" };
		String url = String.format(SEARCH, q);
		String rs = search(url, keys, new Foreach() {

			@Override
			public String toString(Object args) {
				StringBuffer sb = new StringBuffer();
				if (!"".equals(args)) {
					sb.append(HTTP).append(args).append("\r\n");
				}
				return sb.toString();
			}

		});
		File file = Files.create(String.format("%s.csv", q));
		Files.write(rs, file);
		return rs;
	}

	public static File items(String url) throws IOException, InterruptedException {
		String[] keys = { "mods", "recitem", "data", "items" };
		String rs = search(url, keys, new Foreach() {

			@Override
			public String toString(Object args) {
				return Item.fromMap((Map) args).toString();
			}

		});
		String name = Http.args(url).get("nid");
		File file = Files.create(String.format("%s/%s.%s", "taobao", name, "csv"));
		Files.write(rs, file);
		return file;
	}

	/*
	 * private static File search(String q, int p) throws IOException { String url =
	 * String.format(SEARCH, q + Page.args(p)); return Http.curl(url); }
	 */

	private static String search(String url, String[] keys, Foreach foreach) throws IOException, InterruptedException {
		StringBuffer sb = new StringBuffer();
		int max = 1;
		for (int i = 1; i <= max; i++) {
			String html = Http.curl(url + Page.args(i));
			// if (i == 1) {
			max = Page.max(html);
			// File file = Files.write(html, "html");
			// }
			// sb.append(url).append("\r\n");
			Json json = new Json(html);
			json.sub("g_page_config").select(keys).array().toList().stream().forEach(map -> {
				sb.append(foreach.toString(map));
			});
		}
		return sb.toString();
	}

	private static String HTTP = "https://s.taobao.com";
	private static String SEARCH = "https://s.taobao.com/search?q=%s";

}

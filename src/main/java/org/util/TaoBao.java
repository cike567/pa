package org.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.taobao.Item;
import org.taobao.Page;
import org.util.html.ChromeDevTools;
import org.util.html.Json;

public class TaoBao {

	public static File html(String url) throws IOException {
		String html = ChromeDevTools.html(url);
		File file = Files.write(html, "html");
		return file;
	}

	public static File search(String q, int p) throws IOException {
		String url = String.format(SEARCH, q + Page.args(p));
		return html(url);
	}

	public static File searchJson(String url, String[] keys) throws IOException {
		String html = ChromeDevTools.html(url);
		Json json = new Json(html);
		String content = json.sub("g_page_config").select(keys).array().toString();
		File file = Files.write(content, "json");
		return file;
	}

	public static File search(String url, String[] keys) throws IOException {
		String rs = search(url, keys, new Foreach() {

			@Override
			public String toString(Object args) {
				return Item.fromMap((Map) args).toString();
			}

		});
		String name = Html.args(url).get("nid");
		File file = Files.create(String.format("%s/%s.%s", "taobao", name, "csv"));
		Files.write(rs, file);
		return file;
	}

	public static String searchSamestyle(String q) throws IOException {
		String[] keys = { "mods", "itemlist", "data", "auctions", "i2iTags", "samestyle", "url" };
		return searchA(q, keys);
	}

	public static String searchA(String q, String[] keys) throws IOException {
		String url = String.format(SEARCH, q);
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= Page.MAX; i++) {
			String html = ChromeDevTools.html(url + Page.args(i));
			Json json = new Json(html);
			json.sub("g_page_config").select(keys).array().toList().stream().forEach(item -> {
				if (!"".equals(item)) {
					sb.append(HTTP).append(item).append("\r\n");
				}
			});
		}
		File file = Files.write(sb.toString(), "csv");
		System.out.print(file.getAbsolutePath());
		return sb.toString();
	}

	private static String search(String url, String[] keys, Foreach foreach) throws IOException {
		StringBuffer sb = new StringBuffer();
		int max = 1;
		for (int i = 1; i <= max; i++) {
			String html = ChromeDevTools.html(url + Page.args(i));
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

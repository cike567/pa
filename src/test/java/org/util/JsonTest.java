package org.util;

import java.io.IOException;

import org.json.JSONArray;
import org.junit.Test;
import org.util.html.Json;
import org.util.io.Files;

/**
 * Unit test for simple App.
 */
public class JsonTest {

	@Test
	public void testSub() {
		String temp = "{\"id\":1}";
		Json json = new Json(temp);
		System.out.println(json.object());

		temp = "f[]a:[{\"id\":1},{}b";
		json = new Json(temp);
		System.out.println(json.subs());

		String sub = "fa";
		temp = "d:[d],fa\":[{\"id\":1}],{}b";
		json = new Json(temp);
		System.out.println(json.sub(sub).array());
	}

	@Test
	public void testSelect() {
		String temp = "[{\"a\":{\"d\":[{\"e\":{\"f\":1}},{\"e\":{\"f\":2}}]},\"b\":\"\"},{\"a\":{\"d\":[{\"e\":{\"f\":1}},{\"e\":{\"f\":2}}]},\"b\":\"\"}]";
		String[] keys = { "a", "d", "e", "f" };
		Json json = new Json(temp);
		System.out.println(json.select(keys).array());

	}

	@Test
	public void testJson() throws IOException {
		String fileName = "1541691636009.html";
		String sub = "itemlist";
		String[] keys = { "data", "auctions", "i2iTags", "similar", "url" };
		String temp = Files.read(fileName);
		Json json = new Json(temp);
		JSONArray array = json.sub(sub).select(keys).array();
		System.out.println(array);
	}

}

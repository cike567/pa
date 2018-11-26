package org.util;

import java.io.IOException;

import org.json.JSONArray;
import org.junit.Test;
import org.util.html.Json;
import org.util.io.Files;

/**
 * 
 * @author cike
 *
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

		temp = "{\"result\":{\"root\":{\"nodeName\":\"#document\",\"childNodeCount\":2,\"backendNodeId\":3,\"localName\":\"\",\"baseURL\":\"https://s.taobao.com/\",\"nodeValue\":\"\",\"children\":[{\"nodeName\":\"html\",\"backendNodeId\":5,\"localName\":\"\",\"systemId\":\"\",\"nodeValue\":\"\",\"nodeType\":10,\"nodeId\":2,\"parentId\":1,\"publicId\":\"\"},{\"nodeName\":\"HTML\",\"childNodeCount\":2,\"backendNodeId\":6,\"localName\":\"html\",\"nodeValue\":\"\",\"children\":[{\"nodeName\":\"HEAD\",\"childNodeCount\":36,\"backendNodeId\":7,\"localName\":\"head\",\"nodeValue\":\"\",\"attributes\":[],\"nodeType\":1,\"nodeId\":4,\"parentId\":3},{\"nodeName\":\"BODY\",\"childNodeCount\":11,\"backendNodeId\":8,\"localName\":\"body\",\"nodeValue\":\"\",\"attributes\":[\"id\",\"index\",\"data-spm\",\"1\"],\"nodeType\":1,\"nodeId\":5,\"parentId\":3}],\"frameId\":\"621B1F2E747CAF12FE7578B5AAA583B2\",\"attributes\":[\"class\",\"ks-webkit537 ks-webkit ks-chrome70 ks-chrome\"],\"nodeType\":1,\"nodeId\":3,\"parentId\":1}],\"documentURL\":\"https://s.taobao.com/\",\"nodeType\":9,\"nodeId\":1,\"xmlVersion\":\"\"}},\"id\":16}";
		json = new Json(temp);
		keys = new String[] { "result", "root", "nodeId" };
		json.select(keys);
		System.out.print(json.object());

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

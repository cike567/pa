package org.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.taobao.Page;
import org.taobao.Search;
import org.util.io.Files;

/**
 * Unit test for simple App.
 */
public class TBTest {

	@Test
	public void testPage() throws IOException {
		String html = "<input class=\"input J_Input\" type=\"number\" value=\"1\" min=\"1\" max=\"5\" aria-label=\"页码输入框\">";
		int max = Page.max(html);
		for (int i = 1; i <= max; i++) {
			String page = Page.args(i);
			System.out.println(page);
		}
	}

	// @Test
	public void testSamestyle() throws IOException {
		String q = "衬衣";
		Search.samestyle(q);
	}

	@Test
	public void testTB() throws IOException {
		// "singleauction", "data"
		// String url =
		// "https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=1&uniqpid=-366819228&nid=566590244997";
		String fileName = "src/main/resources/Samestyle.csv";
		List<String> list = Files.read(new File(fileName));
		for (String url : list) {
			File file = Search.items(url);
			System.out.println(file.getAbsolutePath());
			break;
		}

		System.out.println("#####");
	}

}

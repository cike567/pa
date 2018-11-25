package org.util;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.util.html.Http;

/**
 * Unit test for simple App.
 */
public class HtmlTest {

	@Test
	public void testArgs() {
		String url = "https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=1&uniqpid=-1774496277&nid=564869817581";
		String rs = Http.args(url).get("nid");
		System.out.println(rs);
	}

	@Test
	public void testCurl() throws IOException {
		String url = "https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=1&uniqpid=-1356293700&nid=575553233524";
		File file = Http.curl(url);
		System.out.print(file);
	}

}

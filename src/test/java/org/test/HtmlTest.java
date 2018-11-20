package org.test;

import org.junit.Test;
import org.util.Html;

/**
 * Unit test for simple App.
 */
public class HtmlTest {

	@Test
	public void testArgs() {
		String url = "https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=1&uniqpid=-1774496277&nid=564869817581";
		String rs = Html.args(url).get("nid");
		System.out.println(rs);
	}

}

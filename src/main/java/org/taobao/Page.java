package org.taobao;

import java.util.Random;

import org.util.Strings;

/**
 * 
 * @author cike
 *
 */
public class Page {

	public static String args(int index) {
		int p = 3;
		String page = "";
		int[] no = new int[3];
		if (index == 1) {
			return page;
		} else {
			no[0] = (index - 3) * (-3);
			no[1] = no[0];
			no[2] = 44 * (index - 1);
		}
		if (index == p) {
			if (new Random().nextBoolean()) {
				no[0] = -6;
			} else {
				no[1] = 6;
			}
		}
		page = String.format(PAGE, no[0], no[1], no[2]);
		return page;
	}

	public static int max(String html) {
		int max = 1;
		html = new Strings(html).sub("input J_Input").sub("max=\"").value("\"");
		if (!"".equals(html)) {
			max = Integer.parseInt(html);
		}
		return max;
	}

	public static Integer MAX = 1;

	private static String PAGE = "&bcoffset=%s&ntoffset=%s&p4ppushleft=1,48&s=%s";

}

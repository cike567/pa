package org.taobao;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author cike
 *
 */
@Data
@AllArgsConstructor
public class Item {

	String rawTitle;
	String itemLoc;
	String viewPrice;
	String nick;
	String picUrl;
	String detailUrl;

	public static Item fromMap(Map<String, String> map) {
		return new Item(map.get("raw_title"), map.get("item_loc"), map.get("view_price"), map.get("nick"),
				map.get("pic_url"), map.get("detail_url"));
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s\r\n", nick, rawTitle, viewPrice, itemLoc, picUrl, detailUrl);
	}
}

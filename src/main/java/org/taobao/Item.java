package org.taobao;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {

	String raw_title;
	String item_loc;
	String view_price;
	String nick;
	String pic_url;
	String detail_url;

	public static Item fromMap(Map<String, String> map) {
		return new Item(map.get("raw_title"), map.get("item_loc"), map.get("view_price"), map.get("nick"),
				map.get("pic_url"), map.get("detail_url"));
	}

	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s\r\n", nick, raw_title, view_price, item_loc, pic_url, detail_url);
	}
}

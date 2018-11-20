package org.util.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Json {

	/// select

	public Json select(String[] keys) {

		if (temp.startsWith("{")) {
			object = new JSONObject(temp);
		} else if (temp.startsWith("[")) {
			array = new JSONArray(temp);
		}

		for (String key : keys) {
			if (object != null) {
				Object value = object.get(key);
				if (value instanceof JSONObject) {
					object = (JSONObject) value;
					array = null;
					continue;
				} else if (value instanceof JSONArray) {
					array = (JSONArray) value;
					object = null;
					continue;
				}
			}
			if (array != null) {
				array = select(array, key);
				object = null;
				continue;
			}
		}

		return this;
	}

	private JSONArray select(JSONArray array, String key) {
		int size = array.length();
		JSONArray list = new JSONArray();
		for (int i = 0; i < size; i++) {
			Object item = array.get(i);
			if (item instanceof JSONObject) {
				JSONObject map = array.getJSONObject(i);
				// TODO
				if (map.has(key)) {
					list.put(map.get(key));
				}
			} else if (item instanceof JSONArray) {
				JSONArray sub = select((JSONArray) item, key);
				for (int j = 0; j < sub.length(); j++) {
					list.put(sub.get(j));
				}
			}

		}
		return list;
	}

	/// sub

	public Json sub(String sub) {
		int index = temp.indexOf(sub);
		temp = temp.substring(index + sub.length());
		return sub();
	}

	public Json sub() {
		byte[] b = temp.getBytes();
		Stack<Character> e = new Stack<Character>();
		int begin = -1;
		int end = -1;
		for (int i = 0; i < b.length; i++) {
			char c = (char) b[i];
			switch (c) {
			case '[':
			case '{':
				e.add(c);
				if (begin == -1) {
					begin = i;
				}
				break;
			case ']':
			case '}':
			case '"':
			case '\'':
				if (e.isEmpty()) {
					continue;
				}
				if (e.peek() == VALUE.get(c)) {
					e.pop();
				} else {
					e.add(c);
				}
				break;
			default:
				break;
			}
			if (begin >= 0 && e.empty()) {
				end = i - begin + 1;
				break;
			}
		}
		if (end > begin) {
			temp = new String(b, begin, end);
		}

		return this;
	}

	private int begin(byte[] b, int begin) {
		for (int i = begin; i < b.length; i++) {
			char c = (char) b[i];
			if (c == '[' || c == '{') {
				begin = i;
				break;
			} else {
				begin = -1;
			}
		}
		return begin;
	}

	private int end(byte[] b, int begin) {
		int end = -1;
		Stack<Character> e = new Stack<Character>();
		e.add((char) b[begin]);
		for (int i = begin + 1; i < b.length; i++) {
			char c = (char) b[i];
			switch (c) {
			case '[':
			case '{':
				e.add(c);
				break;
			case ']':
			case '}':
			case '"':
			case '\'':
				if (e.peek() == VALUE.get(c)) {
					e.pop();
				} else {
					e.add(c);
				}
				break;
			default:
				break;
			}
			if (e.empty()) {
				end = i;
				break;
			}
		}
		return end;
	}

	public List<String> subs() {
		byte[] b = temp.getBytes();
		List<String> rs = new ArrayList<String>();
		for (int i = 0; i < b.length; i++) {
			int begin = begin(b, i);
			int end = -1;
			if (begin >= 0) {
				i = begin;
				end = end(b, begin);
			} else {
				continue;
			}
			if (end >= 0) {
				i = end;
				rs.add(new String(b, begin, end - begin + 1));
			} else {
				continue;
			}
		}
		return rs;
	}

	/// value

	public JSONArray array() {
		if (array == null) {
			if (temp.startsWith("[")) {
				array = new JSONArray(temp);
			} else {
				array = new JSONArray();
			}
		}
		return array;
	}

	public JSONObject object() {
		if (object == null) {
			if (temp.startsWith("{")) {
				object = new JSONObject(temp);
			} else {
				object = new JSONObject();
			}
		}
		return object;
	}

	public Json(String temp) {
		this.temp = temp;
		String[] keys = {};
		select(keys);
	}

	private String temp;
	private JSONArray array;
	private JSONObject object;
	public final Logger log = LoggerFactory.getLogger(Json.class);

	private final Map<Character, Character> VALUE = new HashMap<Character, Character>() {
		{
			put(']', '[');
			put('}', '{');
			put('\'', '\'');
			put('"', '"');
		}
	};

}

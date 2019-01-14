package org.util;

import lombok.AllArgsConstructor;

/**
 * 
 * @author cike
 *
 */
@AllArgsConstructor
public class Strings {

	public Strings sub(String fix) {
		int index = temp.indexOf(fix);
		if (index > -1) {
			temp = temp.substring(temp.indexOf(fix) + fix.length());
		} else {
			temp = "";
		}
		return this;
	}

	public Strings sub(int b, int e) {
		if (e < 0) {
			e = temp.length() + e;
		}
		temp = temp.substring(b, e);
		return this;
	}

	public String value(String fix) {
		int index = temp.indexOf(fix);
		if (index > -1) {
			return new String(temp.getBytes(), 0, index);
		}
		return "";
	}

	public String toString() {
		return temp;
	}

	private String temp;

}

package models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Size {

	s9x9(9), s13x13(13), s19x19(19);

	private static final Map<String, Size> lookup = new HashMap<String, Size>();

	static {
		for (Size e : EnumSet.allOf(Size.class))
			lookup.put(e.getCode(), e);
	}

	public static Size get(String code) {
		return lookup.get(code);
	}

	private String code;

	private int length;

	Size(int l) {
		length = l;
		code = "" + l + "x" + l;
	}

	public String getCode() {
		return code;
	}

	public int getLength() {
		return length;
	}

	@Override
	public String toString() {
		return code;
	}

}

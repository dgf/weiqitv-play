package models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Size {

	s9x9(9), s13x13(13), s19x19(19);

	private static final Map<Integer, Size> lookup = new HashMap<Integer, Size>();

	static {
		for (Size e : EnumSet.allOf(Size.class))
			lookup.put(e.getLength(), e);
	}

	public static Size get(int length) {
		return lookup.get(length);
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

package models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Size {

	s9x9("9x9"), s13x13("13x13"), s19x19("19x19");

	private static final Map<String, Size> lookup = new HashMap<String, Size>();

	static {
		for (Size e : EnumSet.allOf(Size.class))
			lookup.put(e.getCode(), e);
	}

	public static Size get(String code) {
		return lookup.get(code);
	}

	private String code;

	Size(String rc) {
		code = rc;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code;
	}

}

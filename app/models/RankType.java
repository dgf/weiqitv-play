package models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RankType {

	NR("NR"), KYU("k"), DAN("d"), PRO("p");

	private static final Map<String, RankType> lookup = new HashMap<String, RankType>();

	static {
		for (RankType e : EnumSet.allOf(RankType.class))
			lookup.put(e.getCode(), e);
	}

	public static RankType get(String code) {
		return lookup.get(code);
	}

	private String code;

	RankType(String rc) {
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

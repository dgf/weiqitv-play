package models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RankType {

	NR("NR"), KYU("k"), DAN("d"), PRO("p");

	private static final Map<String, RankType> lookup = new HashMap<String, RankType>();

	static {
		for (RankType e : EnumSet.allOf(RankType.class))
			lookup.put(e.type, e);
	}

	public static RankType get(String type) {
		return lookup.get(type);
	}

	public String type;

	RankType(String t) {
		type = t;
	}

	@Override
	public String toString() {
		return type;
	}

}

package models;

public enum BlackOrWhite {
	BLACK("B"), WHITE("W");

	public static BlackOrWhite get(String code) {
		if (code.equals(WHITE.bOrW)) {
			return WHITE;
		} else if (code.equals(BLACK.bOrW)) {
			return BLACK;
		} else {
			return null;
		}
	}

	public final String bOrW;

	BlackOrWhite(String bOrW) {
		this.bOrW = bOrW;
	}
}

package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import play.data.validation.Required;

@Entity
public class Handicap extends AbstractCriterion<Handicap> {

	@Required
	public int stones;

	public static Handicap getHandicap(int stones) {
		Handicap handicap = find("stones", stones).first();
		if (handicap == null) {
			handicap = new Handicap();
			handicap.stones = stones;
			handicap.save();
		}
		return handicap;
	}

	@Override
	public String toString() {
		return "" + stones;
	}

	@Override
	public int compareTo(Handicap o) {
		return Integer.valueOf(stones).compareTo(o.stones);
	}

	public static List<String> getStones(Size size, int stones) {
		List<String> coordinates = new ArrayList<String>();
		switch (size) {
		case s9x9:
			switch (stones) {
			case 4:
				coordinates.add("G3");
			case 3:
				coordinates.add("C7");
			case 2:
				coordinates.add("G7");
				coordinates.add("C3");
			}
			break;
		case s13x13:
			switch (stones) {
			case 5:
				coordinates.add("G7");
			case 4:
				coordinates.add("K4");
			case 3:
				coordinates.add("D10");
			case 2:
				coordinates.add("D4");
				coordinates.add("K10");
			}
			break;
		case s19x19:
			switch (stones) {
			case 9:
				// tengen, see 5
			case 8:
				coordinates.add("K4");
				coordinates.add("K16");
			case 7:
				// tengen, see 5
			case 6:
				coordinates.add("Q10");
				coordinates.add("D10");
			case 5:
				if (stones != 6 && stones != 8) {
					coordinates.add("K10");
				}
			case 4:
				coordinates.add("Q4");
			case 3:
				coordinates.add("D16");
			case 2:
				coordinates.add("D4");
				coordinates.add("Q16");
			}
			break;
		}
		return coordinates;
	}
}
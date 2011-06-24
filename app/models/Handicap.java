package models;

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
}
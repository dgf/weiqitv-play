package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Handicap extends Model {

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
}
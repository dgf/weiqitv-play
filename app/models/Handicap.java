package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Handicap extends Model {

	@Required
	public int stones;

	public Handicap() {
		this(0);
	}

	public Handicap(int stones) {
		setHandicap(stones);
	}

	public Handicap(String stones) {
		setHandicap(stones);
	}

	// @Override
	public int compareTo(Handicap o) {
		return Integer.valueOf(stones).compareTo(o.stones);
	}

	// @Override
	public String getName() {
		return "handicap";
	}

	// @Override
	public String getValue() {
		return "" + stones;
	}

	public void setHandicap(int h) {
		if (h == 0 || (h > 1 && h < 10)) {
			this.stones = h;
		} else {
			throw new IllegalArgumentException("unsupported handicap: " + h);
		}
	}

	public void setHandicap(String stones) {
		setHandicap(Integer.parseInt(stones));
	}

	// @Override
	public void setValue(String value) {
		setHandicap(value);
	}

	@Override
	public String toString() {
		return "" + stones;
	}
}
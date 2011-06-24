package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Criteria extends Model {

	@Required
	public String name;

	@Required
	@ManyToOne
	public Rank maxRank;

	@Required
	@ManyToOne
	public Rank minRank;

	@Required
	@ManyToOne
	public Handicap minHandicap;

	@Required
	@ManyToOne
	public Handicap maxHandicap;

	@Override
	public String toString() {
		return name + ": " + minRank + "-" + maxRank + " " + minHandicap + "-" + maxHandicap;
	}

	public boolean matches(Game game) {
		if (minRank.le(game.black.rank) //
				&& maxRank.ge(game.black.rank) //
				&& minRank.le(game.white.rank) //
				&& maxRank.ge(game.white.rank) //
				&& minHandicap.le(game.handicap) //
				&& maxHandicap.ge(game.handicap) //
		) {
			return true;
		} else {
			return false;
		}
	}
}

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
}

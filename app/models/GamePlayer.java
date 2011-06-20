package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class GamePlayer extends Model {

	@ManyToOne
	@Required
	public Player player;

	@ManyToOne
	@Required
	public Rank rank;

	@Override
	public String toString() {
		return player.name + " " + rank;
	}

}

package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;

import play.Logger;
import play.data.validation.Required;

@Entity
public class Game extends TemporalModel {

	@ManyToOne
	@Required
	public GameServer server;

	@Required
	public String onlineId;

	@OneToOne(cascade = CascadeType.ALL)
	@Required
	public GamePlayer white;

	@OneToOne(cascade = CascadeType.ALL)
	@Required
	public GamePlayer black;

	@Required
	public Size size;

	@Required
	public double komi;

	@ManyToOne
	@Required
	public Handicap handicap;

	@Required
	public int turn;

	@OneToMany(mappedBy = "game")
	public List<Turn> turns;

	public String result;

	@Override
	public String toString() {
		return white + " vs. " + black + ": " + turn;
	}

	@PostPersist
	public void updateNextChannelGames() {
		Logger.info("game created %s", this);
	}
}

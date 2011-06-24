package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import play.Logger;
import play.data.validation.Required;
import events.ResultEvent;

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

	public static Game findByServerHostAndOnlineId(String serverHost, String onlineId) {
		GameServer gameServer = GameServer.find("host", serverHost).<GameServer> first();
		return Game.find("byServerAndOnlineId", gameServer, onlineId).first();
	}

	@PostUpdate
	public void publishResult() {
		if (result != null) {
			ResultEvent re = new ResultEvent();
			re.result = result;
			ChannelList.publishEvent(this, re);
			Logger.info("game ENDS %s", this);
		}
	}

	@PostPersist
	public void updateNextChannelGames() {
		Logger.info("game created %s", this);
	}
}

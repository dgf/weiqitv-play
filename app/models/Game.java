package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import jobs.CheckAsNextChannelGame;
import play.Logger;
import play.data.validation.Required;
import events.ResultEvent;

@Entity
public class Game extends TemporalModel {

	@ManyToOne
	@Required
	public GameServer server;

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
	public float komi;

	@ManyToOne
	@Required
	public Handicap handicap;

	@Required
	public int turn;

	@Required
	public int byo;

	public int observer;

	public String result;

	@Override
	public String toString() {
		return white + " vs. " + black;
	}

	public static Game findByServerHostAndOnlineId(String serverHost, String onlineId) {
		GameServer gameServer = GameServer.find("host", serverHost).<GameServer> first();
		return Game.find("byServerAndOnlineId", gameServer, onlineId).first();
	}

	@PostUpdate
	public void publishResult() {
		if (result != null) {
			ResultEvent re = new ResultEvent();
			re.message = result;
			ChannelList.publishEvent(this, re);
			Logger.info("game ENDS %s", this);
		}
	}

	@PostPersist
	public void updateNextChannelGames() {
		Logger.info("%s created", this);
		new CheckAsNextChannelGame(this).now();
	}

	/**
	 * @return rank of stronger player
	 */
	public Rank getRank() {
		return white.rank.ge(black.rank) ? white.rank : black.rank;
	}

	public static final int BETTER_GAME_TURN_OFFSET = 17;

	public boolean isBetterThan(Game game) {
		// TODO consider the influence of handicap, komi and time settings
		if (game == null || (getRank().ge(game.getRank()) //
				&& turn <= game.turn + BETTER_GAME_TURN_OFFSET)) {
			return true;
		}
		return false;
	}

	public static Game findByServerAndOnlineId(GameServer gameServer, String onlineId) {
		return Game.find("byServerAndOnlineId", gameServer, onlineId).first();
	}
}

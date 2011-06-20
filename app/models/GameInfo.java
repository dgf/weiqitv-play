package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class GameInfo extends Model {

	@ManyToOne
	@Required
	public GameServer server;

	@Required
	public String onlineId;

	@OneToOne
	@Required
	public GamePlayer white;

	@OneToOne
	@Required
	public GamePlayer black;

	@Required
	public Size size;

	@Required
	public double komi;

	@Temporal(TemporalType.TIMESTAMP)
	@Required
	public Date timestamp;

	public String result;

	@Required
	public int turn;

	@ManyToOne
	@Required
	public Handicap handicap;

	public static final int BETTER_GAME_MOVE_OFFSET = 17;

	public Rank getRankOfStrongerPlayer() {
		// Rank whiteRank = white.getRank();
		// Rank blackRank = black.getRank();
		return white.rank;
		// TODO compare ranks
		// return whiteRank.ge(blackRank) ? whiteRank : blackRank;
	}

	public boolean isBetterThan(GameInfo game) {
		// TODO consider the influence of handicap, komi and time settings
		// if (game == null ||
		// (getRankOfStrongerPlayer().ge(game.getRankOfStrongerPlayer()) //
		// && move <= game.getMove() + BETTER_GAME_MOVE_OFFSET)) {
		// return true;
		// }
		return false;
	}

	@Override
	public String toString() {
		return white + " vs. " + black + ": " + turn;
	}
}

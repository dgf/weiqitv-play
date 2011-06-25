package models;

import java.util.regex.Matcher;

public class WeiqiFactory {

	public static Game game(String white, String wRank, String black, String bRank, int size,
			int handicap, float komi) {
		Game game = new Game();
		game.white = gamePlayer(white, wRank);
		game.black = gamePlayer(black, bRank);
		game.komi = komi;
		game.size = Size.get(size);
		game.handicap = handicap(handicap);
		return game;
	}

	public static GamePlayer gamePlayer(String name, String rank) {
		GamePlayer gamePlayer = new GamePlayer();
		gamePlayer.player = player(name);
		gamePlayer.rank = rank(rank);
		return gamePlayer;
	}

	public static Rank rank(String nrAndTyp) {
		Matcher m = Rank.RANK_PATTERN.matcher(nrAndTyp);
		if (m.matches() == false) {
			throw new RuntimeException("unknown rank " + nrAndTyp);
		} else {
			Rank r = new Rank();
			r.nr = Integer.parseInt(m.group(1));
			r.type = RankType.get(m.group(2));
			return r;
		}
	}

	public static Player player(String name) {
		Player player = new Player();
		player.name = name;
		return player;
	}

	public static Handicap handicap(int stones) {
		Handicap handicap = new Handicap();
		handicap.stones = stones;
		return handicap;
	}

	public static Criteria criteria(String name) {
		Criteria criteria = new Criteria();
		criteria.minRank = rank("30k");
		criteria.maxRank = rank("9p");
		criteria.minHandicap = handicap(0);
		criteria.maxHandicap = handicap(9);
		criteria.name = name;
		criteria.size = Size.s19x19;
		return criteria;
	}
}

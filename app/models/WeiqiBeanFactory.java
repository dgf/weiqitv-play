package models;

import java.util.List;
import java.util.regex.Matcher;

public class WeiqiBeanFactory {

	public static Criteria criteria(String name, String minRank, String maxRank, int minHandi,
			int maxHandi, Size size) {
		Criteria criteria = new Criteria();
		criteria.minRank = rank(minRank);
		criteria.maxRank = rank(maxRank);
		criteria.minHandicap = handicap(minHandi);
		criteria.maxHandicap = handicap(maxHandi);
		criteria.name = name;
		criteria.size = size;
		return criteria;
	}

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

	public static Handicap handicap(int stones) {
		Handicap handicap = new Handicap();
		handicap.stones = stones;
		return handicap;
	}

	public static Move move(int number, BlackOrWhite player, String coordinate,
			List<String> prisoners, int seconds, int byo) {
		Move move = new Move();
		move.number = number;
		move.player = player;
		move.coordinate = coordinate;
		move.prisoners = Prisoner.toPrisonerList(move, prisoners);
		move.seconds = seconds;
		move.byo = byo;
		return move;
	}

	public static Player player(String name) {
		Player player = new Player();
		player.name = name;
		return player;
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
}

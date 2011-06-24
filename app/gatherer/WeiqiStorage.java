package gatherer;

import models.BlackOrWhite;

public interface WeiqiStorage {

	String addGame(String server, String id, String white, String wRank, String black,
			String bRank, int turn, int size, int handicap, float komi, int byo, int observer);

	String addMove(String server, String id, int number, BlackOrWhite player, String coordinate,
			int seconds, int byo, String[] prisoners);
}

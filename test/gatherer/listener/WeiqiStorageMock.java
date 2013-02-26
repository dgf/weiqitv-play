package gatherer.listener;

import gatherer.WeiqiStorage;
import models.BlackOrWhite;

class WeiqiStorageMock implements WeiqiStorage {
	boolean gameCalled = false;
	boolean moveCalled = false;
	boolean resultCalled = false;

	@Override
	public void addMove(String server, String id, int number, BlackOrWhite player,
			String coordinate, int seconds, int byo, String... prisoners) {
		moveCalled = true;
	}

	@Override
	public void addGame(String server, String id, String white, String wRank, String black,
			String bRank, int turn, int size, int handicap, float komi, int byo, int observer) {
		gameCalled = true;
	}

	@Override
	public void addResult(String server, String game, String result) {
		resultCalled = true;
	}
}
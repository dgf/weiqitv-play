package controllers;

import gatherer.IgsGatherer;
import gatherer.IgsOption;
import gatherer.WeiqiGameGatherer;
import gatherer.WeiqiStorage;
import jobs.CreateGame;
import jobs.CreateMove;
import jobs.SaveGameResult;
import models.BlackOrWhite;
import play.Logger;

public class GathererService implements WeiqiStorage {

	public static GathererService instance = new GathererService();

	private final WeiqiGameGatherer igs;

	private GathererService() {
		Logger.debug("start gatherer service");
		igs = new IgsGatherer(this);
	}

	public void startGatherer(String server, int port) {
		Logger.info("start gatherer %s:%s", server, port);
		igs.setServer(server, port);
		igs.connect();
		igs.loginAnonymous();
		igs.start();
	}

	public void restartGatherer(String server, int port) {
		Logger.info("restart gatherer %s:%s", server, port);
		stopGatherer();
		startGatherer(server, port);
	}

	public void stopGatherer() {
		Logger.info("stop gatherer");
		igs.stop();
		igs.logout();
		igs.disconnect();
	}

	public void observe(String number) {
		igs.observce(number);
	}

	public void toggle(IgsOption option) {
		igs.toggle(option);
	}

	@Override
	public void addGame(String server, String onlineId, String white, String wRank, String black,
			String bRank, int turn, int size, int handicap, float komi, int byo, int observer) {
		new CreateGame(server, onlineId, white, wRank, black, bRank, turn, size, handicap, komi,
				byo, observer).now();
	}

	@Override
	public void addMove(String server, String onlineId, int number, BlackOrWhite player,
			String coordinate, int seconds, int byo, String... prisoners) {
		new CreateMove(server, onlineId, number, player, coordinate, seconds, byo, prisoners).now();
	}

	@Override
	public void addResult(String server, String game, String result) {
		new SaveGameResult(server, game, result).now();
	}
}

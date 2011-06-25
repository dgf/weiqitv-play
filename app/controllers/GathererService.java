package controllers;

import gatherer.IgsGatherer;
import gatherer.WeiqiGameGatherer;
import gatherer.WeiqiStorage;

import java.util.List;

import jobs.CreateGame;
import jobs.CreateMove;
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

	@Override
	public String addGame(String server, String onlineId, String white, String wRank, String black,
			String bRank, int turn, int size, int handicap, float komi, int byo, int observer) {
		new CreateGame(server, onlineId, white, wRank, black, bRank, turn, size, handicap, komi,
				byo, observer).now();
		return onlineId;
	}

	@Override
	public String addMove(String server, String onlineId, int number, BlackOrWhite player,
			String coordinate, int seconds, int byo, List<String> prisoners) {
		new CreateMove(server, onlineId, number, player, coordinate, seconds, byo, prisoners).now();
		return onlineId;
	}
}

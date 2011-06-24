package jobs;

import models.Game;
import play.Logger;
import play.jobs.Job;

public class SaveGameResult extends Job {

	private final String game;
	private final String server;
	private final String result;

	public SaveGameResult(String server, String game, String result) {
		this.server = server;
		this.game = game;
		this.result = result;
	}

	@Override
	public void doJob() throws Exception {
		Game actual = Game.findByServerHostAndOnlineId(server, game);
		if (actual == null) {
			Logger.error("game %s %s doesn't exists", server, game);
			return;
		} else {
			actual.result = result;
			actual.save();
			Logger.debug("save game %s result ", game, result);
		}
	}
}

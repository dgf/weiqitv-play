package jobs;

import models.Game;
import play.Logger;
import play.jobs.Job;

public class SaveGameResult extends Job {

	private final String onlineId;
	private final String server;
	private final String result;

	public SaveGameResult(String server, String onlineId, String result) {
		this.server = server;
		this.onlineId = onlineId;
		this.result = result;
	}

	@Override
	public void doJob() throws Exception {
		Game actual = Game.findByServerHostAndOnlineId(server, onlineId);
		if (actual == null) {
			Logger.error("game %s %s doesn't exists", server, onlineId);
			return;
		} else {
			actual.result = result;
			actual.onlineId = null;
			actual.save();
			Logger.debug("save game %s result ", onlineId, result);
		}
	}

}

package jobs;

import java.util.regex.Matcher;

import models.Game;
import models.GamePlayer;
import models.GameServer;
import models.Handicap;
import models.Player;
import models.Rank;
import models.Size;
import play.Logger;
import play.jobs.Job;

public class CreateGame extends Job {

	private final Matcher m;
	private final String server;

	public CreateGame(String server, Matcher m) {
		this.server = server;
		this.m = m;
	}

	@Override
	public void doJob() throws Exception {
		String id = m.group(1);

		GameServer gameServer = GameServer.find("host", server).<GameServer> first();
		Game actual = Game.find("byServerAndOnlineId", gameServer, id).first();
		if (actual != null) {
			Logger.error("game %s %s exists", actual.server, actual.onlineId);
			return;
		}

		Game g = new Game();
		g.server = gameServer;
		g.onlineId = id;

		GamePlayer white = new GamePlayer();
		white.player = Player.getPlayer(m.group(2));
		white.rank = Rank.getRank(m.group(3));
		g.white = white;

		GamePlayer black = new GamePlayer();
		black.player = Player.getPlayer(m.group(4));
		black.rank = Rank.getRank(m.group(5));
		g.black = black;

		g.turn = Integer.parseInt(m.group(6));
		g.size = Size.get(Integer.parseInt(m.group(7)));
		g.handicap = Handicap.getHandicap(Integer.parseInt(m.group(8)));
		g.komi = Float.parseFloat(m.group(9));

		Logger.debug("save game %s", g);
		g.save();
	}
}

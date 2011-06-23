package jobs;

import java.util.List;

import models.Game;
import models.GameServer;
import models.Move;
import play.Logger;
import play.jobs.Job;

public class CreateMoves extends Job {

	private final String server;
	private final List<Move> moves;
	private final String onlineId;

	public CreateMoves(String server, String onlineId, List<Move> moves) {
		this.server = server;
		this.onlineId = onlineId;
		this.moves = moves;
	}

	@Override
	public void doJob() throws Exception {

		GameServer gameServer = GameServer.find("host", server).<GameServer> first();
		Game game = Game.find("byServerAndOnlineId", gameServer, onlineId).first();
		if (game == null) {
			Logger.error("game %s %s doesn't exists", server, onlineId);
			return;
		}

		for (Move move : moves) {
			Move m = Move.find("byGameAndNumber", game, move.number).<Move> first();
			if (m == null) { // doesn't exist
				move.game = game;
				if (game.turn < move.number) {
					game.turn = move.number;
					game.save();
				}
				move.save();
			} else {
				Logger.warn("move %s exist with id %s", move, m.id);
			}
		}

	}
}

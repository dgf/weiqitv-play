package jobs;

import events.MoveEvent;
import gatherer.listener.IgsMove;

import java.util.List;

import models.BlackOrWhite;
import models.ChannelList;
import models.Game;
import models.Handicap;
import models.Move;
import models.Prisoner;
import play.Logger;
import play.jobs.Job;

public class CreateMove extends Job {

	private final String server;
	private final String onlineId;
	private final int number;
	private final BlackOrWhite player;
	private final String coordinate;
	private final int seconds;
	private final int byo;
	private final List<String> prisoners;

	public CreateMove(String server, String onlineId, int number, BlackOrWhite player,
			String coordinate, int seconds, int byo, List<String> prisoners) {
		this.server = server;
		this.onlineId = onlineId;
		this.number = number;
		this.player = player;
		this.coordinate = coordinate;
		this.seconds = seconds;
		this.byo = byo;
		this.prisoners = prisoners;
	}

	@Override
	public void doJob() throws Exception {
		Game game = Game.findByServerHostAndOnlineId(server, onlineId);
		if (game == null) {
			Logger.error("game %s %s doesn't exists", server, onlineId);
			return;
		}

		Move m = Move.findByGameAndNumber(game, number);
		if (m != null) { // exist!
			Logger.warn("move %s exist with id %s", m, m.id);
		} else {
			Move move = null;
			if (coordinate.startsWith(IgsMove.HANDICAP_PREFIX)) { // add handicap moves
				String count = coordinate.substring(IgsMove.HANDICAP_PREFIX.length());
				List<String> stones = Handicap.getStones(game.size, Integer.parseInt(count));
				for (String stone : stones) {
					move = createMove(game, stone);
					updateChannelSockets(game, move);
				}
			} else { // add single move
				move = createMove(game, coordinate);
				updateChannelSockets(game, move);
			}

			// update game turn count
			if (game.turn < number) {
				game.turn = number;
				game.save();
			}
		}
	}

	private Move createMove(Game game, String coordinate) {
		Move move = new Move();
		move.game = game;
		move.byo = byo;
		move.coordinate = coordinate;
		move.number = number;
		move.player = player;
		move.prisoners = Prisoner.toList(move, prisoners);
		move.seconds = seconds;

		Logger.debug("create move %s", move);
		return move.save();
	}

	private void updateChannelSockets(Game game, Move move) {
		MoveEvent me = new MoveEvent();
		me.coordinate = move.coordinate;
		me.player = move.player;
		me.number = move.number;
		me.prisoners = Prisoner.toList(move.prisoners);
		me.time = move.seconds;
		me.byo = move.byo;

		Logger.debug("publish game move %s", this);
		ChannelList.publishEvent(game, me);
	}
}

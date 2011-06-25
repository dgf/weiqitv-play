package jobs;

import java.util.List;

import models.BlackOrWhite;
import models.Game;
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
	private final int byoYomi;
	private final List<String> prisoners;

	public CreateMove(String server, String onlineId, int number, BlackOrWhite player,
			String coordinate, int seconds, int byoYomi, List<String> prisoners) {
		this.server = server;
		this.onlineId = onlineId;
		this.number = number;
		this.player = player;
		this.coordinate = coordinate;
		this.seconds = seconds;
		this.byoYomi = byoYomi;
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
			Move move = new Move();
			move.game = game;
			move.byoYomi = byoYomi;
			move.coordinate = coordinate;
			move.number = number;
			move.player = player;
			move.prisoners = Prisoner.toList(move, prisoners);
			move.secondsLeft = seconds;
			move.save();

			if (game.turn < move.number) { // update game turn count
				game.turn = move.number;
				game.save();
			}
		}
	}
}

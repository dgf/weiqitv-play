package gatherer.listener;

import static gatherer.IgsConstants.*;
import gatherer.TelnetOutputListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jobs.CreateMoves;
import models.BlackOrWhite;
import models.Move;
import play.Logger;

// 15 Game 43 I: ryoken (14 359 4) vs rokujidoo (15 598 24)
// 15 269(W): J9 K9
// 2
// 1 8
public class IgsMove implements TelnetOutputListener {

	private String server;

	private boolean retrieveMoveList;

	private GameStatus status;

	private List<Move> moves;

	private static final Pattern GAME_STATUS = Pattern.compile("15 Game +" //
			+ "([0-9]+) " // game nr
			+ "I: " // ONLY IGS GO GAMES, CHESS IS BORING
			// white player: name, prisoners, seconds, byo-yomi period
			+ "([^ ]+) " + "\\(([0-9]+) +([0-9]+) +([0-9-]+)\\)" + " vs "
			// black player: ...
			+ "([^ ]+) " + "\\(([0-9]+) +([0-9]+) +([0-9-]+)\\)" + ".*");

	private static final Pattern MOVE_PATTERN = Pattern.compile("15 +" //
			+ "([0-9]+)\\((W|B)\\): " // move nr and player
			+ "([A-HJ-T0-9 ]+)"); // coordinates

	public IgsMove(String server) {
		this.server = server;
	}

	@Override
	public boolean notify(String line) {

		if (retrieveMoveList) {
			if (line.equals(OBSERVED)) { // ignore flag output for observed move
				Logger.debug("end of observed");
				return true;
			} else if (line.equals(OK) //
					|| line.equals(MOVE_LIST_OK)) { // all moves readed
				new CreateMoves(server, status.getId(), moves).now();
				retrieveMoveList = false;
				return false;
			}
		}

		// Game 13 I: SHIGE08 (2 546 17) vs YMT123 (3 462 1)
		Matcher gsm = GAME_STATUS.matcher(line);
		if (gsm.matches()) {
			moves = new ArrayList<Move>();
			status = getGameStatus(gsm);
			Logger.debug("game status: " + status);
			retrieveMoveList = true;
			return true;
		}

		// 15 269(W): J9 K9
		Matcher mpm = MOVE_PATTERN.matcher(line);
		if (mpm.matches()) {
			Move turn = getMove(mpm);
			moves.add(turn);
			Logger.debug("move: " + turn);
			return true;
		}

		return false;
	}

	private GameStatus getGameStatus(Matcher gsm) {
		String id = gsm.group(1);

		GamePlayerStatus white = new GamePlayerStatus();
		white.setSeconds(Integer.parseInt(gsm.group(3)));
		white.setPrisoners(Integer.parseInt(gsm.group(4)));
		white.setByoYomi(Integer.parseInt(gsm.group(5)));

		GamePlayerStatus black = new GamePlayerStatus();
		black.setSeconds(Integer.parseInt(gsm.group(3)));
		black.setPrisoners(Integer.parseInt(gsm.group(4)));
		black.setByoYomi(Integer.parseInt(gsm.group(5)));

		return new GameStatus(id, white, black);
	}

	private Move getMove(Matcher mpm) {
		Move move = new Move();
		move.number = Integer.parseInt(mpm.group(1));

		BlackOrWhite player = BlackOrWhite.get(mpm.group(2));
		GamePlayerStatus gps;
		if (player == BlackOrWhite.WHITE) {
			gps = status.getWhite();
		} else {
			gps = status.getBlack();
		}

		List<String> list = Arrays.asList(mpm.group(3).split(" "));
		move.player = player;
		move.coordinate = list.get(0);
		move.secondsLeft = gps.getSeconds();
		move.byoYomi = gps.getByoYomi();
		// move.prisoners = list.toArray(new String[] {});

		return move;
	}
}

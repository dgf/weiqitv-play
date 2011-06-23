package gatherer.listener;

import static gatherer.IgsConstants.*;
import gatherer.TelnetOutputListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jobs.CreateGame;
import play.Logger;

/**
 * filter igs game list entries
 */
public class IgsGame implements TelnetOutputListener {

	private final String server;

	private boolean retrieveGameList;

	public IgsGame(String server) {
		this.server = server;
	}

	private static final Pattern GAME_PATTERN = Pattern.compile("7 " //
			+ "\\[ *([0-9]+)\\] +" // game nr
			+ PLAYER_PATTERN + " vs. +" + PLAYER_PATTERN // white and black
			+ " \\( *([0-9]+) +([0-9]+) +" // move, size
			+ "([0-9]+) +([0-9\\.\\-]+) +" // handi, komi
			+ "(.*)");

	@Override
	public synchronized boolean notify(String line) {
		if (retrieveGameList && line.equals(OK)) {
			Logger.debug("game list retrieved");
			retrieveGameList = false;
			return false;
		}

		if (line.equals(GAME_HEADER)) {
			Logger.debug("start game list reading");
			retrieveGameList = true;
			return true;
		}

		Matcher m = GAME_PATTERN.matcher(line);
		if (m.matches() == false) {
			return false;
		} else {
			new CreateGame(server, m).now();
			return true;
		}
	}

}

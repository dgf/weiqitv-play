package gatherer.listener;

import static gatherer.IgsConstants.*;
import gatherer.TelnetOutputListener;
import gatherer.WeiqiStorage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.Logger;

/**
 * filter igs game list entries
 */
public class IgsGame implements TelnetOutputListener {

	private final String server;

	private boolean retrieveGameList;

	private final WeiqiStorage storage;

	public IgsGame(String server, WeiqiStorage storage) {
		this.server = server;
		this.storage = storage;
	}

	/**
	 * <pre>
	 * 7 \\[ *([0-9]+)\\] +([^ ]+) \\[ *([0-9kdp]+|NR)[^\\]]*\\] vs\\. +([^ ]+) \\[ *([0-9kdp]+|NR)[^\\]]*\\] +\\( *([0-9]+) +([0-9]+) +([0-9]+) +([0-9\\.\\-]+) +([0-9]+) +I\\) +\\(([0-9]+)\\)
	 * </pre>
	 */
	private static final Pattern GAME_PATTERN = Pattern.compile("7 " //
			+ "\\[ *([0-9]+)\\] +" // game nr
			+ PLAYER_PATTERN + " vs\\. +" + PLAYER_PATTERN // white and black
			+ " +\\( *([0-9]+) +([0-9]+) +" // move, size
			+ "([0-9]+) +([0-9\\.\\-]+) +" // handi, komi
			+ "([0-9]+) +I\\) +\\( *([0-9]+) *\\)"); // byo, observer

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
		} else { // store game
			String id = m.group(1);
			String white = m.group(2);
			String wRank = m.group(3);
			String black = m.group(4);
			String bRank = m.group(5);
			int turn = Integer.parseInt(m.group(6));
			int size = Integer.parseInt(m.group(7));
			int handicap = Integer.parseInt(m.group(8));
			float komi = Float.parseFloat(m.group(9));
			int byo = Integer.parseInt(m.group(10));
			int observer = Integer.parseInt(m.group(11));

			id = storage.addGame(server, id, white, wRank, black, bRank, //
					turn, size, handicap, komi, byo, observer);
			Logger.debug("game %s: %s", id, line);
			return true;
		}
	}
}

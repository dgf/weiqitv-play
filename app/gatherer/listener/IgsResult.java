package gatherer.listener;

import static gatherer.IgsConstants.*;
import gatherer.TelnetOutputListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jobs.SaveGameResult;
import play.Logger;

public class IgsResult implements TelnetOutputListener {

	private boolean retrieveConnect;

	// 21 {Game 150: mstkigo vs orihiyoshi : Black resigns.}
	// 21 {Game 543: ks260820 vs MIYLI : W 69.5 B 79.0}
	// 1 5
	private static final Pattern CONNECT = Pattern.compile("21 \\{Game " //
			+ "([0-9]+): ([^ ]+) vs ([^ ]+) : (.*).\\}");

	private final String server;

	private String game;

	private String result;

	public IgsResult(String server) {
		this.server = server;
	}

	@Override
	public boolean notify(String line) {

		if (retrieveConnect && line.equals(OK)) {
			new SaveGameResult(server, game, result).now();
			retrieveConnect = false;
			return false;
		}

		Matcher m = CONNECT.matcher(line);
		if (m.matches()) {
			retrieveConnect = true;
			game = m.group(1);
			result = m.group(4);
			Logger.trace("connect %s", line);
			return true;
		}

		return false;
	}

}

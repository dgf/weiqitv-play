package gatherer.listener;

import static gatherer.IgsConstants.*;

import gatherer.TelnetOutputListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.Logger;

public class IgsResult implements TelnetOutputListener {

	private boolean retrieveConnect;

	// 21 {Game 150: mstkigo vs orihiyoshi : Black resigns.}
	// 1 5
	private static final Pattern CONNECT = Pattern.compile("21 \\{Game " //
			+ "([0-9]+): ([^ ]+) vs ([^ ]+) : (.*).\\}");

	@Override
	public boolean notify(String line) {

		if (retrieveConnect && line.equals(OK)) {
			retrieveConnect = false;
			return false;
		}

		Matcher m = CONNECT.matcher(line);
		if (m.matches()) {
			retrieveConnect = true;
			Logger.trace("connect %s", line);
			return true;
		}

		Logger.debug("#### OTHER FOO  #### " + line);
		return false;
	}

}

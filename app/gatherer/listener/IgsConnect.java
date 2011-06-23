package gatherer.listener;

import static gatherer.IgsConstants.*;

import gatherer.TelnetOutputListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.Logger;

public class IgsConnect implements TelnetOutputListener {

	private boolean retrieveConnect;

	// 21 {guest3993 [NR ] has connected.}
	// 1 5
	private static final Pattern CONNECT = Pattern.compile("21 \\{" //
			+ PLAYER_PATTERN + " has connected.\\}");

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

		return false;
	}

}

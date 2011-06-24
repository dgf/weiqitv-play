package gatherer.listener;

import static gatherer.IgsConstants.*;
import gatherer.TelnetOutputListener;
import play.Logger;

public class IgsVerboseTraffic implements TelnetOutputListener {

	private boolean retrieveConnect;

	@Override
	public boolean notify(String line) {
		if (line == null || line.equals("") || line.equals(OK)) {
			// ignore it
		} else {
			Logger.debug("#### OTHER FOO  #### " + line);
		}
		return false;
	}

}

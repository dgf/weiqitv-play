package gatherer.listener;

import gatherer.TelnetOutputListener;
import play.Logger;

import static gatherer.IgsConstants.OK;

public class IgsVerboseTraffic implements TelnetOutputListener {

    @Override
    public boolean notify(String line) {
        if (line == null || line.equals("") || line.equals(OK)) {
            // ignore it
        } else {
            Logger.trace("telnet message ignored, line: " + line);
        }
        return false;
    }

}

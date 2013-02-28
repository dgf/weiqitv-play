package gatherer.listener;

import gatherer.TelnetOutputListener;
import play.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static gatherer.IgsConstants.OK;

public class IgsDisconnect implements TelnetOutputListener {

    private boolean retrieveDisconnect;

    // 21 {pat33 has disconnected}
    // 1 5
    private static final Pattern DISCONNECT = Pattern.compile("21 \\{" //
            + "([^ ]+)" + " has disconnected\\}");

    @Override
    public boolean notify(String line) {

        if (retrieveDisconnect && line.equals(OK)) {
            retrieveDisconnect = false;
            return false;
        }

        Matcher m = DISCONNECT.matcher(line);
        if (m.matches()) {
            retrieveDisconnect = true;
            Logger.trace("disconnect %s", line);
            return true;
        }

        return false;
    }

}

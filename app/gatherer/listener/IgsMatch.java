package gatherer.listener;

import gatherer.IgsGatherer;
import gatherer.TelnetOutputListener;
import play.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static gatherer.IgsConstants.*;

public class IgsMatch implements TelnetOutputListener {

    private boolean retrieveMatch;

    // 21 {Match 304: fp1172 [ 9k*] vs. MR38251218 [10k*] }
    // 1 5
    private static final Pattern MATCH = Pattern.compile("21 \\{Match " //
            + "([0-9]+): " + PLAYER_PATTERN + " vs. " + PLAYER_PATTERN + " \\}");

    private final IgsGatherer igs;

    public IgsMatch(IgsGatherer igs) {
        this.igs = igs;
    }

    @Override
    public boolean notify(String line) {

        if (retrieveMatch && line.equals(OK)) {
            retrieveMatch = false;
            return false;
        }

        Matcher m = MATCH.matcher(line);
        if (m.matches()) {
            retrieveMatch = true;
            Logger.trace("new match %s", line);
            igs.getGameInfo(m.group(1));
            return true;
        }

        return false;
    }

}

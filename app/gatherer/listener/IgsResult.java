package gatherer.listener;

import gatherer.TelnetOutputListener;
import gatherer.WeiqiStorage;
import play.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static gatherer.IgsConstants.*;

// 21 {Game 150: mstkigo vs orihiyoshi : Black resigns.}
// 21 {Game 543: ks260820 vs MIYLI : W 69.5 B 79.0}
// 21 {Game 165: cli vs boudu : Black forfeits on time.}
// 9 {Game 348: weslie vs Yatsugatak : W 71.5 B 83.0} // observed game ends
// 1 5
public class IgsResult implements TelnetOutputListener {

    public boolean retrieveResult;

    private static final Pattern CONNECT = Pattern.compile("(21|9) \\{Game " //
            + "([0-9]+): ([^ ]+) vs ([^ ]+) : (.*)\\}");

    private final String server;

    private String game;

    private String result;

    private final WeiqiStorage storage;

    public IgsResult(String server, WeiqiStorage storage) {
        this.server = server;
        this.storage = storage;
    }

    @Override
    public boolean notify(String line) {

        if (retrieveResult && (line.equals(OK) || line.equals(MOVE_LIST_OK))) {
            storage.addResult(server, game, result);
            retrieveResult = false;
            return false;
        }

        Matcher m = CONNECT.matcher(line);
        if (m.matches()) {
            retrieveResult = true;
            game = m.group(2);
            result = m.group(5);
            Logger.debug("result %s", line);
            return true;
        }

        return false;
    }
}

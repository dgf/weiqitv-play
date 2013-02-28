package jobs;

import events.MoveEvent;
import gatherer.listener.IgsMoves;
import models.BlackOrWhite;
import models.ChannelList;
import models.Game;
import models.Handicap;
import models.Move;
import models.Prisoner;
import play.Logger;
import play.jobs.Job;

import java.util.List;

import static models.WeiqiJpaFactory.move;

public class CreateMove extends Job {

    private final String server;
    private final String onlineId;
    private final int number;
    private final BlackOrWhite player;
    private final String coordinate;
    private final int seconds;
    private final int byo;
    private final String[] prisoners;

    public CreateMove(String server, String onlineId, int number, BlackOrWhite player,
                      String coordinate, int seconds, int byo, String... prisoners) {
        this.server = server;
        this.onlineId = onlineId;
        this.number = number;
        this.player = player;
        this.coordinate = coordinate;
        this.seconds = seconds;
        this.byo = byo;
        this.prisoners = prisoners;
    }

    @Override
    public void doJob() throws Exception {
        Game game = Game.findByServerHostAndOnlineId(server, onlineId);
        if (game == null) {
            Logger.error("game %s %s doesn't exists", server, onlineId);
            return;
        }

        Move m = Move.findByGameAndNumber(game, number);
        if (m != null) { // exist!
            Logger.warn("move %s exist with id %s", m, m.id);
        } else {
            Move move = null;
            // add handicap moves
            if (coordinate.startsWith(IgsMoves.HANDICAP_PREFIX)) {
                String count = coordinate.substring(IgsMoves.HANDICAP_PREFIX.length());
                List<String> stones = Handicap.getStones(game.size, Integer.parseInt(count));
                for (String stone : stones) {
                    move = createMove(game, stone);
                    updateChannelSockets(game, move);
                }
            } else { // add single move
                move = createMove(game, coordinate);
                updateChannelSockets(game, move);
            }

            // update game turn count
            if (game.turn < number) {
                game.turn = number;
                game.save();
            }
        }
    }

    private Move createMove(Game game, String coordinate) {
        Move move = move(game, number, player, coordinate, seconds, byo, prisoners);
        Logger.debug("create move %s", move);
        return move;
    }

    private void updateChannelSockets(Game game, Move move) {
        MoveEvent me = new MoveEvent();
        me.coordinate = move.coordinate;
        me.player = move.player;
        me.number = move.number;
        me.prisoners = Prisoner.toStringList(move.prisoners);
        me.time = move.seconds;
        me.byo = move.byo;

        Logger.debug("publish game move %s", this);
        ChannelList.publishEvent(game, me);
    }
}

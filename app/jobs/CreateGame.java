package jobs;

import models.Channel;
import models.Criteria;
import models.Game;
import models.GameServer;
import play.Logger;
import play.jobs.Job;

import java.util.List;

import static models.WeiqiJpaFactory.game;

public class CreateGame extends Job {

    private final String server;
    private final String onlineId;
    private final String white;
    private final String wRank;
    private final String black;
    private final String bRank;
    private final int turn;
    private final int size;
    private final int handicap;
    private final float komi;
    private final int byo;
    private final int observer;

    public CreateGame(String server, String onlineId, String white, String wRank, String black,
                      String bRank, int turn, int size, int handicap, float komi, int byoYomi, int observer) {
        this.server = server;
        this.onlineId = onlineId;
        this.white = white;
        this.wRank = wRank;
        this.black = black;
        this.bRank = bRank;
        this.turn = turn;
        this.size = size;
        this.handicap = handicap;
        this.komi = komi;
        this.byo = byoYomi;
        this.observer = observer;
    }

    @Override
    public void doJob() throws Exception {
        GameServer gameServer = GameServer.findByHost(server);
        Game actual = Game.findByServerAndOnlineId(gameServer, onlineId);
        if (actual != null) {
            Logger.warn("game %s %s exists", actual.server, actual.onlineId);
        }

        updateNextChannelGames(createGame(gameServer));
    }

    private Game createGame(GameServer gameServer) {

        Game game = game(white, wRank, black, bRank, size, handicap, komi);

        game.server = gameServer;
        game.onlineId = onlineId;

        game.turn = turn;
        game.byo = byo;
        game.observer = observer;

        Logger.info("save game %s", game);
        return game.save();
    }

    /**
     * match a game with each criteria and update related channels
     * TODO use an upcoming game list per channel
     */
    private void updateNextChannelGames(Game game) {

        List<Criteria> criteriaList = Criteria.all().fetch();
        for (Criteria criteria : criteriaList) {
            Logger.debug("%s matches? %s", criteria.name, game);
            if (criteria.matches(game)) {
                Logger.debug("%s matches!", criteria.name);
                List<Channel> channels = Channel.findByCriteria(criteria);
                for (Channel channel : channels) {
                    if (game.isBetterThan(channel.next)) {
                        channel.next = game;
                        Logger.info("%s is the next game on channel %s", game, channel.title);
                        channel.save();
                    }
                }
            }
        }
    }
}

package controllers;

import gatherer.IgsGatherer;
import gatherer.IgsOption;
import gatherer.WeiqiGameGatherer;
import gatherer.WeiqiStorage;
import groovy.util.ResourceConnector;
import jobs.CreateGame;
import jobs.CreateMove;
import jobs.SaveResult;
import models.BlackOrWhite;
import models.GameServer;
import play.Logger;

public class GathererService implements WeiqiStorage {

    public static GathererService instance = new GathererService();

    private final WeiqiGameGatherer igs;

    private GathererService() {
        Logger.info("start gatherer service");
        igs = new IgsGatherer(this);
    }

    public void start() {
        GameServer gs = GameServer.all().first();
        if (gs == null) {
            Logger.warn("please configure a game server");
        } else {
            Logger.info("start gatherer %s:%s", gs.host, gs.port);
            igs.setServer(gs.host, gs.port);
            igs.connect();
            igs.loginAnonymous();
            igs.start();
        }
    }

    public boolean isActive() {
        return igs.isConnected() && igs.isLoggedIn();
    }

    public void observe(String number) {
        igs.observe(number);
    }

    public void toggle(IgsOption option) {
        igs.toggle(option);
    }

    @Override
    public void addGame(String server, String onlineId, String white, String wRank, String black,
                        String bRank, int turn, int size, int handicap, float komi, int byo, int observer) {
        new CreateGame(server, onlineId, white, wRank, black, bRank, turn, size, handicap, komi,
                byo, observer).now();
    }

    @Override
    public void addMove(String server, String onlineId, int number, String player,
                        String coordinate, int seconds, int byo, String... prisoners) {
        new CreateMove(server, onlineId, number, player, coordinate, seconds, byo, prisoners).now();
    }

    @Override
    public void addResult(String server, String game, String result) {
        new SaveResult(server, game, result).now();
    }
}

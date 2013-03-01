package controllers;

import events.MessageEvent;
import gatherer.IgsOption;
import jobs.NextGame;
import jobs.Reset;
import models.ChannelList;
import models.Game;
import play.Logger;
import play.mvc.With;

import java.util.List;

@With(Secure.class)
public class Admin extends AbstractController {

    @Check("isAdmin")
    public static void index() {
        render();
    }

    @Check("isAdmin")
    public static void observe(int number, String gameId) {
        Logger.info("observe game %s on channel %s", gameId, number);
        GathererService.instance.observe(gameId);
        flash.success("observe %s", gameId);
        WeiqiTV.watch(number);
    }

    @Check("isAdmin")
    public static void next(int number) throws Exception {
        Logger.info("switch to next game on channel %s", number);
        await(new NextGame(number).now());
        flash.success("switch to next game on channel %s", number);
        WeiqiTV.watch(number);
    }

    @Check("isAdmin")
    public static void reset() {
        Logger.info("reset database");
        await(new Reset(Security.connected()).now());
        flash.success("database reloaded");
        index();
    }

    @Check("isAdmin")
    public static void toggle(String option) {
        Logger.info("toggle option %s", option);
        GathererService.instance.toggle(IgsOption.get(option));
        flash.success("toggle %s", option);
        index();
    }

    @Check("isAdmin")
    public static void games() {
        List<Game> games = Game.findAll();
        render(games);
    }

    @Check("isAdmin")
    public static void broadcast(String message) {
        Logger.info("broadcast %s", message);
        MessageEvent me = new MessageEvent();
        me.message = message;
        ChannelList.instance.publishEvent(me);
        flash.success("broadcast %s", message);
        index();
    }

}

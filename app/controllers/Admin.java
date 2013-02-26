package controllers;

import events.MessageEvent;
import gatherer.IgsOption;
import jobs.ShowNextGameOnChannel;
import models.ChannelList;
import models.Game;
import models.GameServer;
import play.Logger;
import play.mvc.With;
import play.test.Fixtures;

import java.util.List;

@With(Secure.class)
public class Admin extends AbstractController {

	@Check("isAdmin")
	public static void index() {
		render();
	}

	@Check("isAdmin")
	public static void observe(int number, String gameId) {
		Logger.debug("observe game %s on channel %s", gameId, number);
		GathererService.instance.observe(gameId);
		flash.success("observe %s", gameId);
		WeiqiTV.watch(number);
	}

	@Check("isAdmin")
	public static void next(int number) throws Exception {
		Logger.debug("switch to next game on channel %s", number);
		new ShowNextGameOnChannel(number).now();
		flash.success("switch to next game on channel %s", number);
		WeiqiTV.watch(number);
	}

	@Check("isAdmin")
	public static void reset() {
		Logger.debug("reset database");
		Fixtures.deleteDatabase();
		Fixtures.loadModels("initial-data.yml");
		flash.success("database reloaded");
		index();
	}

	@Check("isAdmin")
	public static void start() {
		Logger.debug("start gatherer");
		GameServer igs = GameServer.all().first();
		GathererService.instance.startGatherer(igs.host, igs.port);
		flash.success("gatherer started with %s:%s", igs.host, igs.port);
		index();
	}

	@Check("isAdmin")
	public static void stop() {
		Logger.debug("stop gatherer");
		GathererService.instance.stopGatherer();
		flash.success("gatherer stopped");
		index();
	}

	@Check("isAdmin")
	public static void toggle(String option) {
		Logger.debug("toggle option %s", option);
		GathererService.instance.toggle(IgsOption.get(option));
		flash.success("toogle %s", option);
		index();
	}

	@Check("isAdmin")
	public static void games() {
		List<Game> games = Game.findAll();
		render(games);
	}

	@Check("isAdmin")
	public static void broadcast(String message) {
		Logger.debug("broadcast %s", message);
		MessageEvent me = new MessageEvent();
		me.message = message;
		ChannelList.instance.publishEvent(me);
		flash.success("broadcast %s", message);
		index();
	}

}

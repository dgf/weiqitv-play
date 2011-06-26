package controllers;

import java.util.List;

import jobs.ShowNextGameOnChannel;
import models.ChannelList;
import models.Game;
import models.GameServer;
import play.mvc.With;
import play.test.Fixtures;
import events.MessageEvent;

@With(Secure.class)
public class Admin extends AbstractController {

	@Check("isAdmin")
	public static void index() {
		render();
	}

	@Check("isAdmin")
	public static void observe(int number, String gameId) {
		GathererService.instance.observe(gameId);
		flash.success("observe %s", gameId);
		WeiqiTV.watch(number);
	}

	@Check("isAdmin")
	public static void next(int number) throws Exception {
		new ShowNextGameOnChannel(number).now();
		flash.success("switch to next game on channel %s", number);
		WeiqiTV.watch(number);
	}

	@Check("isAdmin")
	public static void reset() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("initial-data.yml");
		flash.success("database reloaded");
		index();
	}

	@Check("isAdmin")
	public static void start() {
		GameServer igs = GameServer.all().<GameServer> first();
		GathererService.instance.startGatherer(igs.host, igs.port);
		flash.success("gatherer started with %s:%s", igs.host, igs.port);
		index();
	}

	@Check("isAdmin")
	public static void stop() {
		GathererService.instance.stopGatherer();
		flash.success("gatherer stopped");
		index();
	}

	@Check("isAdmin")
	public static void games() {
		List<Game> games = Game.findAll();
		render(games);
	}

	@Check("isAdmin")
	public static void broadcast(String message) {
		MessageEvent me = new MessageEvent();
		me.message = message;
		ChannelList.instance.publishEvent(me);
		flash.success("broadcast %s", message);
		index();
	}

}

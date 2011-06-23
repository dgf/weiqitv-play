package controllers;

import gatherer.GathererService;
import models.GameServer;
import play.Logger;
import play.mvc.With;
import play.test.Fixtures;

@With(Secure.class)
public class Admin extends AbstractController {

	public static void observe(int channelId, int gameId) {
		Logger.info("FOO %s BAR", gameId);
		GathererService.instance.observe("" + gameId);
		flash.success("observe %s", gameId);
		WeiqiTV.watch(channelId);
	}

	public static void restart() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("initial-data.yml");
		flash.success("database reloaded");
		WeiqiTV.index();
	}

	public static void start() {
		GameServer igs = GameServer.all().<GameServer> first();
		GathererService.instance.startGatherer(igs.host, igs.port);
		flash.success("gatherer started with %s:%s", igs.host, igs.port);
		WeiqiTV.index();
	}

	public static void stop() {
		GathererService.instance.stopGatherer();
		flash.success("gatherer stopped");
		WeiqiTV.index();
	}
}

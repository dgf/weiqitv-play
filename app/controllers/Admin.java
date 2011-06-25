package controllers;

import java.util.List;

import jobs.ShowNextGame;
import models.Game;
import models.GameServer;
import play.Logger;
import play.mvc.With;
import play.test.Fixtures;

@With(Secure.class)
public class Admin extends AbstractController {

	public static void index() {
		render();
	}

	public static void observe(int number, String gameId) {
		Logger.info("FOO %s BAR", gameId);
		GathererService.instance.observe(gameId);
		flash.success("observe %s", gameId);
		WeiqiTV.watch(number);
	}

	public static void next(int number) throws Exception {
		new ShowNextGame(number).now();
		flash.success("switch to next game on channel %s", number);
		WeiqiTV.watch(number);
	}

	public static void reset() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("initial-data.yml");
		flash.success("database reloaded");
		index();
	}

	public static void start() {
		GameServer igs = GameServer.all().<GameServer> first();
		GathererService.instance.startGatherer(igs.host, igs.port);
		flash.success("gatherer started with %s:%s", igs.host, igs.port);
		index();
	}

	public static void stop() {
		GathererService.instance.stopGatherer();
		flash.success("gatherer stopped");
		index();
	}

	public static void games() {
		List<Game> games = Game.findAll();
		render(games);
	}

}

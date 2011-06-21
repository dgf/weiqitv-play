package controllers;

import java.util.List;

import json.MyExclusionStrategy;
import json.MyRenderJson;
import models.Channel;
import models.Turn;
import play.Logger;
import play.Play;
import play.mvc.Before;
import play.mvc.Controller;
import play.test.Fixtures;

public class Application extends Controller {

	@Before
	static void addDefaults() {
		renderArgs.put("title", Play.configuration.getProperty("weiqiTV.title"));
		renderArgs.put("baseline", Play.configuration.getProperty("weiqiTV.baseline"));
	}

	public static void index() {
		List<Channel> channels = Channel.find("order by number").fetch();
		render(channels);
	}

	public static void watch(int number) {
		Logger.info("watch %s", number);
		Channel channel = Channel.find("byNumber", number).first();
		render(channel);
	}

	public static void restart() {
		Logger.info("renew data");
		Fixtures.deleteDatabase();
		Fixtures.loadModels("initial-data.yml");
		index();
	}

	public static void listTurns(int id) {
		renderJSONHide(Turn.find("game_id", id).fetch());
	}

	protected static void renderJSONExpose(Object o) {
		throw new MyRenderJson(o);
	}

	protected static void renderJSONHide(Object o) {
		throw new MyRenderJson(o, new MyExclusionStrategy());
	}
}
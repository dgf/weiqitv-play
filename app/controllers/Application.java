package controllers;

import java.util.List;

import models.Channel;
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
		List<Channel> channels = Channel.find("order by postedAt desc").fetch();
		render(channels);
	}

	public static void watch(int number) {
		Channel channel = Channel.find("byNumber", number).first();
		int size = channel.game.size.getLength();
		render(channel, size);
	}

	public static void restart() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("initial-data.yml");
		index();
	}

}
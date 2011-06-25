package controllers;

import java.util.List;

import models.Channel;
import models.Move;
import models.SearchResult;
import play.Logger;

public class WeiqiTV extends AbstractController {

	public static void index() {
		List<Channel> channels = Channel.allByNumber();
		render(channels);
	}

	public static void watch(int number) {
		Channel channel = Channel.findByNumber(number);
		List<Channel> channels = Channel.allByNumber();
		flash.success("watch %s", channel.title);
		render(channel, channels);
	}

	public static void listTurns(long id) {
		Logger.debug("list %s turns", id);
		renderJSONHide(Move.find("game_id = ? order by number", id).fetch());
	}

	public static void search(String query) {
		System.out.println("search " + query);
		SearchResult sr = new SearchResult();
		// sr.channels = Search.search("title:" + query, Channel.class).fetch();
		// sr.users = Search.search("name:" + query, User.class).fetch();
		// sr.players = Search.search("name:" + query, Player.class).fetch();
		renderJSONHide(sr);
	}

}
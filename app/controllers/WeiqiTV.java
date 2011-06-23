package controllers;

import java.util.List;

import models.Channel;
import models.Game;
import models.Move;
import models.Player;
import models.SearchResult;
import models.User;
import play.modules.search.Search;

public class WeiqiTV extends AbstractController {

	public static void index() {
		List<Channel> channels = Channel.find("order by number").fetch();
		render(channels);
	}

	public static void watch(int number) {
		Channel channel = Channel.find("byNumber", number).first();
		flash.success("watch %s", number);
		render(channel);
	}

	public static void listTurns(int id) {
		renderJSONHide(Move.find("game_id", id).fetch());
	}

	public static void search(String query) {
		System.out.println("search " + query);
		SearchResult sr = new SearchResult();
		sr.channels = Search.search("title:" + query, Channel.class).fetch();
		sr.users = Search.search("name:" + query, User.class).fetch();
		sr.players = Search.search("name:" + query, Player.class).fetch();
		renderJSONHide(sr);
	}

	public static void games() {
		List<Game> games = Game.findAll();
		render(games);
	}

}
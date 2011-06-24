package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Channel;
import models.Move;
import models.SearchResult;
import play.Logger;

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

	public static void listPrisoners(int id) {
		List<Move> moves = Move.find("game_id", id).fetch();
		List<String[]> bp = new ArrayList<String[]>();
		List<String[]> wp = new ArrayList<String[]>();
		for (Move move : moves) {
			if (move.prisoners != null && move.prisoners.length != 0) {
				switch (move.player) {
				case BLACK:
					bp.add(move.prisoners);
					break;
				case WHITE:
					wp.add(move.prisoners);
					break;
				default:
					break;
				}
			}
		}

		renderJSON(wp);
	}

}
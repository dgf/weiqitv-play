package controllers;

import models.Channel;
import models.Move;
import play.Logger;

import java.util.List;

public class WeiqiTV extends AbstractController {

    public static void index() {
        List<Channel> channels = Channel.allByNumber();
        render(channels);
    }

    public static void watch(Integer number) {
        Logger.info("watch %s", number);
        Channel channel = Channel.findByNumber(number);
        List<Channel> channels = Channel.allByNumber();
        flash.success("watch %s", channel.title);
        render(channel, channels);
    }

    public static void turns(Long game) {
        Logger.info("list %s turns", game);
        List<Move> turns = Move.findByGameId(game);
        render(turns);
    }

    public static void channels() {
        Logger.info("list all channels");
        List<Channel> channels = Channel.allByNumber();
        request.format = "json";
        render(channels);
    }

}

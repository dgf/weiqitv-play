package jobs;

import controllers.GathererService;
import events.NextGameEvent;
import models.Channel;
import models.ChannelList;
import models.Game;
import play.Logger;
import play.jobs.Job;

public class ShowNextGameOnChannel extends Job {

    private final int number;

    public ShowNextGameOnChannel(int number) {
        this.number = number;
    }

    @Override
    public void doJob() throws Exception {
        Channel channel = Channel.findByNumber(number);
        channel.game = channel.next;
        channel.next = null;
        channel.save();

        NextGameEvent nge = new NextGameEvent();
        Game game = channel.game;

        nge.game = game.toString();
        nge.turn = game.turn;
        nge.white = game.white.toString();
        nge.black = game.black.toString();

        nge.byo = game.byo;
        nge.handicap = game.handicap.stones;
        nge.wTime = 0;
        nge.bTime = 0;

        Logger.info("show next game %s on channel %s", game, channel);
        ChannelList.instance.publishEvent(game, nge);
        GathererService.instance.observe(game.onlineId);
    }
}

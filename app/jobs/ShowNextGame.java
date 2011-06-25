package jobs;

import models.Channel;
import models.ChannelList;
import models.Game;
import play.Logger;
import play.jobs.Job;
import controllers.GathererService;
import events.ShowNextGameEvent;

public class ShowNextGame extends Job {

	private final long channelId;

	public ShowNextGame(long channelId) {
		this.channelId = channelId;
	}

	@Override
	public void doJob() throws Exception {
		Channel channel = Channel.findById(channelId);
		channel.game = channel.next;
		channel.save();

		ShowNextGameEvent se = new ShowNextGameEvent();
		Game game = channel.game;

		se.game = game.toString();
		se.white = game.white.toString();
		se.black = game.black.toString();

		se.byo = game.byo;
		se.handicap = game.handicap.stones;
		se.wTime = 0;
		se.bTime = 0;

		Logger.info("show next game %s on channel %s", game, channel);
		ChannelList.instance.publishEvent(game, se);
		GathererService.instance.observe(game.onlineId);
	}
}

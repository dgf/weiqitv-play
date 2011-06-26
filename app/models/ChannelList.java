package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jobs.ShowNextGameOnChannel;
import play.Logger;
import play.libs.F.EventStream;
import events.ChannelEvent;
import events.ResultEvent;

public class ChannelList {

	public static ChannelList instance = new ChannelList();

	public final Map<Integer, EventStream<ChannelEvent>> eventStreams = new HashMap<Integer, EventStream<ChannelEvent>>();

	private ChannelList() {
	}

	public EventStream<ChannelEvent> getStream(int number) {
		return eventStreams.get(number);
	}

	public void addStream(Channel channel) {
		Logger.info("add %s channel stream", channel.title);
		eventStreams.put(channel.number, new EventStream<ChannelEvent>());
	}

	public static void publishEvent(Game game, ChannelEvent event) {
		List<Channel> channels = Channel.find("game", game).fetch();
		for (Channel channel : channels) {
			ChannelList.instance.getStream(channel.number).publish(event);
			if (event instanceof ResultEvent) {
				new ShowNextGameOnChannel(channel.number).in("10s");
			}
		}
	}

	public void publishEvent(ChannelEvent event) {
		List<Channel> channels = Channel.all().fetch();
		for (Channel channel : channels) {
			ChannelList.instance.getStream(channel.number).publish(event);
		}
	}
}

package models;

import events.ChannelEvent;
import play.Logger;
import play.libs.F.EventStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // update actual games
        List<Channel> channels = Channel.findByGame(game);
        for (Channel channel : channels) {
            ChannelList.instance.getStream(channel.number).publish(event);
        }
    }

    public void publishEvent(ChannelEvent event) {
        List<Channel> channels = Channel.all().fetch();
        for (Channel channel : channels) {
            ChannelList.instance.getStream(channel.number).publish(event);
        }
    }
}

package models;

import play.libs.F.EventStream;
import events.ChannelEvent;

public class ChannelList {

	public static ChannelList instance = new ChannelList();

	public final EventStream<ChannelEvent> event = new EventStream<ChannelEvent>();

	private ChannelList() {
	}
}

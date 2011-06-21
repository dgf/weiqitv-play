package models;

import play.libs.F.EventStream;

public class ChannelList {
	public static ChannelList instance = new ChannelList();

	public final EventStream event = new EventStream();

	private ChannelList() {
	}
}

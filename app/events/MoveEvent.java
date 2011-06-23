package events;

import models.BlackOrWhite;

public class MoveEvent implements ChannelEvent {

	public final String type = "move";

	public int number;

	public String coordinate;

	public BlackOrWhite player;

}

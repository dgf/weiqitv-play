package events;

import java.util.List;

import models.BlackOrWhite;

public class MoveEvent implements ChannelEvent {

	public final String type = "move";

	public int number;

	public String coordinate;

	public BlackOrWhite player;

	public List<String> prisoners;

	public int time;

	public int byo;

}

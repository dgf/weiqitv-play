package events;

import models.BlackOrWhite;
import models.Move;

public class MoveEvent implements ChannelEvent {

	public final String type = "move";

	public int number;

	public String coordinate;

	public BlackOrWhite player;

	public String[] prisoners;

	public int time;

	public Move move;

}

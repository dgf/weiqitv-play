package events;

import java.util.List;

import models.BlackOrWhite;
import models.Move;
import models.Prisoner;

public class MoveEvent implements ChannelEvent {

	public final String type = "move";

	public int number;

	public String coordinate;

	public BlackOrWhite player;

	public List<Prisoner> prisoners;

	public int time;

	public Move move;

}

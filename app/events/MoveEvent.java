package events;

import models.BlackOrWhite;

import java.util.List;

public class MoveEvent implements ChannelEvent {

    public final String type = "move";

    public int number;

    public String coordinate;

    public BlackOrWhite player;

    public List<String> prisoners;

    public int time;

    public int byo;

}

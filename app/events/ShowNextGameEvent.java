package events;

public class ShowNextGameEvent implements ChannelEvent {

	public final String type = "next";

	public String game; // name

	public int handicap;

	public int byo;

	public float komi;

	public String white;

	public int wTime;

	public String black;

	public int bTime;

}

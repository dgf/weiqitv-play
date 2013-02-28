package events;

public class MessageEvent implements ChannelEvent {

    public final String type = "message";

    public String message;
}

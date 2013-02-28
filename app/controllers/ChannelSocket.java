package controllers;

import events.ChannelEvent;
import models.ChannelList;
import play.Logger;
import play.libs.F.EventStream;
import play.mvc.WebSocketController;

public class ChannelSocket extends WebSocketController {

    public static void stream(int number) {
        Logger.info("try streaming... %s", number);
        EventStream<ChannelEvent> stream = ChannelList.instance.getStream(number);
        while (inbound.isOpen()) {
            try {
                Logger.info("Waiting for next change... %s", number);
                ChannelEvent event = await(stream.nextEvent());
                if (event != null) {
                    Logger.info("publish event %s to channel %s subscribers", event.getClass(),
                            number);
                    outbound.sendJson(event);
                }
            } catch (Exception e) {
                Logger.error(e, "outbound closed");
            }
        }
    }
}

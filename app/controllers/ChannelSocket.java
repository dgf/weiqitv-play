package controllers;

import models.ChannelList;
import play.Logger;
import play.modules.log4play.ExceptionUtil;
import play.mvc.WebSocketController;
import events.ChannelEvent;

public class ChannelSocket extends WebSocketController {

	public static void stream() {
		Logger.info("try streaming...");
		while (inbound.isOpen()) {
			try {
				Logger.info("Waiting for next change...");
				ChannelEvent event = await(ChannelList.instance.event.nextEvent());
				if (event != null) {
					Logger.info("publish event %s to channel subscribers", event);
					outbound.sendJson(event);
				}
			} catch (Throwable t) {
				Logger.error(ExceptionUtil.getStackTrace(t));
			}
		}
	}
}

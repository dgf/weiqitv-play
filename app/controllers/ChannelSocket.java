package controllers;

import models.ChannelList;
import play.Logger;
import play.modules.log4play.ExceptionUtil;
import play.mvc.WebSocketController;

public class ChannelSocket extends WebSocketController {

	public static void stream() {
		Logger.info("try streaming...");
		while (inbound.isOpen()) {
			try {
				Logger.info("Waiting for next change...");
				String change = await(ChannelList.instance.event.nextEvent());
				if (change != null) {
					Logger.info("Publishing change %s to outbound subscribers", change);
					outbound.send(change);
				}
			} catch (Throwable t) {
				Logger.error(ExceptionUtil.getStackTrace(t));
			}
		}
	}
}
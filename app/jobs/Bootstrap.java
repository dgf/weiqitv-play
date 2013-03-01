package jobs;

import controllers.GathererService;
import models.Channel;
import models.ChannelList;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import java.util.List;

/**
 * create a websocket for each channel at startup
 */
@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        Logger.info("bootstrap, initializing channels");
        List<Channel> channels = Channel.findAll();
        for (Channel channel : channels) {
            channel.game = null;
            channel.next = null;
            channel.save();
            ChannelList.instance.addStream(channel);
        }
    }

}

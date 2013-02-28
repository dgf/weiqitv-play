package jobs;

import models.Channel;
import models.ChannelList;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import java.util.List;

/**
 * create a websocket for each channel at startup
 */
@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        List<Channel> channels = Channel.findAll();
        for (Channel channel : channels) {
            ChannelList.instance.addStream(channel);
        }
    }

}

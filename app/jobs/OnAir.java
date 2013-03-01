package jobs;

import controllers.GathererService;
import models.Channel;
import models.ChannelList;
import play.Logger;
import play.jobs.Every;
import play.jobs.Job;

import java.util.Random;


/**
 * check the state the gatherer and of each channel
 */
@Every("10s")
public class OnAir extends Job {

    @Override
    public void doJob() throws Exception {
        Logger.info("check gatherer and channel state");
        if(GathererService.instance.isActive() == false) {
            GathererService.instance.start(); // restart it
        }
        for (Integer number : ChannelList.instance.eventStreams.keySet()) {
            Channel channel = Channel.findByNumber(number);
            if (channel.game == null && channel.next != null) {
                int seconds = new Random().nextInt(7) + 1;
                Logger.info("start watching of %s on channel %s in %s seconds",
                        channel.next.onlineId, channel.title, seconds);
                new NextGame(number).in(seconds);
            }
        }
    }
}

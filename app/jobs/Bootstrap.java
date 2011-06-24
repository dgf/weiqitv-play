package jobs;

import java.util.List;

import models.Channel;
import models.ChannelList;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() {
		// create a socket for each channel
		List<Channel> channels = Channel.findAll();
		for (Channel channel : channels) {
			ChannelList.instance.addStream(channel);
		}
	}

}

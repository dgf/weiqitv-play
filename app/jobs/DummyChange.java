package jobs;

import models.ChannelList;
import play.Logger;
import play.jobs.Every;
import play.jobs.Job;
import play.libs.F.EventStream;

@Every("5s")
public class DummyChange extends Job {

	public void doJob() {
		double r = Math.random();
		Logger.info("do random change %s", r);
		EventStream stream = ChannelList.instance.event;
		stream.publish("change " + r);
	}
}

package jobs;

import models.BlackOrWhite;
import models.ChannelList;
import play.Logger;
import play.jobs.Every;
import play.jobs.Job;
import play.libs.F.EventStream;
import events.MoveEvent;

@Every("20s")
public class DummyChange extends Job {

	public void doJob() {
		double r = Math.round(Math.random() * 100);
		Logger.info("do random change %s", r);
		EventStream stream = ChannelList.instance.event;
		// stream.publish("change " + r);
		MoveEvent m = new MoveEvent();
		m.player = BlackOrWhite.BLACK;
		m.coordinate = "K12";
		stream.publish(m);
	}
}

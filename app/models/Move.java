package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PostPersist;

import play.Logger;
import play.data.validation.Required;
import events.MoveEvent;

@Entity
@DiscriminatorValue("Move")
public class Move extends Turn {

	@Required
	public String coordinate;

	public String[] prisoners;

	@Override
	public String toString() {
		return player + coordinate;
	}

	@PostPersist
	public void updateChannel() {
		Logger.debug("create game move %s", this);
		MoveEvent me = new MoveEvent();
		me.coordinate = coordinate;
		me.player = player;
		me.number = number;
		// me.byoYomi = byoYomi;
		ChannelList.instance.event.publish(me);
	}
}

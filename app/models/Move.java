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
	public void updateChannelSockets() {
		MoveEvent me = new MoveEvent();
		me.coordinate = coordinate;
		me.player = player;
		me.number = number;
		me.prisoners = prisoners;
		me.time = secondsLeft;

		Logger.debug("create game move %s", this);
		ChannelList.publishEvent(game, me);
	}

	public static Move findByGameAndNumber(Game game, int number) {
		return Move.find("byGameAndNumber", game, number).<Move> first();
	}
}

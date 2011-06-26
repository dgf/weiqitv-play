package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;

import play.Logger;
import play.data.validation.Required;
import events.MoveEvent;

@Entity
@DiscriminatorValue("Move")
public class Move extends Turn {

	@Required
	@Column(length = 4)
	public String coordinate;

	@OneToMany(mappedBy = "move", cascade = CascadeType.ALL)
	public List<Prisoner> prisoners;

	@Override
	public String toString() {
		return player + " " + coordinate + " " + seconds + " " + byo;
	}

	@PostPersist
	public void updateChannelSockets() {
		MoveEvent me = new MoveEvent();
		me.coordinate = coordinate;
		me.player = player;
		me.number = number;
		me.prisoners = Prisoner.toList(prisoners);
		me.time = seconds;
		me.byo = byo;

		Logger.debug("create game move %s", this);
		ChannelList.publishEvent(game, me);
	}

	public static List<Move> findByGameId(long id) {
		return Move.find("game_id = ? order by number", id).fetch();
	}

	public static Move findByGameAndNumber(Game game, int number) {
		return Move.find("byGameAndNumber", game, number).<Move> first();
	}
}

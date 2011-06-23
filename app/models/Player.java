package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.search.Field;
import play.modules.search.Indexed;

@Indexed
@Entity
public class Player extends Model {

	@Field
	@Required
	public String name;

	@ManyToOne
	public User user;

	public static Player getPlayer(String name) {
		Player player = find("name", name).first();
		if (player == null) {
			player = new Player();
			player.name = name;
		}
		return player;
	}

	@Override
	public String toString() {
		return name;

	}
}

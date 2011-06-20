package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class GameServer extends Model {

	@Required
	public String server;

	@Override
	public String toString() {
		return server;
	}
}

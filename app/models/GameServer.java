package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class GameServer extends Model {

	@Required
	public String host;

	@Required
	public int port;

	@Override
	public String toString() {
		return host + ":" + port;
	}

	public static GameServer findByHost(String server) {
		return GameServer.find("host", server).<GameServer> first();
	}
}

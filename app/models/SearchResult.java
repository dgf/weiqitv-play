package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class SearchResult extends Model {

	@Required
	@OneToMany
	public List<Channel> channels;

	@Required
	@OneToMany
	public List<User> users;

	@Required
	@OneToMany
	public List<Player> players;

}

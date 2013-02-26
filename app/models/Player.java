package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

//@Indexed
@Entity
public class Player extends Model {

	// @Field
	@Required
	@Column(unique = true, nullable = false)
	public String name;

	@ManyToOne
	public User user;

	@Override
	public String toString() {
		return name;
	}

	static Player findByName(String name) {
		return Player.find("name", name).first();
	}
}

package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Player extends Model {

	@Required
	public String name;

	@ManyToOne
	public User user;

	@Override
	public String toString() {
		return name;
	}

}

package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;
import annotations.Hide;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Turn extends Model {

	@Hide
	@Required
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Game game;

	@Required
	public BlackOrWhite player;

}

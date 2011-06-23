package models;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;
import annotations.JSONhide;

@Entity
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Turn")
// @MappedSuperclass
public abstract class Turn extends Model {

	@JSONhide
	@Required
	@ManyToOne
	public Game game;

	@Required
	public int number;

	@Required
	public BlackOrWhite player;

	@Required
	public int secondsLeft;

	public int byoYomi;

}

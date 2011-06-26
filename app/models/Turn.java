package models;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import play.data.validation.Max;
import play.data.validation.Min;
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
	@ManyToOne(fetch = FetchType.LAZY)
	public Game game;

	@Required
	@Min(0)
	@Max(361)
	public int number;

	@Required
	public BlackOrWhite player;

	@Required
	@Min(0)
	@Max(3600)
	public int seconds;

	public int byo;

}

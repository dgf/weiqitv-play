package models;

import javax.persistence.Entity;

import play.data.validation.Required;

@Entity
public class Move extends Turn {

	@Required
	public String coordinate;

	@Required
	public int secondsLeft;

	public int byoYomi;

	public String[] prisoners;

	@Override
	public String toString() {
		return player + coordinate;
	}
}

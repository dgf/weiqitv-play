package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Pass")
public class Pass extends Turn {

	@Override
	public String toString() {
		return player + "P";
	}
}

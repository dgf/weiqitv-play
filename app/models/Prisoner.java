package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;
import annotations.JSONhide;

@Entity
public class Prisoner extends Model {

	@JSONhide
	@Required
	@ManyToOne(fetch = FetchType.LAZY)
	public Move move;

	@Required
	@Column(length = 3)
	public String coordinate;

	public Prisoner(Move move, String coordinate) {
		this.move = move;
		this.coordinate = coordinate;
	}

	public static List<Prisoner> toList(Move move, List<String> prisoners) {
		List<Prisoner> list = new ArrayList<Prisoner>(prisoners.size());
		for (String coordinate : prisoners) {
			list.add(new Prisoner(move, coordinate));
		}
		return list;
	}
}

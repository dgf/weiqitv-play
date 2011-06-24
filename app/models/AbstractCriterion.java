package models;

import javax.persistence.MappedSuperclass;

import play.db.jpa.Model;

@MappedSuperclass
public abstract class AbstractCriterion<T> extends Model implements Criterion<T> {

	public boolean ge(T value) {
		return compareTo(value) >= 0 ? true : false;
	}

	public boolean le(T value) {
		return compareTo(value) <= 0 ? true : false;
	}

}

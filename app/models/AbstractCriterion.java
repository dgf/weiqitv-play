package models;

import play.db.jpa.Model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractCriterion<T> extends Model implements Criterion<T> {

    public boolean ge(T value) {
        return compareTo(value) >= 0 ? true : false;
    }

    public boolean le(T value) {
        return compareTo(value) <= 0 ? true : false;
    }

}

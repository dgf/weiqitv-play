package models;

public interface Criterion<T> extends Comparable<T> {

	/**
	 * @return greater than or equal
	 */
	boolean ge(T value);

	/**
	 * @return less than or equal
	 */
	boolean le(T value);
}

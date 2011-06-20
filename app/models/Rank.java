package models;

import static models.RankType.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * @link http://en.wikipedia.org/wiki/Go_ranks_and_ratings
 */
@Entity
public class Rank extends Model {

	public static final int MAX_PRO_RANK = 9;

	// online weiqi ranking includes 8d and 9d
	public static final int MAX_DAN_RANK = 9;

	public static final int MAX_KYU_RANK = 30;

	public static final Pattern RANK_PATTERN = Pattern.compile("([0-9]{1,2})([dkp])");

	@Required
	public int nr;

	@Required
	@Enumerated(EnumType.STRING)
	public RankType type;

	public Rank(int nr, RankType type) {
		setRank(nr, type);
	}

	public Rank(String rank) {
		setRank(rank);
	}

	// @Override
	public int compareTo(Rank o) {
		int compare = type.compareTo(o.type);
		if (compare == 0) {
			switch (type) {
			case KYU:
				return o.nr - nr;
			default:
				return nr - o.nr;
			}
		} else if (Math.abs(compare) == 2) { // PRO vs. KYU
			if (type.equals(PRO)) {
				return 8 + o.nr + nr;
			} else {
				return -8 - o.nr - nr;
			}
		} else {
			switch (type) {
			case PRO: // PRO vs. DAN
				return 9 - o.nr + nr;
			case DAN:
				if (o.type.equals(PRO)) { // DAN vs. PRO
					return -9 - o.nr + nr;
				} else { // DAN vs. KYU
					return -1 + o.nr + nr;
				}
			default: // KYU vs. DAN
				return 1 - o.nr - nr;
			}
		}
	}

	// @Override
	public String getName() {
		return "rank";
	}

	// @Override
	public String getValue() {
		return type == NR ? NR.name() : "" + nr + type;
	}

	private void setRank(int nr, RankType type) {
		if ((type == NR && nr == 0)
				|| (nr > 0 && ((type == KYU && nr <= MAX_KYU_RANK)
						|| (type == DAN && nr <= MAX_DAN_RANK) || (type == PRO && nr <= MAX_PRO_RANK)))) {
			this.nr = nr;
			this.type = type;
		} else {
			throw new IllegalArgumentException("" + nr + type + " is an invalid rank");
		}
	}

	public void setRank(String rank) {
		try {
			if (rank.equals(NR.name())) {
				setRank(0, NR);
				return;
			}
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(e);
		}

		Matcher matcher = RANK_PATTERN.matcher(rank);

		if (matcher.matches() == false) {
			throw new IllegalArgumentException( //
					rank + " matching failed: " + RANK_PATTERN);
		}

		setRank(matcher.group(1), matcher.group(2));
	}

	private void setRank(String nr, String type) {
		setRank(Integer.parseInt(nr), RankType.get(type));
	}

	// @Override
	public void setValue(String value) {
		setRank(value);
	}

	@Override
	public String toString() {
		return "" + nr + type;
	}
}
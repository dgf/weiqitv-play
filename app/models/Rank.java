package models;

import static models.RankType.*;

import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.Logger;
import play.data.validation.Required;

/**
 * @link http://en.wikipedia.org/wiki/Go_ranks_and_ratings
 */
@Entity
public class Rank extends AbstractCriterion<Rank> {

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

	@Override
	public String toString() {
		return type == NR ? NR.name() : "" + nr + type;
	}

	@Override
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

	public static Rank findByNrAndType(int nr, RankType type) {
		Logger.info("find by nr:%s type:%s class:%s", nr, type, type.getClass().getName());
		return Rank.find("byNrAndType", nr, type).first();
	}
}

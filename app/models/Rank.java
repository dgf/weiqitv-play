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

	public static Rank getRank(int nr, RankType type) {
		Rank rank = find("byNrAndType", nr, type).first();
		if (rank == null) {
			rank = new Rank();
			rank.nr = nr;
			rank.type = type;
			rank.save();
		}
		return rank;
	}

	public static Rank getRank(String nr, String type) {
		return getRank(Integer.parseInt(nr), RankType.get(type));
	}

	public static Rank getRank(String rank) {
		try {
			if (rank.equals(NR.name())) {
				return getRank(0, NR);
			}
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(e);
		}

		Matcher matcher = RANK_PATTERN.matcher(rank);

		if (matcher.matches() == false) {
			throw new IllegalArgumentException( //
					rank + " matching failed: " + RANK_PATTERN);
		}

		return getRank(matcher.group(1), matcher.group(2));
	}

	@Override
	public String toString() {
		return type == NR ? NR.name() : "" + nr + type;
	}
}
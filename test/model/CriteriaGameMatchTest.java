package model;

import static models.WeiqiBeanFactory.*;
import models.Criteria;
import models.Game;
import models.Size;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class CriteriaGameMatchTest extends UnitTest {

	private Criteria cut;
	private Game game;

	@Before
	public void setup() {
		cut = criteria("criteria", "30k", "9p", 0, 9, Size.s19x19);
		game = game("white", "1d", "black", "3k", 19, 3, 0.5f);
	}

	@Test
	public void matchByDefault() {
		assertTrue(cut.matches(game));
	}

	@Test
	public void blackIsntGoodEnough() {
		cut.minRank = rank("2k");
		assertFalse(cut.matches(game));
	}

	@Test
	public void whiteIsntGoodEnough() {
		cut.minRank = rank("2d");
		assertFalse(cut.matches(game));
	}

	@Test
	public void toMuchHandicap() {
		cut.maxHandicap = handicap(2);
		assertFalse(cut.matches(game));
	}

	@Test
	public void notEnoughHandicap() {
		cut.minHandicap = handicap(4);
		assertFalse(cut.matches(game));
	}

}

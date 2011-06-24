package model;

import static models.WeiqiFactory.*;
import models.Criteria;
import models.Game;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class CriteriaMatchTest extends UnitTest {

	private Criteria cut;
	private Game game;

	@Before
	public void setup() {
		cut = criteria("TEST");
		game = game("white", "3k", "black", "5k", 19, 2, 0.5);
	}

	@Test
	public void matchByDefault() {
		// by default, all criteria include the range from minimum to maximum
		assertTrue(cut.matches(game));
	}

	@Test
	public void blackIsntGoodEnough() {
		cut.minRank = rank("4k");
		assertFalse(cut.matches(game));
	}

	@Test
	public void whiteIsntGoodEnough() {
		game.white.rank = rank("5k");
		cut.minRank = rank("4k");
		assertFalse(cut.matches(game));
	}

	@Test
	public void toMuchHandicap() {
		cut.maxHandicap = handicap(0);
		assertFalse(cut.matches(game));
	}

	@Test
	public void notEnoughHandicap() {
		cut.minHandicap = handicap(4);
		assertFalse(cut.matches(game));
	}

}

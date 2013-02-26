package model;

import static models.BlackOrWhite.*;
import static models.WeiqiJpaFactory.*;
import static org.junit.matchers.JUnitMatchers.*;
import models.Game;
import models.Move;
import models.Prisoner;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class MoveCrudTest extends UnitTest {

	private Game game;

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		game = game("white", "5k", "black", "7k", 19, 2, 0.5f);
	}

	@Test
	public void crud() throws Exception {
		// create
		Move m = move(game, 0, BLACK, "Q16", 300, -1);
		assertEquals(1, Move.count());

		// read
		m = Move.findById(m.id);
		assertEquals(BLACK, m.player);
		assertEquals("Q16", m.coordinate);
		assertTrue(m.prisoners.isEmpty());
		assertEquals(300, m.seconds);
		assertEquals(-1, m.byo);

		// update
		m.prisoners = prisoner(m, "K10", "D4");
		m.save();
		assertEquals(2, Prisoner.count());

		// read again
		m = Move.findByGameAndNumber(game, 0);
		assertThat(Prisoner.toStringList(m.prisoners), hasItems("K10", "D4"));

		// delete
		m.delete();
		assertEquals(0, Move.count());
		assertEquals(0, Prisoner.count());
	}
}

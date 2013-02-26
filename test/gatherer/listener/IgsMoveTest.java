package gatherer.listener;

import static gatherer.IgsConstants.*;
import gatherer.IgsConstants;
import models.BlackOrWhite;

import org.junit.Test;

import play.test.UnitTest;

// 15 Game 43 I: ryoken (14 359 4) vs rokujidoo (15 598 24)
// 9 Handicap and komi are disable.
// 15   0(B): Handicap 2
// 15 269(W): J9 K9
// 2
// 1 8
public class IgsMoveTest extends UnitTest {

	private static final String[] states = new String[] {
			"15 Game 43 I: ryoken (21 578 20) vs rokujidoo (19 546 14)",
			"15 Game 23 I: Fuku5858 (0 23 -1) vs odamori25 (0 39 -1)",
			"15 Game 256 I: dokirisan (0 397 14) vs GONTA3 (0 82 0)" };

	private static final String[] moves = new String[] { "15   0(B): Q16", //
			"15   1(W): D4", //
			"15 269(W): J9 K9 K10" };

	@Test
	public void handicap() throws Exception {
		WeiqiStorageMock storageMock = new WeiqiStorageMock() {
			@Override
			public void addMove(String server, String id, int number, BlackOrWhite player,
					String coordinate, int seconds, int byo, String... prisoners) {
				assertEquals("server:port", server);
				assertEquals("43", id);
				assertEquals(0, number);
				assertEquals(BlackOrWhite.BLACK, player);
				assertEquals("HC2", coordinate);
				assertEquals(546, seconds);
				assertEquals(14, byo);
				assertEquals(0, prisoners.length);
			}
		};
		IgsMoves cut = new IgsMoves("server:port", storageMock);

		assertTrue("game 43", cut.notify(states[0]));
		assertTrue("handicap 2", cut.notify("15   0(B): Handicap 2"));
		assertTrue(cut.notify(OBSERVED));
		assertFalse(cut.notify(MOVE_LIST_OK));
		assertFalse(cut.isRetrieving());
	}

	@Test
	public void pass() throws Exception {
		WeiqiStorageMock storageMock = new WeiqiStorageMock() {
			@Override
			public void addMove(String server, String id, int number, BlackOrWhite player,
					String coordinate, int seconds, int byo, String... prisoners) {
				assertEquals(BlackOrWhite.WHITE, player);
				assertEquals("Pass", coordinate);
			}
		};
		IgsMoves cut = new IgsMoves("server:port", storageMock);

		assertTrue("game 43", cut.notify(states[0]));
		assertTrue("pass", cut.notify("15 301(W): Pass"));
		assertTrue(cut.notify(OBSERVED));
		assertFalse(cut.notify(MOVE_LIST_OK));
		assertFalse(cut.isRetrieving());
	}

	@Test
	public void parseMoveList() throws Exception {

		WeiqiStorageMock storageMock = new WeiqiStorageMock();
		IgsMoves cut = new IgsMoves("server:port", storageMock);

		for (String move : moves) {
			storageMock.moveCalled = false;
			assertTrue(move, cut.notify(states[0]));
			assertTrue(move, cut.notify(move));
			assertTrue(cut.notify(IgsConstants.OBSERVED));
			assertFalse(cut.notify(IgsConstants.MOVE_LIST_OK));
			assertTrue(storageMock.moveCalled);
			assertFalse(cut.isRetrieving());
		}
	}

	@Test
	public void parseStateList() throws Exception {

		WeiqiStorageMock storageMock = new WeiqiStorageMock();
		IgsMoves cut = new IgsMoves("server:port", storageMock);

		for (String line : states) {
			storageMock.moveCalled = false;
			assertTrue(line, cut.notify(line));
			assertTrue(line, cut.notify(moves[0]));
			assertTrue(cut.notify(IgsConstants.OBSERVED));
			assertFalse(cut.notify(IgsConstants.MOVE_LIST_OK));
			assertTrue(storageMock.moveCalled);
			assertFalse(cut.isRetrieving());
		}
	}

}

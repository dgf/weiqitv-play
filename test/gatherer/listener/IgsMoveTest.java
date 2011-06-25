package gatherer.listener;

import gatherer.IgsConstants;
import gatherer.WeiqiStorage;

import java.util.List;

import models.BlackOrWhite;

import org.junit.Test;

import play.test.UnitTest;

// 15 Game 43 I: ryoken (14 359 4) vs rokujidoo (15 598 24)
// 9 Handicap and komi are disable.
// 15 269(W): J9 K9
// 2
// 1 8
public class IgsMoveTest extends UnitTest {

	private static final String[] states = new String[] { //
	"15 Game 43 I: ryoken (21 578 20) vs rokujidoo (19 546 14)", //
			"15 Game 23 I: Fuku5858 (0 23 -1) vs odamori25 (0 39 -1)", //
			"15 Game 256 I: dokirisan (0 397 14) vs GONTA3 (0 82 0)" };

	private static final String[] moves = new String[] { "15   0(B): Q16", //
			"15   1(W): D4", //
			"15 269(W): J9 K9 K10", //
			"15 301(W): Pass" };

	@Test
	public void parseMoveList() throws Exception {

		WeiqiMoveStorageMock storageMock = new WeiqiMoveStorageMock();
		IgsMove cut = new IgsMove("server:port", storageMock);

		for (String line : states) {
			storageMock.checked = false;
			assertTrue(line, cut.notify(line));
			assertTrue(line, cut.notify(moves[0]));
			assertFalse(cut.notify(IgsConstants.MOVE_LIST_OK));
			assertTrue(storageMock.checked);
		}

	}

	private class WeiqiMoveStorageMock implements WeiqiStorage {
		boolean checked = false;

		@Override
		public String addMove(String server, String id, int number, BlackOrWhite player,
				String coordinate, int seconds, int byoYomi, List<String> prisoners) {
			checked = true;
			return id;
		}

		@Override
		public String addGame(String server, String id, String white, String wRank, String black,
				String bRank, int turn, int size, int handicap, float komi, int byo, int observer) {
			fail();
			return null;
		}
	}
}

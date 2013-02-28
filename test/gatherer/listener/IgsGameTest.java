package gatherer.listener;

import gatherer.WeiqiStorage;
import models.BlackOrWhite;
import org.junit.Test;
import play.test.UnitTest;

import static gatherer.IgsConstants.*;

// 7 [##]  white name [ rk ]      black name [ rk ] (Move size H Komi BY FR) (###)
// 7 [17]        newt [ 1d*] vs.     VENUS55 [ 1d*] (178   19  0  6.5 10  I) (  0)
// 1 5
public class IgsGameTest extends UnitTest {

    @Test
    public void parseGameCheck() throws Exception {
        String line = "7 [1] white [ 3p ] vs. black [ 1p ] (2 3 4 5.5 6 I) ( 7)";
        WeiqiStorage storageMock = new WeiqiStorageMock() {
            @Override
            public void addMove(String server, String id, int number, BlackOrWhite player,
                                String coordinate, int seconds, int byo, String... prisoners) {
                fail("move in a game test?");
            }

            @Override
            public void addResult(String server, String game, String result) {
                fail("result in a game test?");
            }

            @Override
            public void addGame(String server, String id, String white, String wRank, String black,
                                String bRank, int turn, int size, int handicap, float komi, int byo,
                                int observer) {
                assertEquals("server:port", server);
                assertEquals("1", id);
                assertEquals("white", white);
                assertEquals("3p", wRank);
                assertEquals("black", black);
                assertEquals("1p", bRank);
                assertEquals(2, turn);
                assertEquals(3, size);
                assertEquals(4, handicap);
                assertEquals(5.5f, komi, 0.1);
                assertEquals(6, byo);
                assertEquals(7, observer);
            }
        };

        IgsGameList cut = new IgsGameList("server:port", storageMock);

        assertTrue(cut.notify(GAME_HEADER));
        assertTrue(cut.isRetrieving());
        assertTrue(line, cut.notify(line));
        assertTrue(cut.isRetrieving());
        assertFalse(cut.notify(OK));
        assertFalse(cut.isRetrieving());
    }

    private static final String[] testStrings = new String[]{
            "7 [53]   Tellmepls [ 4k*] vs.  masakazu48 [4k ] (272   19  0  0.5 10  I) (  0)",
            "7 [69]        tyuu [11k*] vs.        GONZ [11k] (198   19  0  6.5 10  I) (  1)",
            "7 [111]       snthk [ 6k ] vs.        ZIEL [ 6k*] (  3   19  0  6.5 10  I) (  0)",
            "7 [ 6]     Insight [ 1d*] vs.       Dhr46 [ 1d*] (  2   19  0  6.5  5  I) (  0)",
            "7 [99]        alea [ NR ] vs.     Maxaren [ NR ] (154   19  0  6.5  5  I) (  0)"};

    @Test
    public void parseGameList() throws Exception {

        WeiqiStorageMock storageMock = new WeiqiStorageMock();
        IgsGameList cut = new IgsGameList("server:port", storageMock);

        assertTrue(cut.notify(GAME_HEADER));
        for (String line : testStrings) {
            storageMock.gameCalled = false;
            assertTrue(line, cut.notify(line));
            assertTrue(storageMock.gameCalled);
        }
        assertFalse(cut.notify(OK));
        assertFalse(cut.isRetrieving());
    }

}

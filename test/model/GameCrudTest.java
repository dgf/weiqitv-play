package model;

import models.Game;
import models.GamePlayer;
import models.Handicap;
import models.Player;
import models.Rank;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import static models.WeiqiJpaFactory.*;

public class GameCrudTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }

    @Test
    public void crud() throws Exception {

        // create
        Game g = game("white", "5k", "black", "7k", 19, 2, 0.5f);

        assertEquals(1, Game.count());
        assertEquals(2, GamePlayer.count());
        assertEquals(2, Player.count());
        assertEquals(2, Rank.count());
        assertEquals(1, Handicap.count());

        // read
        g = Game.findById(g.id);
        assertEquals("white", g.white.player.name);
        assertEquals("5k", g.white.rank.toString());
        assertEquals("black", g.black.player.name);
        assertEquals("7k", g.black.rank.toString());
        assertEquals(2, g.handicap.stones);
        assertEquals(19, g.size.length);

        // update
        g.server = server("server", 7777);
        g.onlineId = "onlineId";
        g.turn = 17;
        g.byo = -1;
        g.observer = 61;
        g.save();

        // read again
        g = Game.findByServerHostAndOnlineId("server", "onlineId");
        assertEquals(17, g.turn);
        assertEquals(-1, g.byo);
        assertEquals(61, g.observer);

        // delete
        g.delete();

        assertEquals(0, Game.count());
        assertEquals(0, GamePlayer.count());
        assertEquals(2, Player.count());
        assertEquals(2, Rank.count());
        assertEquals(1, Handicap.count());

    }

}

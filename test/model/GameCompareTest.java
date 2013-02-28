package model;

import models.Game;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import static models.Game.BETTER_GAME_TURN_OFFSET;
import static models.WeiqiBeanFactory.game;

public class GameCompareTest extends UnitTest {

    private Game aGame;
    private Game aBetterGame;
    private Game aTeachingGame;

    @Before
    public void setup() {
        aGame = game("dagnu", "4k", "phuzen", "4k", 19, 0, 6.5f);
        aBetterGame = game("peter", "2k", "dagnu", "4k", 19, 2, 0.5f);
        aTeachingGame = game("kiscane", "11k", "dagnu", "4k", 19, 0, -35.5f);
    }

    @Test
    public void betterThanNull() {
        assertTrue(aGame.isBetterThan(null));
    }

    @Test
    public void betterThan() {

        aGame.turn = 3;

        aBetterGame.turn = 3 + BETTER_GAME_TURN_OFFSET;
        assertFalse(aGame.isBetterThan(aBetterGame));
        assertTrue(aBetterGame.isBetterThan(aGame));

        aBetterGame.turn = 3 + BETTER_GAME_TURN_OFFSET + 1;
        assertFalse(aGame.isBetterThan(aBetterGame));
        assertFalse(aBetterGame.isBetterThan(aGame));
    }

    @Test
    public void getRankOfStrongerPlayer() {
        assertEquals(aGame.white.rank, aGame.getRank());
        assertEquals(aBetterGame.white.rank, aBetterGame.getRank());
        assertEquals(aTeachingGame.black.rank, aTeachingGame.getRank());
    }

}

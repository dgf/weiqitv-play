package model;

import models.Handicap;
import models.Size;
import org.junit.Test;
import play.test.UnitTest;

import static models.Size.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class HandicapTest extends UnitTest {

    private void assertHandicapStones(Size size, int stones, String... coordinates) {
        assertThat(Handicap.getStones(size, stones), hasItems(coordinates));
    }

    @Test
    public void size9x9() throws Exception {
        assertTrue(Handicap.getStones(s9x9, 0).isEmpty());
        assertHandicapStones(s9x9, 2, "G7", "C3");
        assertHandicapStones(s9x9, 2, "G7", "C3");
        assertHandicapStones(s9x9, 3, "G7", "C3", "C7");
        assertHandicapStones(s9x9, 4, "G7", "C3", "C7", "G3");
    }

    @Test
    public void size13x13() throws Exception {
        assertHandicapStones(s9x9, 2, "G7", "C3");
        assertTrue(Handicap.getStones(s13x13, 0).isEmpty());
        assertHandicapStones(s13x13, 2, "K10", "D4");
        assertHandicapStones(s13x13, 3, "K10", "D4", "D10");
        assertHandicapStones(s13x13, 4, "K10", "D4", "D10", "K4");
        assertHandicapStones(s13x13, 5, "K10", "D4", "D10", "K4", "G7");
    }

    @Test
    public void size19x19() throws Exception {
        assertTrue(Handicap.getStones(s19x19, 0).isEmpty());
        assertHandicapStones(s19x19, 2, "Q16", "D4");
        assertHandicapStones(s19x19, 3, "Q16", "D4", "D16");
        assertHandicapStones(s19x19, 4, "Q16", "D4", "D16", "Q4");
        assertHandicapStones(s19x19, 5, "Q16", "D4", "D16", "Q4", "K10");
        assertHandicapStones(s19x19, 6, "Q16", "D4", "D16", "Q4", "Q10", "D10");
        assertHandicapStones(s19x19, 7, "Q16", "D4", "D16", "Q4", "Q10", "D10", "K10");
        assertHandicapStones(s19x19, 8, "Q16", "D4", "D16", "Q4", "Q10", "D10", "K16", "K4");
        assertHandicapStones(s19x19, 9, "Q16", "D4", "D16", "Q4", "Q10", "D10", "K16", "K4", "K10");
    }

}

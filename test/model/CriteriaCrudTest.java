package model;

import models.Criteria;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import static models.WeiqiJpaFactory.criteria;

public class CriteriaCrudTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }

    @Test
    public void crud() throws Exception {
        // create
        Criteria c = criteria("criteria", 19, "1d", "9d", 0, 4);
        assertEquals(1, Criteria.count());

        // read
        c = Criteria.findById(c.id);
        assertEquals("criteria", c.name);
        assertEquals(19, c.size.length);
        assertEquals("1d", c.minRank.toString());
        assertEquals("9d", c.maxRank.toString());
        assertEquals(0, c.minHandicap.stones);
        assertEquals(4, c.maxHandicap.stones);

        // update
        c.name = "updated criteria";
        c.save();
        assertEquals(1, Criteria.count());

        // read again
        c = Criteria.findById(c.id);
        assertEquals("updated criteria", c.name);

        // delete
        c.delete();
        assertEquals(0, Criteria.count());
    }
}

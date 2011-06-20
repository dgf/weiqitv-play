package model;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class PlayerTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("data.yml");
	}

	@Test
	public void count() throws Exception {
		// assertEquals(2, Player.count());
	}

	@Test
	public void check() {
		// Player dan = Player.find("byName", "beginner").first();
		// assertEquals("beginner", dan.name);
		// assertEquals("1d", dan.rank.toString());
		// dan.save();
	}

}

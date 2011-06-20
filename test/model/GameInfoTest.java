package model;

import models.GameInfo;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class GameInfoTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("data.yml");
	}

	@Test
	public void count() throws Exception {
		assertEquals(1, GameInfo.count());
	}

	@Test
	public void checkInfo() {

	}

}

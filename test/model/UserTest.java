package model;

import java.net.MalformedURLException;

import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class UserTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("data.yml");
	}

	@Test
	public void count() throws Exception {
		assertEquals(3, User.count());
	}

	@Test
	public void createAndRetrieveUser() throws MalformedURLException {
		// Retrieve the user by mail address
		User u = User.find("byMail", "admin@g2d.de").first();

		// Test
		assertNotNull(u);
		assertEquals("Administrator", u.fullname);
		assertEquals("admin@g2d.de", u.mail);
		assertEquals("secretAdmin", u.password);
		assertEquals("http://www.g2d.de", u.website);
		assertTrue(u.isAdmin);
	}

	@Test
	public void tryConnectAsUser() {
		assertNotNull(User.connect("admin@g2d.de", "secretAdmin"));
		assertNull(User.connect("admin@g2d.de", "badpassword"));
	}

}

package model;

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

        // assert fixture count
        assertEquals(3, User.count());
    }

    @Test
    public void tryConnectAsUser() {
        assertNotNull(User.connect("admin", "secretAdmin"));
        assertNull(User.connect("admin", "badpassword"));
    }

}

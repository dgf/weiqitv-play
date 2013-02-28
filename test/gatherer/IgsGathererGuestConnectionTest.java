package gatherer;

import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import static gatherer.IgsConstants.*;

public class IgsGathererGuestConnectionTest extends UnitTest {

    private IgsGatherer cut;

    @Before
    public void setUp() {
        cut = new IgsGatherer(null);
        cut.setServer(IGS_SERVER, IGS_PORT);
    }

    @Test
    public void loginAsGuestAndLogout() {

        assertFalse(cut.isConnected());

        cut.connect();
        assertTrue(cut.isConnected());
        assertFalse(cut.isLoggedIn());

        cut.loginAnonymous();
        assertTrue(cut.isLoggedIn());

        cut.logout();
        assertFalse(cut.isLoggedIn());
        assertTrue(cut.isConnected());

        cut.disconnect();
        assertFalse(cut.isConnected());
    }
}

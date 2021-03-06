package gatherer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import static gatherer.IgsConstants.*;

public class TelnetUtilIgsLoginTestCase extends UnitTest {

    private TelnetUtil cut;

    @Before
    public void setUp() throws Exception {
        cut = TelnetUtil.connect(IGS_SERVER, IGS_PORT);
    }

    @After
    public void tearDown() throws Exception {
        cut.disconnect();
    }

    @Test
    public void loginAsGuest() {
        cut.readUntil("Login: ");
        cut.send("guest");
        cut.readUntil("#> ");
        cut.send("exit");
    }

}

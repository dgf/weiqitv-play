package model;

import org.junit.Test;

import static models.RankType.*;
import static org.junit.Assert.assertEquals;

public class RankTypeTest {

    @Test
    public void compare() throws Exception {
        assertEquals(0, NR.compareTo(NR));
        assertEquals(-1, NR.compareTo(KYU));
        assertEquals(-2, NR.compareTo(DAN));
        assertEquals(-3, NR.compareTo(PRO));

        assertEquals(0, KYU.compareTo(KYU));
        assertEquals(-1, KYU.compareTo(DAN));
        assertEquals(-2, KYU.compareTo(PRO));

        assertEquals(1, DAN.compareTo(KYU));
        assertEquals(0, DAN.compareTo(DAN));
        assertEquals(-1, DAN.compareTo(PRO));

        assertEquals(2, PRO.compareTo(KYU));
        assertEquals(1, PRO.compareTo(DAN));
        assertEquals(0, PRO.compareTo(PRO));
    }
}

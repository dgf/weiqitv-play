package model;

import org.junit.Test;

import static models.WeiqiBeanFactory.rank;
import static org.junit.Assert.*;

public class RankCompareTest {

    private boolean le(String a, String b) {
        return rank(a).le(rank(b));
    }

    private boolean ge(String a, String b) {
        return rank(a).ge(rank(b));
    }

    private int compare(String a, String b) {
        return rank(a).compareTo(rank(b));
    }

    @Test
    public void compareKyuRanks() throws Exception {
        assertEquals(0, compare("5k", "5k"));
        assertTrue(le("5k", "5k"));
        assertTrue(ge("5k", "5k"));

        assertEquals(-2, compare("7k", "5k"));
        assertTrue(le("7k", "5k"));
        assertFalse(ge("7k", "5k"));

        assertEquals(6, compare("1k", "7k"));
        assertTrue(ge("1k", "7k"));
        assertFalse(le("1k", "7k"));
    }

    @Test
    public void compareDanRanks() throws Exception {
        assertEquals(0, compare("3d", "3d"));
        assertTrue(le("3d", "3d"));
        assertTrue(ge("3d", "3d"));

        assertEquals(-4, compare("3d", "7d"));
        assertTrue(le("3d", "7d"));
        assertFalse(ge("3d", "7d"));

        assertEquals(6, compare("7d", "1d"));
        assertTrue(ge("7d", "1d"));
        assertFalse(le("7d", "1d"));
    }

    @Test
    public void compareProRanks() throws Exception {
        assertEquals(0, compare("1p", "1p"));
        assertTrue(le("1p", "1p"));
        assertTrue(ge("1p", "1p"));

        assertEquals(-2, compare("1p", "3p"));
        assertTrue(le("1p", "3p"));
        assertFalse(ge("1p", "3p"));

        assertEquals(2, compare("3p", "1p"));
        assertTrue(ge("3p", "1p"));
        assertFalse(le("3p", "1p"));
    }

    @Test
    public void compareDanKyuRanks() throws Exception {
        assertEquals(-1, compare("1k", "1d"));
        assertEquals(1, compare("1d", "1k"));
        assertTrue(le("1k", "1d"));
        assertFalse(ge("1k", "1d"));

        assertEquals(4, compare("2d", "3k"));
        assertEquals(-4, compare("3k", "2d"));
        assertTrue(ge("2d", "3k"));
        assertFalse(le("2d", "3k"));
    }

    @Test
    public void compareKyuProRanks() throws Exception {
        assertEquals(-10, compare("1k", "1p"));
        assertEquals(10, compare("1p", "1k"));
        assertTrue(le("1k", "1p"));
        assertFalse(ge("1k", "1p"));

        assertEquals(15, compare("3p", "4k"));
        assertEquals(-15, compare("4k", "3p"));
        assertTrue(ge("3p", "4k"));
        assertFalse(le("3p", "4k"));
    }

    @Test
    public void compareDanProRanks() throws Exception {
        assertEquals(-1, compare("9d", "1p"));
        assertEquals(1, compare("1p", "9d"));
        assertTrue(le("9d", "1p"));
        assertFalse(ge("9d", "1p"));

        assertEquals(-5, compare("7d", "3p"));
        assertEquals(5, compare("3p", "7d"));
        assertTrue(ge("3p", "7d"));
        assertFalse(le("3p", "7d"));
    }

}

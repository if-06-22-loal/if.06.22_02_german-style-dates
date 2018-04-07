import org.junit.Test;

import java.util.regex.Matcher;

import static org.junit.Assert.*;

public class GermanStyleDateCheckerTests {
    /* Write a regular expression that matches German written dates.
    They are in the form dd.mm.yyyy, where the separator ”.” can
    also be ”-” or a blank. The leading digit d or m can be omitted.
    Valid years are between 1900 and 2099. But the leading two yy
    can also be omitted.

    Take care that the unit tests have to pass in the order as given
    in this file here. Tests passing after one failed test will not
    count.
     */

    @Test
    public void testSimpleValidDate() {
        checkRegex("23.03.1967");
    }

    private void checkRegex(final String testString) {
        Matcher m = GermanStyleDateChecker.getMatcher(testString);
        assertTrue(m.find());
        assertEquals(testString, m.group());
        assertFalse(m.find());
    }

    @Test
    public void testUnsupportedSeparator() {
        Matcher m = GermanStyleDateChecker.getMatcher("02:04:1935");
        assertFalse(m.find());
    }

    @Test
    public void testInvalidDayOfMonth() {
        Matcher m = GermanStyleDateChecker.getMatcher("32.08.2012");
        assertFalse(m.find());
    }

    @Test
    public void testValidDayOfMonthSinceWeOnlyCheckWhetherBetween0And31() {
        checkRegex("31.02.2015");
    }

    @Test
    public void testMissingZerosAndCentury() {
        checkRegex("1.3.95");
    }

    @Test
    public void testInvalidMonth() {
        Matcher m = GermanStyleDateChecker.getMatcher("22.13.2000");
        assertFalse(m.find());
    }

    @Test
    public void testYearOutOfRange() {
        Matcher m = GermanStyleDateChecker.getMatcher("18.3.1899");
        assertFalse(m.find());
    }

    @Test
    public void testDashAsSeparator() {
        checkRegex("28-04-2014");
    }

    @Test
    public void testBlankAsSeparator() {
        checkRegex("8 4 2014");
    }

    @Test
    public void testNoEmbeddingSupported() {
        Matcher m = GermanStyleDateChecker.getMatcher("today is the 7.4.14");
        assertFalse(m.find());
    }

    @Test
    public void testSeparatorMix() {
        checkRegex("2-2.1978");
    }

}
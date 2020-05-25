import org.junit.jupiter.api.Test;
import java.util.regex.Matcher;
import static org.junit.jupiter.api.Assertions.*;

public class GermanStyleDateCheckerTest {
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
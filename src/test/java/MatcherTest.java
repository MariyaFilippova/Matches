import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class MatcherTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;

    @Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void interrupted() {
        System.out.println(Matcher.matches("dssdfsdfdsf wdasdads dadlkn mdsds .", "^([a-zA-Z]+ *)+$"));
        Assert.assertEquals(testOut.toString(), "Too many backtrack operations!\nfalse\n");
    }

    @Test
    public void incorrectRegex() {
        System.out.println(Matcher.matches("dssdfsdfdsf", "[(])"));
        Assert.assertEquals(testOut.toString(), "Incorrect regex\nfalse\n");
    }

    @Test
    public void positiveResult() {
        System.out.println(Matcher.matches("dssdfsdfdsf", "[a-z]*"));
        Assert.assertEquals(testOut.toString(), "true\n");
    }
}

import org.junit.Assert;
import org.junit.Test;
import scanner.language.Regex;

public class RegexTest {

    @Test
    public void testCharacterCount() {
        Regex regex = new Regex("abc");

        for (int i = 0; i < 3; i++) {
            Assert.assertTrue( regex.hasNextChar());
            regex.getNextChar();
        }
        Assert.assertFalse( regex.hasNextChar());
    }

    @Test
    public void testAlphaNumericReturnsCorrectly() {
        Regex regex = new Regex("abc");

        Assert.assertEquals('a', regex.getNextChar());
        Assert.assertEquals('b', regex.getNextChar());
        Assert.assertEquals('c', regex.getNextChar());
        Assert.assertEquals('\n', regex.getNextChar());
    }
}
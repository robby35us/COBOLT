package scanner.generator.thompsons;

import org.junit.Assert;
import org.junit.Test;
import scanner.language.Regex;
import scanner.model.automata.NDFA;
import scanner.model.state.NDFAState;

public class RegexParserTest {
    private RegexParser parser = new RegexParser();

    @Test
    public void emptyStringProducesEmptyFA() {
        Regex pattern = new Regex("");
        NDFA actual = parser.parseExpression(pattern);
        Assert.assertEquals(new NDFA(new NDFAState(true)), actual);
    }

    @Test
    public void singleCharProducesSimpleFA() {
        // Arrange
        Regex pattern = new Regex("a");

        NDFA expected = IntermediateNDFABuilder.buildSimple('a');

        // Act
        NDFA actual = parser.parseExpression(pattern);

        //Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void twoCharsProducesAndFA() {
        // Arrange
        Regex pattern = new Regex("ab");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA expected = IntermediateNDFABuilder.buildAnd(a, b);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void threeCharsProducesAndAndFA() {
        // Arrange
        Regex pattern = new Regex("abc");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA temp = IntermediateNDFABuilder.buildAnd(a, b);
        NDFA c = IntermediateNDFABuilder.buildSimple('c');
        NDFA expected = IntermediateNDFABuilder.buildAnd(temp, c);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharProducesOrFA() {
        // Arrange
        Regex pattern = new Regex("a|b");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA expected = IntermediateNDFABuilder.buildOr(a, b);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharOrCharProducesOrOrFa() {
        // Arrange
        Regex pattern = new Regex("a|b|c");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA c = IntermediateNDFABuilder.buildSimple('c');
        NDFA temp = IntermediateNDFABuilder.buildOr(a, b);
        NDFA expected = IntermediateNDFABuilder.buildOr(temp, c);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charAndCharOrCharProducesAndOrFa() {
        // Arrange
        Regex pattern = new Regex("ab|c");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA c = IntermediateNDFABuilder.buildSimple('c');
        NDFA temp = IntermediateNDFABuilder.buildAnd(a, b);
        NDFA expected = IntermediateNDFABuilder.buildOr(temp, c);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharAndCharProducesAndOrFa() {
        // Arrange
        Regex pattern = new Regex("a|bc");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA c = IntermediateNDFABuilder.buildSimple('c');
        NDFA temp = IntermediateNDFABuilder.buildAnd(b, c);
        NDFA expected = IntermediateNDFABuilder.buildOr(a, temp);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharAndCharOrCharProducesAndORORFA() {
        // Arrange
        Regex pattern = new Regex("a|bc|d");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA c = IntermediateNDFABuilder.buildSimple('c');
        NDFA d = IntermediateNDFABuilder.buildSimple('d');
        NDFA temp1 = IntermediateNDFABuilder.buildAnd(b, c);
        NDFA temp2 = IntermediateNDFABuilder.buildOr(a, temp1);
        NDFA expected = IntermediateNDFABuilder.buildOr(temp2, d);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void perCharPerProducesCharFA() {
        // Arrange
        Regex pattern = new Regex("(a)");

        NDFA expected = IntermediateNDFABuilder.buildSimple('a');

        // Act
        NDFA actual = parser.parseExpression(pattern);

        //Assert
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void perCharOrCharPerAndCharOrCharProducesORAndOr() {
        // Arrange
        Regex pattern = new Regex("(a|b)c|d");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA c = IntermediateNDFABuilder.buildSimple('c');
        NDFA d = IntermediateNDFABuilder.buildSimple('d');
        NDFA temp1 = IntermediateNDFABuilder.buildOr(a, b);
        NDFA temp2 = IntermediateNDFABuilder.buildAnd(temp1, c);
        NDFA expected = IntermediateNDFABuilder.buildOr(temp2, d);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void perCharOrCharPerAndCharOrCharProducesOrOrAnd() {
        // Arrange
        Regex pattern = new Regex("(a|b)(c|d)");
        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA c = IntermediateNDFABuilder.buildSimple('c');
        NDFA d = IntermediateNDFABuilder.buildSimple('d');
        NDFA temp1 = IntermediateNDFABuilder.buildOr(a, b);
        NDFA temp2 = IntermediateNDFABuilder.buildOr(c, d);
        NDFA expected = IntermediateNDFABuilder.buildAnd(temp1, temp2);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void CharOrCharAndPerCharOrCharPerProducesOrAndOr() {
        // Arrange
        Regex pattern = new Regex("a|b(c|d)");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA c = IntermediateNDFABuilder.buildSimple('c');
        NDFA d = IntermediateNDFABuilder.buildSimple('d');
        NDFA temp1 = IntermediateNDFABuilder.buildOr(c, d);
        NDFA temp2 = IntermediateNDFABuilder.buildAnd(b, temp1);
        NDFA expected = IntermediateNDFABuilder.buildOr(a, temp2);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charStarProducesStarFa() {
        // Arrange
        Regex pattern = new Regex("a*");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA expected = IntermediateNDFABuilder.buildClosure(a);

        // Act
        NDFA actual = parser.parseExpression(pattern);


        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void orInClosure() {
        // Arrange
        Regex pattern = new Regex("(a|b)*");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA temp = IntermediateNDFABuilder.buildOr(a, b);
        NDFA expected = IntermediateNDFABuilder.buildClosure(temp);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void integrationTest() {
        // Arrange
        Regex pattern = new Regex("a(bc|d*(e|f))(g|h)ij");

        NDFA a = IntermediateNDFABuilder.buildSimple('a');
        NDFA b = IntermediateNDFABuilder.buildSimple('b');
        NDFA c = IntermediateNDFABuilder.buildSimple('c');
        NDFA d = IntermediateNDFABuilder.buildSimple('d');
        NDFA e = IntermediateNDFABuilder.buildSimple('e');
        NDFA f = IntermediateNDFABuilder.buildSimple('f');
        NDFA g = IntermediateNDFABuilder.buildSimple('g');
        NDFA h = IntermediateNDFABuilder.buildSimple('h');
        NDFA i = IntermediateNDFABuilder.buildSimple('i');
        NDFA j = IntermediateNDFABuilder.buildSimple('j');
        NDFA temp1 = IntermediateNDFABuilder.buildAnd(b, c);
        NDFA temp2 = IntermediateNDFABuilder.buildClosure(d);
        NDFA temp3 = IntermediateNDFABuilder.buildOr(e,f);
        NDFA temp4 = IntermediateNDFABuilder.buildAnd(temp2, temp3);
        NDFA temp5 = IntermediateNDFABuilder.buildOr(temp1, temp4);
        NDFA temp6 = IntermediateNDFABuilder.buildAnd(a, temp5);
        NDFA temp7 = IntermediateNDFABuilder.buildOr(g, h);
        NDFA temp8 = IntermediateNDFABuilder.buildAnd(temp6, temp7);
        NDFA temp9 = IntermediateNDFABuilder.buildAnd(temp8, i);
        NDFA expected = IntermediateNDFABuilder.buildAnd(temp9, j);

        // Act
        NDFA actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }
}

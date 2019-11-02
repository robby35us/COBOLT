package scanner.generator.thompsons;

import org.junit.Assert;
import org.junit.Test;
import scanner.language.Regex;
import scanner.model.FiniteAutomaton;
import scanner.model.State;

public class RegexParserTest {
    private RegexParser parser = new RegexParser();

    @Test
    public void emptyStringProducesEmptyFA() {
        Regex pattern = new Regex("");
        FiniteAutomaton actual = parser.parseExpression(pattern);
        Assert.assertEquals(new FiniteAutomaton(new State(true), false), actual);
    }

    @Test
    public void singleCharProducesSimpleFA() {
        // Arrange
        Regex pattern = new Regex("a");

        FiniteAutomaton expected = AutomataBuilder.buildSimple('a');

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        //Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void twoCharsProducesAndFA() {
        // Arrange
        Regex pattern = new Regex("ab");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton expected = AutomataBuilder.buildAnd(a, b);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void threeCharsProducesAndAndFA() {
        // Arrange
        Regex pattern = new Regex("abc");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton temp = AutomataBuilder.buildAnd(a, b);
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton expected = AutomataBuilder.buildAnd(temp, c);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharProducesOrFA() {
        // Arrange
        Regex pattern = new Regex("a|b");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton expected = AutomataBuilder.buildOr(a, b);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharOrCharProducesOrOrFa() {
        // Arrange
        Regex pattern = new Regex("a|b|c");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton temp = AutomataBuilder.buildOr(a, b);
        FiniteAutomaton expected = AutomataBuilder.buildOr(temp, c);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charAndCharOrCharProducesAndOrFa() {
        // Arrange
        Regex pattern = new Regex("ab|c");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton temp = AutomataBuilder.buildAnd(a, b);
        FiniteAutomaton expected = AutomataBuilder.buildOr(temp, c);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharAndCharProducesAndOrFa() {
        // Arrange
        Regex pattern = new Regex("a|bc");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton temp = AutomataBuilder.buildAnd(b, c);
        FiniteAutomaton expected = AutomataBuilder.buildOr(a, temp);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charOrCharAndCharOrCharProducesAndORORFA() {
        // Arrange
        Regex pattern = new Regex("a|bc|d");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton d = AutomataBuilder.buildSimple('d');
        FiniteAutomaton temp1 = AutomataBuilder.buildAnd(b, c);
        FiniteAutomaton temp2 = AutomataBuilder.buildOr(a, temp1);
        FiniteAutomaton expected = AutomataBuilder.buildOr(temp2, d);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void perCharPerProducesCharFA() {
        // Arrange
        Regex pattern = new Regex("(a)");

        FiniteAutomaton expected = AutomataBuilder.buildSimple('a');

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        //Assert
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void perCharOrCharPerAndCharOrCharProducesORAndOr() {
        // Arrange
        Regex pattern = new Regex("(a|b)c|d");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton d = AutomataBuilder.buildSimple('d');
        FiniteAutomaton temp1 = AutomataBuilder.buildOr(a, b);
        FiniteAutomaton temp2 = AutomataBuilder.buildAnd(temp1, c);
        FiniteAutomaton expected = AutomataBuilder.buildOr(temp2, d);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void perCharOrCharPerAndCharOrCharProducesOrOrAnd() {
        // Arrange
        Regex pattern = new Regex("(a|b)(c|d)");
        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton d = AutomataBuilder.buildSimple('d');
        FiniteAutomaton temp1 = AutomataBuilder.buildOr(a, b);
        FiniteAutomaton temp2 = AutomataBuilder.buildOr(c, d);
        FiniteAutomaton expected = AutomataBuilder.buildAnd(temp1, temp2);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void CharOrCharAndPerCharOrCharPerProducesOrAndOr() {
        // Arrange
        Regex pattern = new Regex("a|b(c|d)");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton d = AutomataBuilder.buildSimple('d');
        FiniteAutomaton temp1 = AutomataBuilder.buildOr(c, d);
        FiniteAutomaton temp2 = AutomataBuilder.buildAnd(b, temp1);
        FiniteAutomaton expected = AutomataBuilder.buildOr(a, temp2);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void charStarProducesStarFa() {
        // Arrange
        Regex pattern = new Regex("a*");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton expected = AutomataBuilder.buildClosure(a);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);


        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void orInClosure() {
        // Arrange
        Regex pattern = new Regex("(a|b)*");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton temp = AutomataBuilder.buildOr(a, b);
        FiniteAutomaton expected = AutomataBuilder.buildClosure(temp);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void integrationTest() {
        // Arrange
        Regex pattern = new Regex("a(bc|d*(e|f))(g|h)ij");

        FiniteAutomaton a = AutomataBuilder.buildSimple('a');
        FiniteAutomaton b = AutomataBuilder.buildSimple('b');
        FiniteAutomaton c = AutomataBuilder.buildSimple('c');
        FiniteAutomaton d = AutomataBuilder.buildSimple('d');
        FiniteAutomaton e = AutomataBuilder.buildSimple('e');
        FiniteAutomaton f = AutomataBuilder.buildSimple('f');
        FiniteAutomaton g = AutomataBuilder.buildSimple('g');
        FiniteAutomaton h = AutomataBuilder.buildSimple('h');
        FiniteAutomaton i = AutomataBuilder.buildSimple('i');
        FiniteAutomaton j = AutomataBuilder.buildSimple('j');
        FiniteAutomaton temp1 = AutomataBuilder.buildAnd(b, c);
        FiniteAutomaton temp2 = AutomataBuilder.buildClosure(d);
        FiniteAutomaton temp3 = AutomataBuilder.buildOr(e,f);
        FiniteAutomaton temp4 = AutomataBuilder.buildAnd(temp2, temp3);
        FiniteAutomaton temp5 = AutomataBuilder.buildOr(temp1, temp4);
        FiniteAutomaton temp6 = AutomataBuilder.buildAnd(a, temp5);
        FiniteAutomaton temp7 = AutomataBuilder.buildOr(g, h);
        FiniteAutomaton temp8 = AutomataBuilder.buildAnd(temp6, temp7);
        FiniteAutomaton temp9 = AutomataBuilder.buildAnd(temp8, i);
        FiniteAutomaton expected = AutomataBuilder.buildAnd(temp9, j);

        // Act
        FiniteAutomaton actual = parser.parseExpression(pattern);

        // Assert
        Assert.assertEquals(expected, actual);
    }
}

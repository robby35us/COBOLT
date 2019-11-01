package scanner.language;

import java.util.regex.Pattern;


public class Regex implements Comparable<Regex> {
    private Pattern pattern;
    private int position = 0;

    public Regex(String regexString) {
        //TODO  - handle bad regex exceptions
        pattern = Pattern.compile(regexString);
    }

    @Override
    public String toString() {
        return pattern.pattern();
    }

    public char getNextChar() {
        if (position < pattern.toString().length())
            return pattern.toString().charAt(position++);
        else
            return '\n';
    }

    public boolean hasNextChar() {
        return position < pattern.pattern().length();
    }

    @Override
    public int compareTo(Regex re) {
        return pattern.toString().compareTo(re.toString());
    }
}

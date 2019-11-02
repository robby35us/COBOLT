package scanner.generator.thompsons;

import scanner.language.Regex;
import scanner.model.FiniteAutomaton;
import java.util.HashSet;
import java.util.Set;

public class ThompsonsConstruction {

    private RegexParser parser = new RegexParser();

    public FiniteAutomaton apply(Set<Regex> patterns) {
        Set<FiniteAutomaton> intermediateNFAs
                = produceIntermediateNFAs(patterns);
        return new NDFABuilder().build(intermediateNFAs);
    }

    private Set<FiniteAutomaton> produceIntermediateNFAs(Set<Regex> patterns) {
        Set<FiniteAutomaton> intermediateNFAs = new HashSet<>();
        for (Regex pattern : patterns) {
            intermediateNFAs.add(parser.parseExpression(pattern));
        }
        return intermediateNFAs;
    }

}
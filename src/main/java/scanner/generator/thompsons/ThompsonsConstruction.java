package scanner.generator.thompsons;

import scanner.language.Regex;
import scanner.model.NDFA;

import java.util.HashSet;
import java.util.Set;

public class ThompsonsConstruction {

    private RegexParser parser = new RegexParser();

    public NDFA apply(Set<Regex> patterns) {
        Set<NDFA> intermediateNDFAs
                = produceIntermediateNDFAs(patterns);
        return new ResultNDFABuilder().build(intermediateNDFAs);
    }

    private Set<NDFA> produceIntermediateNDFAs(Set<Regex> patterns) {
        Set<NDFA> intermediateNDFAs = new HashSet<>();
        for (Regex pattern : patterns) {
            intermediateNDFAs.add(parser.parseExpression(pattern));
        }
        return intermediateNDFAs;
    }

}
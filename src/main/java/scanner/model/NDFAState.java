package scanner.model;

import java.util.HashSet;
import java.util.Set;

import static scanner.model.FiniteAutomaton.EPSILON;

public class NDFAState extends State {

    private Set<NDFAState> epsilonStates;

    public NDFAState(boolean acceptingState) {
        super(acceptingState);
        epsilonStates = new HashSet<>();
    }

    @Override
    public void setEndState(char c, State endState) {
        if (c == EPSILON)
            epsilonStates.add((NDFAState) endState);
        else
            super.setEndState(c, endState);
    }

    public Set<NDFAState> getEpsilonStates() {
        return epsilonStates;
    }
}

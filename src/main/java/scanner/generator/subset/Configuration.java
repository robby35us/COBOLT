package scanner.generator.subset;

import scanner.model.NDFAState;
import scanner.model.State;

import java.util.Set;

public class Configuration {
    private Set<NDFAState> states;
    private State resultingState;

    Configuration(Set<NDFAState> states) {

        this.states = states;
        boolean markAsAccepting = false;
        for (State s : states) {
            if (s.isAcceptingState()) {
                markAsAccepting = true;
                break;
            }
        }
        resultingState = new State(markAsAccepting);
    }

    Set<NDFAState> getStates() {
        return states;
    }

    State getResultingState() {
        return resultingState;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Configuration) {
            this.states.containsAll(((Configuration) o).states);
        }
        return false;
    }
}
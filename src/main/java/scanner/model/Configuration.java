package scanner.model;

import java.util.Set;

public class Configuration {
    private Set<State> states;
    private State resultingState;

    public Configuration(Set<State> states) {

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

    public Set<State> getStates() {
        return states;
    }

    public State getResultingState() {
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
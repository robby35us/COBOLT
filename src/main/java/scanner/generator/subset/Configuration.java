package scanner.generator.subset;

import scanner.model.state.NDFAState;
import scanner.model.state.PartitionState;
import scanner.model.state.State;

import java.util.Set;

public class Configuration {
    private Set<NDFAState> states;
    private PartitionState resultingState;

    Configuration(Set<NDFAState> states) {

        this.states = states;
        boolean markAsAccepting = false;
        for (State s : states) {
            if (s.isAcceptingState()) {
                markAsAccepting = true;
                break;
            }
        }
        resultingState = new PartitionState(markAsAccepting);
    }

    Set<NDFAState> getStates() {
        return states;
    }

    PartitionState getResultingState() {
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
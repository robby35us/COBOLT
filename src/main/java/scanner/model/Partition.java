package scanner.model;

import java.util.Set;

public class Partition {
    private Set<State> states;

    public Partition(Set<State> states) {
        this.states = states;
        for(State s : states) {
            s.setPartition(this);
        }
    }

    public Set<State> getStates() {
        return states;
    }

    public boolean containsAcceptingState() {
        for(State s : states) {
            if (s.isAcceptingState())
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Partition) {
            return this.states.equals(((Partition) o).states);
        }
        return false;
    }
}
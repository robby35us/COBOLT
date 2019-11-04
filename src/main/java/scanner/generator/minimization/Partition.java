package scanner.generator.minimization;

import scanner.model.state.PartitionState;
import scanner.model.state.State;

import java.util.Set;

public class Partition {
    private Set<PartitionState> states;

    Partition(Set<PartitionState> states) {
        this.states = states;
        for(PartitionState s : states) {
            s.setPartition(this);
        }
    }

    Set<PartitionState> getStates() {
        return states;
    }

    boolean containsAcceptingState() {
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
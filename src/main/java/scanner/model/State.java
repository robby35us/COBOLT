package scanner.model;

import scanner.generator.minimization.Partition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static scanner.model.FiniteAutomaton.EPSILON;

public class State {

    // state specific data
    private Map<Character, State> endStateMap;
    private boolean acceptingState;

    // for depth-first search
    private boolean marked = false;

    // for the minimization algorithm
    private Partition partition;

    public State(boolean acceptingState) {
        this.acceptingState = acceptingState;
        endStateMap = new HashMap<>();


    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void setPartition(Partition p) {
        partition = p;
    }

    public Partition getPartition() {
        return partition;
    }

    public void setAcceptingState(boolean acceptingState) {
        this.acceptingState = acceptingState;
    }

    public boolean isAcceptingState() {
        return acceptingState;
    }

    public void setEndState(char c, State endState) {
            endStateMap.put (c, endState);
    }

    public void setEndState(Set<Character> characterSet, State endState) {
        for(char c : characterSet) {
            endStateMap.put(c, endState);
        }
    }

    public State getEndState(char c) {
        return endStateMap.get(c);
    }

    public Set<Character> getOutTransitionChars() {
        return endStateMap.keySet();
    }
}

package scanner.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static scanner.model.FiniteAutomaton.EPSILON;

public class State {
    private Partition partition;
    private boolean acceptingState;
    private boolean marked = false;
    private Map<Character, State> endStateMap;
    private Set<State> epsilonStates;

    public State(boolean acceptingState) {
        this.acceptingState = acceptingState;
        endStateMap = new HashMap<>();
        epsilonStates = new HashSet<>();
    }

    boolean isMarked() {
        return marked;
    }

    void setMarked(boolean marked) {
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
        if(c == EPSILON)
            epsilonStates.add(endState);
        else
            endStateMap.put (c, endState);
    }

    void setEndState(Set<Character> characterSet, State endState) {
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

    Set<State> getEpsilonStates() {
        return epsilonStates;
    }
}

package scanner.model.state;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class State {

    // state specific data
    private Map<Character, State> endStateMap;
    private boolean acceptingState;

    // for finding cyclic paths in depth-first search algorithms
    private boolean marked = false;




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

package scanner.model.automata;

import scanner.generator.util.FAPathExplorer;
import scanner.model.state.State;

import java.util.HashSet;
import java.util.Set;

public class DFA {

    public static final char EPSILON = '~';

    private State startingState;
    private Set<State> states;
    private Set<State> acceptingStates;

    public DFA(State startingState) {
        this.startingState = startingState;
        this.states = new HashSet<>();
        this.states.add(startingState);
        this.acceptingStates = new HashSet<>();
    }

    public void connectStatesOnChar(char c, State startState, State endState) {
        startState.setEndState(c, endState);
        this.addEndState(endState);
    }

    public void connectStatesOnChars(Set<Character> chars, State startState, State endState) {
        startState.setEndState(chars, endState);
        this.addEndState(endState);
    }

    void addEndState(State endState) {
        if(endState == null)
            return;
        states.add(endState);
        if (endState.isAcceptingState()) {
            acceptingStates.add(endState);
        }
    }

    public void addEndStates(Set<State> endStates) {
        for (State endState : endStates)
            addEndState(endState);
    }

    public void updateAcceptingStates() {
        acceptingStates.clear();
        for (State s : states) {
            if (s.isAcceptingState()) {
                acceptingStates.add(s);
            }
        }
    }

    public State getStartingState() {
        return startingState;
    }

    public Set<State> getStates() {
        return states;
    }

    public Set<State> getAcceptingStates() {
        return acceptingStates;
    }

    public Set<State> getNonAcceptingStates() {
        Set<State> nonAcceptingStates = new HashSet<>();
        for (State s : states)
            if (!acceptingStates.contains(s))
                nonAcceptingStates.add(s);
        return nonAcceptingStates;
    }



    @Override
    public String toString() {
        return new FAPathExplorer(this).generatePathList().toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DFA && this.toString().equals(o.toString());
    }
}
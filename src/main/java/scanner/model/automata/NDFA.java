package scanner.model.automata;

import scanner.model.state.NDFAState;
import scanner.model.state.State;

import java.util.HashSet;
import java.util.Set;

public class NDFA {

    private DFA automaton;

    public NDFA(NDFAState startingState) {
        automaton = new DFA(startingState);
    }

    public void connectStatesOnChar(char c, NDFAState startState, NDFAState endState) {
        automaton.connectStatesOnChar(c, startState, endState);
    }

    public void addEndState(NDFAState endState) {
        automaton.addEndState(endState);
    }

    public void addEndStates(Set<NDFAState> endStates) {
        for(NDFAState endState : endStates)
            automaton.addEndState(endState);
    }

    public void updateAcceptingStates() {
        automaton.updateAcceptingStates();
    }

    public NDFAState getStartingState() {
        return (NDFAState) automaton.getStartingState();
    }

    public Set<NDFAState> getStates() {
        Set<NDFAState> states = new HashSet<>();
        for(State s : automaton.getStates()) {
            states.add((NDFAState) s);
        }
        return states;
    }

    public Set<NDFAState> getAcceptingStates() {
        Set<NDFAState> acceptingStates = new HashSet<>();
        for(State s : automaton.getAcceptingStates()) {
            acceptingStates.add((NDFAState) s);
        }
        return acceptingStates;
    }

    @Override
    public String toString() {
        return automaton.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof NDFA && automaton.equals(((NDFA) o).automaton);
    }
}

package scanner.model.automata;

import scanner.model.state.PartitionState;
import scanner.model.state.State;

import java.util.HashSet;
import java.util.Set;

public class PartitionDFA {

    private DFA automaton;

    public PartitionDFA(PartitionState startingState) {
        automaton = new DFA(startingState);
    }

    public void connectStatesOnChar(char c, PartitionState startState, PartitionState endState) {
        automaton.connectStatesOnChar(c, startState, endState);
    }

    public void updateAcceptingStates() {
        automaton.updateAcceptingStates();
    }

    public PartitionState getStartingState() {
        return (PartitionState) automaton.getStartingState();
    }

    public Set<PartitionState> getAcceptingStates() {
        Set<PartitionState> acceptingStates = new HashSet<>();
        for(State s : automaton.getAcceptingStates()) {
            acceptingStates.add((PartitionState) s);
        }
        return acceptingStates;
    }

    public Set<PartitionState> getNonAcceptingStates() {
        Set<PartitionState> nonAcceptingStates = new HashSet<>();
        for(State s: automaton.getNonAcceptingStates()) {
            nonAcceptingStates.add((PartitionState) s);
        }
        return nonAcceptingStates;
    }

    @Override
    public String toString() {
        return automaton.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PartitionDFA && automaton.equals(((PartitionDFA) o).automaton);
    }
}

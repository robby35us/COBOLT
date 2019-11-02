package scanner.generator.thompsons;

import scanner.model.FiniteAutomaton;
import scanner.model.State;

import java.util.Set;

import static scanner.model.FiniteAutomaton.EPSILON;

public class NDFABuilder {
    private State resultStartingState;
    private State resultAcceptingState;
    private FiniteAutomaton resultFA;

    NDFABuilder() {
        resultStartingState = new State(false);
        resultAcceptingState = new State(true);
        resultFA = new FiniteAutomaton(resultStartingState, false);
        resultFA.addEndState(resultAcceptingState);
    }

    FiniteAutomaton build(Set<FiniteAutomaton> nfas) {
        for(FiniteAutomaton nfa : nfas)
            connectExistingNFA(nfa);
        resultFA.updateAcceptingStates();
        return resultFA;
    }

    private void connectExistingNFA(FiniteAutomaton nfa) {
        connectOldStart(nfa);
        for (State as : nfa.getAcceptingStates())
            connectNewEnd(as);
        addMiddleStates(nfa);
    }

    private void connectOldStart(FiniteAutomaton nfa) {
        resultStartingState.setEndState(EPSILON, nfa.getStartingState());
    }

    private void connectNewEnd(State as) {
        as.setEndState(EPSILON, resultAcceptingState);
        //as.setAcceptingState(false);
    }

    private void addMiddleStates(FiniteAutomaton nfa) {
        resultFA.addEndStates(nfa.getStates());
    }
}

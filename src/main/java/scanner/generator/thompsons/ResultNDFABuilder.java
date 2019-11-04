package scanner.generator.thompsons;

import scanner.model.automata.NDFA;
import scanner.model.state.NDFAState;
import scanner.model.state.State;

import java.util.Set;

import static scanner.model.automata.DFA.EPSILON;

class ResultNDFABuilder {
    private NDFAState resultStartingState;
    private NDFAState resultAcceptingState;
    private NDFA resultFA;

    ResultNDFABuilder() {
        resultStartingState = new NDFAState(false);
        resultAcceptingState = new NDFAState(true);
        resultFA = new NDFA(resultStartingState);
        resultFA.addEndState(resultAcceptingState);
    }

    NDFA build(Set<NDFA> ndfas) {
        for(NDFA ndfa : ndfas)
            connectExistingNFA(ndfa);
        resultFA.updateAcceptingStates();
        return resultFA;
    }

    private void connectExistingNFA(NDFA ndfa) {
        connectOldStart(ndfa);
        for (NDFAState as : ndfa.getAcceptingStates())
            connectNewEnd(as);
        addMiddleStates(ndfa);
    }

    private void connectOldStart(NDFA ndfa) {
        resultStartingState.setEndState(EPSILON, ndfa.getStartingState());
    }

    private void connectNewEnd(State as) {
        as.setEndState(EPSILON, resultAcceptingState);
        as.setAcceptingState(false); // TODO how does this affect the output?
    }

    private void addMiddleStates(NDFA ndfa) {
        resultFA.addEndStates(ndfa.getStates());
    }
}

package scanner.generator.thompsons;

import scanner.model.automata.NDFA;
import scanner.model.state.NDFAState;

import static scanner.model.automata.DFA.EPSILON;

class IntermediateNDFABuilder {
    static NDFA buildSimple(char c) {
        NDFAState startState = new NDFAState(false);
        NDFA result = new NDFA(startState);

        result.connectStatesOnChar(c, startState, new NDFAState(true));

        result.updateAcceptingStates();
        return result;
    }

    static NDFA buildAnd(
            NDFA a, NDFA b) {
        // get markerStates
        NDFAState aEnd = a.getAcceptingStates().iterator().next();
        NDFAState bStart = b.getStartingState();


        // update non-accepting states
        aEnd.setAcceptingState(false);

        NDFA result = new NDFA(a.getStartingState());
        result.addEndStates(a.getStates());
        result.connectStatesOnChar(EPSILON, aEnd, bStart);
        result.addEndStates(b.getStates());
        result.updateAcceptingStates();

        return result;
    }

    static NDFA buildOr(
            NDFA a, NDFA b) {
        NDFAState start = new NDFAState(false);
        NDFAState aStart = a.getStartingState();
        NDFAState aEnd = a.getAcceptingStates().iterator().next();
        NDFAState bStart = b.getStartingState();
        NDFAState bEnd = b.getAcceptingStates().iterator().next();
        NDFAState end = new NDFAState(true);

        aEnd.setAcceptingState(false);
        bEnd.setAcceptingState(false);

        NDFA result = new NDFA(start);
        result.connectStatesOnChar(EPSILON, start, aStart);
        result.addEndStates(a.getStates());
        result.connectStatesOnChar(EPSILON, aEnd, end);
        result.connectStatesOnChar(EPSILON, start, bStart);
        result.addEndStates(b.getStates());
        result.connectStatesOnChar(EPSILON, bEnd, end);
        result.updateAcceptingStates();

        return result;
    }

    static NDFA buildClosure(NDFA a) {
        NDFAState start = new NDFAState(false);
        NDFAState aStart = a.getStartingState();
        NDFAState aEnd = a.getAcceptingStates().iterator().next();
        NDFAState end = new NDFAState(true);

        aEnd.setAcceptingState(false);

        NDFA result = new NDFA(start);
        result.connectStatesOnChar(EPSILON, start, aStart);
        result.connectStatesOnChar(EPSILON, start, end);
        result.addEndStates(a.getStates());
        result.connectStatesOnChar(EPSILON, aEnd, end);
        result.connectStatesOnChar(EPSILON, aEnd, aStart);

        result.updateAcceptingStates();
        return result;
    }
}
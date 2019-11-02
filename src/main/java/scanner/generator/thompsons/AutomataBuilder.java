package scanner.generator.thompsons;

import scanner.model.FiniteAutomaton;
import scanner.model.State;

import static scanner.model.FiniteAutomaton.EPSILON;

class AutomataBuilder {
    static FiniteAutomaton buildSimple(char c) {
        State startState = new State(false);
        FiniteAutomaton result = new FiniteAutomaton(startState, false);

        result.connectStatesOnChar(c, startState, new State(true));

        result.updateAcceptingStates();
        return result;
    }

    static FiniteAutomaton buildAnd(
            FiniteAutomaton a, FiniteAutomaton b) {
        // get markerStates
        State aEnd = a.getAcceptingStates().iterator().next();
        State bStart = b.getStartingState();


        // update non-accepting states
        aEnd.setAcceptingState(false);

        FiniteAutomaton result = new FiniteAutomaton(a.getStartingState(), false);
        result.addEndStates(a.getStates());
        result.connectStatesOnChar(EPSILON, aEnd, bStart);
        result.addEndStates(b.getStates());
        result.updateAcceptingStates();

        return result;
    }

    static FiniteAutomaton buildOr(
            FiniteAutomaton a, FiniteAutomaton b) {
        State start = new State(false);
        State aStart = a.getStartingState();
        State aEnd = a.getAcceptingStates().iterator().next();
        State bStart = b.getStartingState();
        State bEnd = b.getAcceptingStates().iterator().next();
        State end = new State(true);

        aEnd.setAcceptingState(false);
        bEnd.setAcceptingState(false);

        FiniteAutomaton result = new FiniteAutomaton(start,false);
        result.connectStatesOnChar(EPSILON, start, aStart);
        result.addEndStates(a.getStates());
        result.connectStatesOnChar(EPSILON, aEnd, end);
        result.connectStatesOnChar(EPSILON, start, bStart);
        result.addEndStates(b.getStates());
        result.connectStatesOnChar(EPSILON, bEnd, end);
        result.updateAcceptingStates();

        return result;
    }

    static FiniteAutomaton buildClosure(FiniteAutomaton a) {
        State start = new State(false);
        State aStart = a.getStartingState();
        State aEnd = a.getAcceptingStates().iterator().next();
        State end = new State(true);

        aEnd.setAcceptingState(false);

        FiniteAutomaton result = new FiniteAutomaton(start, false);
        result.connectStatesOnChar(EPSILON, start, aStart);
        result.connectStatesOnChar(EPSILON, start, end);
        result.addEndStates(a.getStates());
        result.connectStatesOnChar(EPSILON, aEnd, end);
        result.connectStatesOnChar(EPSILON, aEnd, aStart);

        result.updateAcceptingStates();
        return result;
    }
}
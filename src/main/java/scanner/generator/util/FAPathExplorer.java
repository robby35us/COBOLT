package scanner.generator.util;

import scanner.model.FiniteAutomaton;
import scanner.model.NDFAState;
import scanner.model.State;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static scanner.model.FiniteAutomaton.EPSILON;

public class FAPathExplorer {

    private Set<Path> pathSet;
    private FiniteAutomaton fa;

    public FAPathExplorer(FiniteAutomaton fa) {
        pathSet = new TreeSet<>();
        this.fa = fa;
    }

    public Set<Path> generatePathList() {
        pathSet.clear();
        for (State s : fa.getStates())
            s.setMarked(false);
        explorePaths(fa.getStartingState(), "");
        return pathSet;
    }

    private void explorePaths(State currentState, String currentString) {
        Set<Character> outCharacters = currentState.getOutTransitionChars();
        Set<NDFAState> epsilonStates = new HashSet<>();
        if(currentState instanceof NDFAState)
            epsilonStates = ((NDFAState) currentState).getEpsilonStates();

        if(currentState.isMarked() || (outCharacters.isEmpty() && epsilonStates.isEmpty())) {
            pathSet.add(new Path(currentString));
            return;
        }

        currentState.setMarked(true);

        for (NDFAState  es : epsilonStates) {
            explorePaths(es, currentString + EPSILON);
        }
        for(char c : outCharacters) {
            explorePaths(currentState.getEndState(c), currentString + c);
        }
        currentState.setMarked(false);

    }
}

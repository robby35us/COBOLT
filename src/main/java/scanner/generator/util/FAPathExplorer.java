package scanner.generator.util;

import scanner.model.FiniteAutomaton;
import scanner.model.State;

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

    public Set<Path> generatePathList(boolean searchCyclic) {
        pathSet.clear();
        if( searchCyclic) {
            for (State s : fa.getStates()) {
                if (s != null)
                    s.setMarked(false);
            }
        }
        explorePaths(fa.getStartingState(), "", searchCyclic);
        return pathSet;
    }

    private void explorePaths(State currentState, String currentString, boolean searchCyclic) {
        Set<Character> outCharacters = currentState.getOutTransitionChars();
        Set<State> epsilonStates = currentState.getEpsilonStates();
        if ((searchCyclic && currentState.isMarked()) ||
                (outCharacters.isEmpty() && epsilonStates.isEmpty())) {
            pathSet.add(new Path(currentString));
            return;
        }
        if(currentState.isAcceptingState()) {
            pathSet.add(new Path(currentString));
        }
        if(searchCyclic)
            currentState.setMarked(true);
        for (State es : epsilonStates) {
            explorePaths(es, currentString + EPSILON, searchCyclic);
        }
        for (char c : outCharacters) {
            explorePaths(currentState.getEndState(c), currentString + c, searchCyclic);
        }
    }
}

package scanner.generator.subset;
import scanner.language.CobolCharacter;
import scanner.model.FiniteAutomaton;
import scanner.model.NDFA;
import scanner.model.NDFAState;
import scanner.model.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SubsetConstruction {

    private FiniteAutomaton dfa;
    private NDFA ndfa;
    private List<Configuration> workList;
    private List<Configuration> configs;
    private Configuration workItem;
    private Configuration newWorkItem;

    public FiniteAutomaton apply(NDFA ndfa) {
        this.ndfa = ndfa;
        initialize();
        while(!workList.isEmpty()) {
            workItem = workList.remove(0);
            processConfiguration();
        }
        dfa.updateAcceptingStates();
        FiniteAutomaton tempResult = dfa;
        cleanup();
        return tempResult;
    }

    private void initialize() {
        Set<NDFAState> stateSet = new HashSet<>();
        stateSet.add( ndfa.getStartingState());

        workList = new LinkedList<>();
        workList.add(getEpsilonClosure(stateSet));

        dfa = new FiniteAutomaton(workList.get(0).getResultingState(), true);

        configs = new ArrayList<>();
        configs.add(workList.get(0));
    }

    private void cleanup() {
        dfa = null;
        ndfa = null;
        workList = null;
        configs = null;
        workItem = null;
        newWorkItem = null;
    }

    private  void processConfiguration() {
        for(Character c : CobolCharacter.getCobolCharacterList()) {
            newWorkItem = getEClosureOFDeltaOf(c);
            if(newWorkItem == null) {
                continue;
            }
            manageTransitions(c);
            updateConfigurationLists();
        }
    }

    private  Configuration getEClosureOFDeltaOf(char c) {
        return getEpsilonClosure(getDelta(workItem, c));
    }

    private Set<NDFAState> getDelta(Configuration q, char c) {
        Set<NDFAState> delta = new HashSet<>();
        for (State state : q.getStates()) {
            if (state.getEndState(c) != null)
                delta.add((NDFAState) state.getEndState(c));
        }
        return delta;
    }


    private Configuration getEpsilonClosure(Set<NDFAState> states) {
        if (states.size() == 0) {
            return null;
        }
        Set<NDFAState> epsilonClosure = new HashSet<>(states);
        for (NDFAState s: states) {
            epsilonClosure.addAll(s.getEpsilonStates());
        }
        return new Configuration(epsilonClosure);
    }

    private  void manageTransitions(char c) {
        boolean likeTransitionFound = dfaHasLikeTransition(c);
        if (!likeTransitionFound && newWorkItem.getStates().size() != 0) {
            addTransitionToDFA(c);
        }
    }

    private  boolean dfaHasLikeTransition(char c) {
        State endState = workItem.getResultingState().getEndState(c);
        return endState != null;
    }

    private  void addTransitionToDFA(char c) {
        dfa.connectStatesOnChar(c, workItem.getResultingState(),
                newWorkItem.getResultingState());
    }


    private  void updateConfigurationLists() {
        if (!configs.contains(newWorkItem) && newWorkItem.getStates().size() != 0) {
            workList.add(newWorkItem);
            configs.add(newWorkItem);
        }
    }
}
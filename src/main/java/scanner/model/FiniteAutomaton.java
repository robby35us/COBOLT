package scanner.model;

import scanner.generator.subset.Configuration;
import scanner.generator.util.FAPathExplorer;
import scanner.generator.util.Path;

import java.util.HashSet;
import java.util.Set;

public class FiniteAutomaton {

    public static final char EPSILON = '~';

    private boolean deterministic;
    private State startingState;
    private Set<State> states;
    private Set<State> acceptingStates;
    private Set<Path> pathList;

    public FiniteAutomaton(State startingState, boolean deterministic) {
        pathList = new HashSet<>();
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

    public void addEndState(State endState) {
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

    public Set<State> getDelta(Configuration q, char c) {
        Set<State> delta = new HashSet<>();
        for (State state : q.getStates()) {
            if (state.getEndState(c) != null)
                delta.add(state.getEndState(c));
        }
        return delta;
    }


    public Configuration getEpsilonClosure(Set<State> states) {
        if (states.size() == 0) {
            return null;
        }
        Set<State> epsilonClosure = new HashSet<>(states);
        for (State s : states) {
            epsilonClosure.addAll(s.getEpsilonStates());
        }
        return new Configuration(epsilonClosure);
    }

    @Override
    public String toString() {
        return new FAPathExplorer(this).generatePathList(deterministic).toString();
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof FiniteAutomaton) {
          for(Path p: ((FiniteAutomaton) o).pathList) {
              if(!this.pathList.contains(p)) {
                  return false;
              }
          }
          return true;
      }
      return false;
        //  return o instanceof FiniteAutomaton
       //         && this.generatePathList().containsAll(((FiniteAutomaton) o).generatePathList());
    }
}
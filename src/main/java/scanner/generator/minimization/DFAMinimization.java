package scanner.generator.minimization;

import scanner.language.CobolCharacter;
import scanner.model.automata.DFA;
import scanner.model.automata.PartitionDFA;
import scanner.model.state.PartitionState;
import scanner.model.state.State;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DFAMinimization {
    private  Set<Partition> tempPSet;
    private  Set<Partition> partitionSet;
    private  Set<Partition> finalPSet;

    public DFA apply(PartitionDFA dfa) {
        initializeTempSet(dfa);
        while(!tempPSet.equals(partitionSet)) {
            partitionSet = tempPSet;
            tempPSet = new HashSet<>();
            for(Partition p : partitionSet) {
                Set<Partition> resultOfSplit = split(p);
                tempPSet.addAll(resultOfSplit);
            }
        }
        finalPSet = tempPSet;
        int sum = 0;
        for(Partition p : finalPSet) {
            sum += p.getStates().size();
        }
        System.out.println(sum);
        return buildResultingDFA(dfa);
    }

    private void initializeTempSet(PartitionDFA dfa) {

        tempPSet = new HashSet<>();
        tempPSet.add(new Partition(dfa.getNonAcceptingStates()));
        tempPSet.add(new Partition(dfa.getAcceptingStates()));

        partitionSet = null;
        finalPSet = new HashSet<>();
    }


    private Set<Partition> split(Partition p) {
        Set<Partition> splitOnC;
        for(char c : CobolCharacter.getCobolCharacterList()) {
            splitOnC = getSplitOnChar(c, p);
            if(splitOnC.size() > 1) {
                return splitOnC;
            }
        }
        splitOnC = new HashSet<>();
        splitOnC.add(p);
        for(PartitionState s : p.getStates()) {
            s.setPartition(p);
        }
        return splitOnC;
    }

    private  Set<Partition> getSplitOnChar(char c, Partition p) {
        Partition primaryPartition = null;
        Set<PartitionState> primaryPartitionStates = new HashSet<>();
        Set<PartitionState> otherPartitionStates = new HashSet<>();
        Set<PartitionState> remainderStates = new HashSet<>();

        for(PartitionState curState : p.getStates()) {
            PartitionState outState = (PartitionState) curState.getEndState(c);
            if(outState == null) {
                remainderStates.add(curState);
                continue;
            }

            if (primaryPartition == null) {
                primaryPartition = outState.getPartition();
            }

            if (outState.getPartition() == primaryPartition) {
                primaryPartitionStates.add(curState);
            } else {
                otherPartitionStates.add(curState);
            }

        }
        if(!remainderStates.isEmpty()) {
            otherPartitionStates.addAll(remainderStates);
        }

        Set<Partition> newPartitions = new HashSet<>();
        if(!primaryPartitionStates.isEmpty()) {
            newPartitions.add(new Partition(primaryPartitionStates));
        }
        if(!otherPartitionStates.isEmpty()) {
            newPartitions.add(new Partition(otherPartitionStates));
        }
        return newPartitions;
    }

    private DFA buildResultingDFA(PartitionDFA partitionDFA) {
        Map<Partition, State> resultingDFAStates = new HashMap<>();
        Partition startingPartition = partitionDFA.getStartingState().getPartition();

        State resultingStartingState = null;
        for (Partition p : finalPSet) {
            resultingDFAStates.put(p, new State(p.containsAcceptingState()));
            if(p == startingPartition)
                resultingStartingState = resultingDFAStates.get(p);
        }

        DFA resultingDFA = new DFA(resultingStartingState);
        for (Partition p : finalPSet) {
            State newState = resultingDFAStates.get(p);
            for (PartitionState s : p.getStates()) {
                Set<Character> outTransitionChars = s.getOutTransitionChars();
                for(char outChar : outTransitionChars) {
                    resultingDFA.connectStatesOnChar( outChar, newState,
                            resultingDFAStates.get(((PartitionState) s.getEndState(outChar)).getPartition()));
                }
            }
        }
        return resultingDFA;
    }
}
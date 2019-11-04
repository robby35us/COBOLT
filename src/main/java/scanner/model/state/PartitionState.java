package scanner.model.state;

import scanner.generator.minimization.Partition;
import scanner.model.state.State;

public class PartitionState extends State {
    // for the minimization algorithm
    private Partition partition;

    public PartitionState(boolean acceptingState) {
        super(acceptingState);
    }

    public void setPartition(Partition p) {
        partition = p;
    }

    public Partition getPartition() {
        return partition;
    }
}

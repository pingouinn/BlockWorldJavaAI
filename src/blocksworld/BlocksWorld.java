package blocksworld;

import java.util.Set;
import java.util.HashSet;

import modelling.Constraint;
import modelling.DifferenceConstraint;
import modelling.Implication;
import modelling.Variable;

public class BlocksWorld {
    
    private BWData data;
    private Set<Constraint> constraints;

    public BlocksWorld(int blocksAmount, int stackAmount) {
        this.data = new BWData(blocksAmount, stackAmount);
        this.constraints = new HashSet<>();

        createBasicConstraint();
    }

    public BWData getData() {
        return this.data;
    }

    public Set<Constraint> getConstraints() {
        return this.constraints;
    }

    private void createBasicConstraint() {
        Variable[] on = this.data.getOnArray();
        Variable[] fixed = this.data.getFixedArray();
        Variable[] free = this.data.getFreeArray();

        for (int b = 0; b < this.data.getBlocksAmount(); b++) {
            for (int bPrime = 0; bPrime < this.data.getBlocksAmount(); bPrime++) {
                if (b == bPrime) continue;//don't create constraint for the same block

                if (b < bPrime)// check for b and bPrime is the same as bPrime and b
                    this.constraints.add(new DifferenceConstraint(on[b], on[bPrime])); //createNotSameConstraint

                Set<Object> isOnBPrime = Set.of(bPrime);
                Set<Object> isFixed = Set.of(true);
                this.constraints.add(new Implication(on[b], isOnBPrime, fixed[bPrime], isFixed)); //createIsFixedConstraints

            }

            for (int p = 0; p < this.data.getStackAmount(); p++) {
                Set<Object> isOnP = Set.of(-(p + 1));
                Set<Object> isNotFree = Set.of(false);
                this.constraints.add(new Implication(on[b], isOnP, free[p], isNotFree)); //createIsFreeConstraints
            }
        }
    }
}

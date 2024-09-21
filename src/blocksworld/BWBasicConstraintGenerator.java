package blocksworld;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import modelling.Constraint;
import modelling.DifferenceConstraint;
import modelling.Implication;
import modelling.Variable;

public class BWBasicConstraintGenerator {
    
    private BWData data;
    private Set<Constraint> constraints;

    
    public BWBasicConstraintGenerator(int blocksAmount, int stackAmount) {
        this.data = new BWData(blocksAmount, stackAmount);
        this.constraints = new HashSet<>();

        createBasicConstraint();
    }

    public Set<Constraint> getConstraints() {
        return this.constraints;
    }

    private void createBasicConstraint() {
        List<Variable> on = new ArrayList<>(this.data.getOn());
        List<Variable> fixed = new ArrayList<>(this.data.getFixed());
        List<Variable> free = new ArrayList<>(this.data.getFree());

        for (int b = 0; b < this.data.getBlocksAmount(); b++) {
            for (int bPrime = 0; bPrime < this.data.getBlocksAmount(); bPrime++) {
                if (b == bPrime) continue;//don't create constraint for the same block

                if (b < bPrime)// check for b and bPrime is the same as bPrime and b
                    this.constraints.add(new DifferenceConstraint(on.get(b), on.get(bPrime))); //createNotSameConstraint

                Set<Object> isOnBPrime = new HashSet<>();
                isOnBPrime.add(fixed.get(bPrime));
                Set<Object> isFixed = new HashSet<>();
                isFixed.add(true);
                this.constraints.add(new Implication(on.get(b), isOnBPrime, fixed.get(bPrime), isFixed)); //createIsFixedConstraints
                
            }

            for (int p = -(this.data.getStackAmount()); p < 0; p++) {
                Set<Object> isOnP = new HashSet<>();
                isOnP.add(p);
                Set<Object> isFree = new HashSet<>();
                isFree.add(false);
                this.constraints.add(new Implication(on.get(b), isOnP, free.get(p), isFree)); //createIsFreeConstraints
            }
        }
    }
}

package blocksworld;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import modelling.Constraint;
import modelling.DifferenceConstraint;
import modelling.Implication;
import modelling.Variable;

//TODO: EX2
public class BWBasicConstraintGenerator {
    
    private BWData data;
    private Set<Constraint> constraints;

    
    public BWBasicConstraintGenerator(int blocksAmount, int stackAmount) {
        this.data = new BWData(blocksAmount, stackAmount);
        this.constraints = new HashSet<>();

        createNotSameConstraint();
        createIsFixedConstraints();
        createIsFreeConstraints();

    }

    public Set<Constraint> getConstraints() {
        return this.constraints;
    }

    private void createNotSameConstraint() {
        List<Variable> on = new ArrayList<>(this.data.getOn());
        for (int i = 0; i < this.data.getBlocksAmount(); i++) {
            for (int j = i + 1; j < this.data.getBlocksAmount(); j++) {
                this.constraints.add(new DifferenceConstraint(on.get(i), on.get(j)));
            }
        }
    }

    private void createIsFixedConstraints() {
        List<Variable> on = new ArrayList<>(this.data.getOn());
        List<Variable> fixed = new ArrayList<>(this.data.getFixed());
        for (int i = 0; i < this.data.getBlocksAmount(); i++) {
            for (int j = 0; j < this.data.getBlocksAmount(); j++) {
                if (i == j) continue;
                Set<Object> isOnbPrime = new HashSet<>();
                isOnbPrime.add(fixed.get(j));

                Set<Object> isFixed = new HashSet<>();
                isFixed.add(true);
                this.constraints.add(new Implication(on.get(i), isOnbPrime, fixed.get(j), isFixed));
            }
        }
    }

    private void createIsFreeConstraints() {
        List<Variable> on = new ArrayList<>(this.data.getOn());
        List<Variable> free = new ArrayList<>(this.data.getFree());
        for (int i = 0; i < this.data.getBlocksAmount(); i++) {
            for (int j = -(this.data.getStackAmount()); j < 0; j++) {
                Set<Object> isOnStack = new HashSet<>();
                isOnStack.add(j);
                Set<Object> isFree = new HashSet<>();
                isFree.add(false);
                this.constraints.add(new Implication(on.get(i), isOnStack, free.get(i), isFree));
            }
        }
    }
}

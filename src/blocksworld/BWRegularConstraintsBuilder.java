package blocksworld;

import java.util.Set;
import java.util.HashSet;

import modelling.Constraint;
import modelling.Implication;
import modelling.Variable;

public class BWRegularConstraintsBuilder {
    private BWData data;
    private Set<Constraint> constraints;

    public BWRegularConstraintsBuilder(BlocksWorld bw) {
        this.data = bw.getData();
        this.constraints = bw.getConstraints();
    }

    public void build() {
        this.createRegularConstraint();
    }

    public Set<Constraint> getConstraints() {
        return this.constraints;
    }

    public void createRegularConstraint() {
        Variable[] on = this.data.getOnArray();

        for (int b = 0; b < this.data.getBlocksAmount(); b++) {
            for (int bPrime = 0; bPrime < this.data.getBlocksAmount(); bPrime++) {
                if (b == bPrime) continue;

                Set<Object> isOnbPrime = Set.of(bPrime);

                Set<Object> isOnOffset = new HashSet<>();
                for (int i = -(this.data.getStackAmount()); i < 0; i++)
                    isOnOffset.add(i);
                
                
                int offset = bPrime - (b - bPrime);

                if (offset >= 0 && offset < this.data.getBlocksAmount())
                    isOnOffset.add(offset);


                this.constraints.add(new Implication(on[b], isOnbPrime, on[bPrime], isOnOffset));

            }
        }
    }
}

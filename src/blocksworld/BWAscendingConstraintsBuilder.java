package blocksworld;

import java.util.Set;
import java.util.HashSet;

import modelling.Constraint;
import modelling.UnaryConstraint;
import modelling.Variable;

public class BWAscendingConstraintsBuilder {
    
    private BWData data;
    private Set<Constraint> constraints;

    
    public BWAscendingConstraintsBuilder(BlocksWorld bw) {
        this.data = bw.getData();
        this.constraints = bw.getConstraints();

        createAscendingConstraint();
    }

    public Set<Constraint> getConstraints() {
        return this.constraints;
    }

    public void createAscendingConstraint() {
        Variable[] on = this.data.getOn().toArray(Variable[]::new);

        for (int b = 0; b < this.data.getBlocksAmount(); b++) {
            Set<Object> isOnBigger = new HashSet<>();

            for (int i = -(this.data.getStackAmount()); i < b; i++)
                isOnBigger.add(i);
            
            this.constraints.add(new UnaryConstraint(on[b], isOnBigger));
        }
    }
}

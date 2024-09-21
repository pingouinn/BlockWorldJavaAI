package modelling;

import java.util.Map;
import java.util.Set;

public class UnaryConstraint implements Constraint {
    private Variable var;
    private Set<Object> values;

    public UnaryConstraint(Variable var, Set<Object> values) {
        this.var = var;
        this.values = values;
    }

    @Override
    public Set<Variable> getScope() {
        return Set.of(var);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciations) {
        if (!instanciations.containsKey(var)) {
            throw new IllegalArgumentException("Variable not in instanciations");
        }
        return values.contains(instanciations.get(var));
        
    }
    
}

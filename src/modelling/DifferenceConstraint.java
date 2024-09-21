package modelling;

import java.util.Map;
import java.util.Set;

public class DifferenceConstraint implements Constraint {
    private Variable var1;
    private Variable var2;

    public DifferenceConstraint(Variable var1, Variable var2) {
        this.var1 = var1;
        this.var2 = var2;
    }

    @Override
    public Set<Variable> getScope() {
        if (var1.equals(var2))
            return Set.of(var1);
        return Set.of(var1, var2);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciations) {
        if (!instanciations.containsKey(var1) || !instanciations.containsKey(var2)) {
            throw new IllegalArgumentException("Variables not in instanciations");
        }
        return !instanciations.get(var1).equals(instanciations.get(var2));
    }
}

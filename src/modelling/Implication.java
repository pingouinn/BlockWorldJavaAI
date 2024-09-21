package modelling;

import java.util.Map;
import java.util.Set;

public class Implication implements Constraint {
    
    private Variable var1;
    private Set<Object> s1;
    private Variable var2;
    private Set<Object> s2;

    public Implication(Variable var1, Set<Object> s1, Variable var2, Set<Object> s2) {
        this.var1 = var1;
        this.s1 = s1;
        this.var2 = var2;
        this.s2 = s2;
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
        return !s1.contains(instanciations.get(var1)) || s2.contains(instanciations.get(var2));
    }

}

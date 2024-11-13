package cp;

import java.util.Set;
import java.util.Map;

import modelling.Variable;
import modelling.Constraint;

public abstract class AbstractSolver implements Solver {
    protected Set<Variable> variables;
    protected Set<Constraint> constraints;
    
    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    public boolean isConsistent(Map<Variable, Object> state) {
        Set<Variable> stateVariables = state.keySet();
        for (Constraint c : constraints) {
            if (stateVariables.containsAll(c.getScope())) {
                if (!c.isSatisfiedBy(state))
                    return false;
            }
        }
        return true;
    }
}

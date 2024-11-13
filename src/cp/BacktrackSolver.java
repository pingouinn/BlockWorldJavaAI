package cp;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import modelling.Variable;
import modelling.Constraint;

public class BacktrackSolver extends AbstractSolver {
    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    @Override
    public Map<Variable, Object> solve() {
        return backtrack(new HashMap<Variable, Object>(), new HashSet<Variable>(variables));
    }

    private Map<Variable, Object> backtrack(Map<Variable, Object> partialSolution, Set<Variable> unassignedVariables) {
        if (unassignedVariables.isEmpty())
            return partialSolution;
        Variable var = unassignedVariables.iterator().next();
        unassignedVariables.remove(var);
        for (Object value : var.getDomain()) {
            partialSolution.put(var, value); // avoid to copy partialSolution
            if (isConsistent(partialSolution)) {
                Map<Variable, Object> result = backtrack(partialSolution, unassignedVariables);
                if (result != null)
                    return result;
            }
            partialSolution.remove(var);// avoid to copy partialSolution
        }
        unassignedVariables.add(var);
        return null;
    }
}

package cp;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;


import modelling.Constraint;
import modelling.Variable;

public class MACSolver extends AbstractSolver {
    ArcConsistency ac;
    
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
        this.ac = new ArcConsistency(constraints);
    }

    @Override
    public Map<Variable, Object> solve() {
        HashMap<Variable, Set<Object>> domains = new HashMap<>();
        for (Variable v : variables)
            domains.put(v, v.getDomain());
        return mac(new HashMap<Variable, Object>(), new HashSet<Variable>(variables), domains);
    }

    private Map<Variable, Object> mac(Map<Variable, Object> partialSolution, Set<Variable> unassignedVariables, Map<Variable, Set<Object>> domains) {
        if (unassignedVariables.isEmpty())
            return partialSolution;
        if (!this.ac.ac1(domains))
            return null;
        Variable var = unassignedVariables.iterator().next();
        unassignedVariables.remove(var);
        for (Object value : domains.get(var)) {
            partialSolution.put(var, value);
            if (isConsistent(partialSolution)) {
                Map<Variable, Object> result = mac(partialSolution, unassignedVariables, domains);
                if (result != null)
                    return result;
            }
            partialSolution.remove(var);
        }
        unassignedVariables.add(var);
        return null;
    }
}

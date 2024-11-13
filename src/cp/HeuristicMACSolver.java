package cp;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import modelling.Constraint;
import modelling.Variable;

public class HeuristicMACSolver extends AbstractSolver {

    private ArcConsistency ac;
    private VariableHeuristic varHeuristic;
    private ValueHeuristic valHeuristic;
    
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic varHeuristic, ValueHeuristic valHeuristic) {
        super(variables, constraints);
        this.varHeuristic = varHeuristic;
        this.valHeuristic = valHeuristic;
        this.ac = new ArcConsistency(constraints);
    }


    @Override
    public Map<Variable, Object> solve() {
        HashMap<Variable, Set<Object>> domains = new HashMap<>();
        for (Variable v : variables)
            domains.put(v, v.getDomain());
        return macHeuristic(new HashMap<Variable, Object>(), new HashSet<Variable>(variables), domains);
    }

    private Map<Variable, Object> macHeuristic(Map<Variable, Object> partialSolution, Set<Variable> unassignedVariables, Map<Variable, Set<Object>> domains) {
        if (unassignedVariables.isEmpty())
            return partialSolution;
        if (!this.ac.ac1(domains))
            return null;
        Variable var = varHeuristic.best(unassignedVariables, domains);
        unassignedVariables.remove(var);
        for (Object value : valHeuristic.ordering(var, domains.get(var))) {
            partialSolution.put(var, value);
            if (isConsistent(partialSolution)) {
                Map<Variable, Object> result = macHeuristic(partialSolution, unassignedVariables, domains);
                if (result != null)
                    return result;
            }
            partialSolution.remove(var);
        }
        unassignedVariables.add(var);
        return null;
    }
}

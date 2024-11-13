package cp;

import java.util.Set;
import java.util.Map;

import modelling.Variable;
import modelling.Constraint;


public class NbConstraintsVariableHeuristic implements VariableHeuristic {

    private Set<Constraint> constraints;
    private boolean useMost;

    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean most) {
        this.constraints = constraints;
        this.useMost = most;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Variable best = null;
        int bestCount = -1;
        for (Variable v : variables) {
            int count = (int) this.constraints.parallelStream()
                .filter(c -> c.getScope().contains(v))
                .count();
            if (this.useMost) {
                if (count > bestCount) {
                    best = v;
                    bestCount = count;
                }
            } else {
                if (count < bestCount || bestCount == -1) {
                    best = v;
                    bestCount = count;
                }
            }
        }
        return best;
    }

}

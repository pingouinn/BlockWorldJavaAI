package cp;

import java.util.Set;
import java.util.Map;

import modelling.Variable;

public class DomainSizeVariableHeuristic implements VariableHeuristic{
    boolean useGreatest;

    public DomainSizeVariableHeuristic(boolean greatest) {
        this.useGreatest = greatest;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Variable best = null;
        int bestSize = -1;
        for (Variable v : variables) {
            int size = domains.get(v).size();
            if (this.useGreatest) {
                if (size > bestSize) {
                    best = v;
                    bestSize = size;
                }
            } else {
                if (size < bestSize || bestSize == -1) {
                    best = v;
                    bestSize = size;
                }
            }
        }
        return best;
    }
}

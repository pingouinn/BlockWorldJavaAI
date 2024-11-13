package cp;

import java.util.Set;
import java.util.Map;

import modelling.Variable;

public interface VariableHeuristic {
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains);
}

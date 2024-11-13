package cp;

import java.util.Set;
import java.util.List;

import modelling.Variable;

public interface ValueHeuristic {
    public List<Object> ordering(Variable vatiable, Set<Object> domain);
}

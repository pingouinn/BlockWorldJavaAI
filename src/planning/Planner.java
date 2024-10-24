package planning;

import java.util.List;
import java.util.Map;
import java.util.Set;

import modelling.Variable;

public interface Planner {

    List<Action> plan();

    Map<Variable, Object> getInitialState();

    Set<Action> getActions();

    Goal getGoal();

    int getNodeCount();

    void activateNodeCount(boolean active);
}

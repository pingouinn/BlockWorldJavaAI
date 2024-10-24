package planning;

import java.util.Map;
import java.util.HashMap;

import modelling.Variable;


public class BasicAction implements Action{

    private Map<Variable, Object> preconditions;
    private Map<Variable, Object> effects;
    private int cost;
    
    public BasicAction(Map<Variable, Object> preconditions, Map<Variable, Object> effects, int cost) {
        this.preconditions = preconditions;
        this.effects = effects;
        this.cost = cost;
    }

    @Override
    public boolean isApplicable(Map<Variable, Object> state) {
        for (Variable var : preconditions.keySet()) {
            if (!state.containsKey(var) || !state.get(var).equals(preconditions.get(var))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> state) {
        Map<Variable, Object> newState = new HashMap<>(state);
        for (Variable var : effects.keySet()) {
            newState.put(var, effects.get(var));
        }
        return newState;
    }

    @Override
    public int getCost() {
        return cost;
    }
}

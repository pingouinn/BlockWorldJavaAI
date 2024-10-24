package planning;

import java.util.Map;

import modelling.Variable;

public class BasicGoal implements Goal {
    private Map<Variable, Object> instanciationPartielle;

    public BasicGoal(Map<Variable, Object> instanciationPartielle) {
        this.instanciationPartielle = instanciationPartielle;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> state) {
        for (Variable var : instanciationPartielle.keySet()) {
            if (!state.containsKey(var) || !state.get(var).equals(instanciationPartielle.get(var))) {
                return false;
            }
        }
        return true;
    }
    
}

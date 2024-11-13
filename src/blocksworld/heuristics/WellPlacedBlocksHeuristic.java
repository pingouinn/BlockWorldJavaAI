package blocksworld.heuristics;

import java.util.Map;

import modelling.Variable;
import planning.Heuristic;


public class WellPlacedBlocksHeuristic implements Heuristic {
    private Map<Variable, Object> goalState;
    
    public WellPlacedBlocksHeuristic (Map<Variable, Object> goalState) {
        this.goalState = goalState;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        float cost = .0f;
        for (Variable var : goalState.keySet()) {
            if (state.get(var) != goalState.get(var)) {
                cost += 1.0f;
            }
        }
        return cost;
    }
}

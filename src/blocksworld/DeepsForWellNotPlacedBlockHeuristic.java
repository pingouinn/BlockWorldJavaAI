package blocksworld;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import planning.Heuristic;
import modelling.Variable;

public class DeepsForWellNotPlacedBlockHeuristic implements Heuristic {
    private Map<Variable, Object> goalState;

    public DeepsForWellNotPlacedBlockHeuristic(Map<Variable, Object> goalState) {
        this.goalState = goalState;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        Map<Integer, Integer> deeps = getDeeps();
        float cost = .0f;
        for (Variable var : goalState.keySet()) {
            String varName = var.getName();
            if (varName.contains("on")) {
                String[] parts = varName.split("_");
                int block = Integer.parseInt(parts[1]);
                if (state.get(var) != goalState.get(var))
                    cost += deeps.get(block);
            }
        }
        return cost;
    }

    private Map<Integer, Integer> getDeeps() {
        Map<Integer, Integer> deeps = new HashMap<>();
        goalState.forEach((var, value) -> {
            String varName = var.getName();
            int intValue = (int) value;
            if (varName.contains("on")) {
                String[] parts = varName.split("_");
                int block = Integer.parseInt(parts[1]);
                deeps.putIfAbsent(block, 0);
                if (intValue >= 0) {
                    updateDepths(deeps, intValue);
                }
            }
        });

        return deeps;
    }
    

    private void updateDepths(Map<Integer, Integer> deeps, int block) {
        deeps.put(block, deeps.get(block) + 1);
        goalState.forEach((var, value) -> {
            String varName = var.getName();
            int intValue = (int) value;
            if (varName.contains("on")) {
                String[] parts = varName.split("_");
                int subBlock = Integer.parseInt(parts[1]);
                if (subBlock == block && intValue >= 0) {
                    updateDepths(deeps, intValue);
                }
            }
        });
    }
}

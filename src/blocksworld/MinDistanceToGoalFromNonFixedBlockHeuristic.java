package blocksworld;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import modelling.Variable;
import planning.Heuristic;


public class MinDistanceToGoalFromNonFixedBlockHeuristic implements Heuristic {

    private List<Integer> onBlockGoal;
    private int numCols;

    public MinDistanceToGoalFromNonFixedBlockHeuristic(Map<Variable, Object> goalState, int numCols) {
        this.onBlockGoal = new ArrayList<>();
        this.numCols = numCols;

        // Extracting goal state
        for (Map.Entry<Variable, Object> entry : goalState.entrySet()) {
            Object value = entry.getValue();
            String varName = entry.getKey().getName();
            if (varName.contains("on")) {
                String[] parts = varName.split("_");
                this.onBlockGoal.add(Integer.parseInt(parts[1]), value instanceof Integer ? (Integer) value : null);
            }
        }
    }

    public Map<Integer, int[]> createCoords(Map<Variable, Object> state) {
        Map<Integer, int[]> blockPositions = new HashMap<>();
        for (Map.Entry<Variable, Object> entry : state.entrySet()) {
            Object value = entry.getValue();
            String varName = entry.getKey().getName();
            if (varName.contains("on")) {
                String[] parts = varName.split("_");
                int block = Integer.parseInt(parts[1]);
                int belowBlock = value instanceof Integer ? (Integer) value : -1;
                if (belowBlock == -1) {
                    // Block is on the stack
                    blockPositions.put(block, new int[]{block % numCols, block / numCols});
                } else {
                    // Block is on another block
                    int[] belowBlockPos = blockPositions.get(belowBlock);
                    blockPositions.put(block, new int[]{belowBlockPos[0], belowBlockPos[1] + 1});
                }
            }
        }
        return blockPositions;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        float cost = .0f;
        // Converting block positions to coords
        Map<Integer, int[]> blockPositions = createCoords(state);
        for (int i = 0; i < onBlockGoal.size(); i++) {
            if (onBlockGoal.get(i) != null) {
                int[] goalPos = new int[]{onBlockGoal.get(i) % numCols, onBlockGoal.get(i) / numCols};
                int[] blockPos = blockPositions.get(i);
                cost += Math.abs(goalPos[0] - blockPos[0]) + Math.abs(goalPos[1] - blockPos[1]);
            }
        }

        return cost;
    }
}

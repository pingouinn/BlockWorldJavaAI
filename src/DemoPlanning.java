import java.util.HashMap;
import java.util.Map;
import java.util.List;

import modelling.Variable;
import planning.BasicGoal;
import planning.Planner;
import planning.DFSPlanner;
import planning.BFSPlanner;
import planning.DijkstraPlanner;
import planning.AStarPlanner;
import planning.Action;
import blocksworld.BlocksWorld;
import blocksworld.BWActionsBuilder;
import blocksworld.DisplayBW;
import blocksworld.heuristics.WellPlacedBlocksHeuristic;
import blocksworld.heuristics.DeepsForNotWellPlacedBlockHeuristic;


public class DemoPlanning {
    public static void main(String[] args) {
        BlocksWorld bw = new BlocksWorld(4, 5);

        Map<Variable, Object> state = new HashMap<>();
        Variable[] on = bw.getData().getOnArray();
        Variable[] fixed = bw.getData().getFixedArray();
        Variable[] free = bw.getData().getFreeArray();

        // Here the blocks are stacked on a single stack, the stack 1 to 4 are free, the blocks 0 to 2 are fixed and only the top one (block 3) is okay to moove
        state.put(on[0], -1);
        state.put(on[1], 0);
        state.put(on[2], 1);
        state.put(on[3], 2);

        state.put(fixed[0], true);
        state.put(fixed[1], true);
        state.put(fixed[2], true);
        state.put(fixed[3], false);

        state.put(free[0], false);
        state.put(free[1], true);
        state.put(free[2], true);
        state.put(free[3], true);
        state.put(free[4], true);

        // GoalState
        // For the final goal, we want to have the blocks in the same order on the stack 2
        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(on[0], -2);
        goalState.put(on[1], 0);
        goalState.put(on[2], 1);
        goalState.put(on[3], 2);
        
        // Creating the basic goal 
        BasicGoal goal = new BasicGoal(goalState);

        // Creating the planner
        BWActionsBuilder actionBuilder = new BWActionsBuilder(bw.getData());
        actionBuilder.build();
        Planner[] planners = new Planner[5];

        planners[4] = new DFSPlanner(state, actionBuilder.getActions(), goal);
        planners[3] = new BFSPlanner(state, actionBuilder.getActions(), goal);
        planners[2] = new DijkstraPlanner(state, actionBuilder.getActions(), goal);
        planners[1] = new AStarPlanner(state, actionBuilder.getActions(), goal, new WellPlacedBlocksHeuristic(goalState));
        planners[0] = new AStarPlanner(state, actionBuilder.getActions(), goal, new DeepsForNotWellPlacedBlockHeuristic(goalState));

        // Calling the GUI 
        DisplayBW[] displays = new DisplayBW[5];
        int x = 20;
        int y = 20;
        for (int i = 0; i < 5; i++) {
            if (x > 1500) {
                x = 500;
                y += 500;
            }
            displays[i] = new DisplayBW(planners[i].getClass().getName(), bw.getData(), x, y, state);
            x += 620;
        }

        for (int i = 0; i < 5; i++) {
            DisplayBW display = displays[i];
            long startTime = System.currentTimeMillis();
            List<Action> goalAction = planners[i].plan();
            long endTime = System.currentTimeMillis();
            System.out.println("Time for " + planners[i].getClass().getName() + ": " + (endTime - startTime) + "ms");
            for (Action action : goalAction) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                state = action.successor(state);
                display.next(state);
            }
            System.out.println("Block 0 on stack : " + state.get(on[0]));
        }
    }
}

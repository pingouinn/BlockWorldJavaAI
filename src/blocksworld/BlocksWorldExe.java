package blocksworld;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import modelling.Variable;
import planning.BasicGoal;
import planning.DFSPlanner;
import planning.Planner;
import planning.BFSPlanner;
import planning.DijkstraPlanner;
import planning.AStarPlanner;
import blocksworld.WellPlacedBlocksHeuristic;
import planning.Action;

public class BlocksWorldExe {
    public static void main(String[] args) {
        BlocksWorld bw = new BlocksWorld(4, 5);

        Map<Variable, Object> state = new HashMap<>();
        Variable[] on = bw.getData().getOnArray();
        Variable[] fixed = bw.getData().getFixedArray();
        Variable[] free = bw.getData().getFreeArray();

        // Here the blocks are stacked on a single stack, the stack 1 and 2 are free, the blocks 0 and 1 are fixed and only the top one (block 2) is okay to moove
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
        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(on[0], -2);
        goalState.put(on[1], 0);
        goalState.put(on[2], 1);
        goalState.put(on[3], 2);

        // Creating constraints
        new BWRegularConstraintsBuilder(bw).build();
        new BWActionsBuilder(bw.getData()).build();
        
        // Creating the basic goal 
        BasicGoal goal = new BasicGoal(goalState);

        // Creating the planner
        BWActionsBuilder actionBuilder = new BWActionsBuilder(bw.getData());
        actionBuilder.build();

        Planner planner = new AStarPlanner(state, actionBuilder.getActions(), goal, new WellPlacedBlocksHeuristic(goalState));
        List<Action> goalAction = planner.plan();
        System.out.println(goalAction);

        // Calling the GUI 
        DisplayBW display = new DisplayBW("Blocks World", bw.getData(), 600, 350, state);

        for (Action action : goalAction) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("next");
            state = action.successor(state);
            try {
                display.next(state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done");
    }
}

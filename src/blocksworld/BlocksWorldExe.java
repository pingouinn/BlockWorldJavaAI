package blocksworld;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import modelling.Variable;
import planning.BasicGoal;
import planning.DFSPlanner;
import planning.Action;

public class BlocksWorldExe {

    public static void main(String[] args) {
        BlocksWorld bw = new BlocksWorld(3, 3);
        
        Map<Variable, Object> state = new HashMap<>();
        Variable[] on = bw.getData().getOn().toArray(Variable[]::new);
        Variable[] fixed = bw.getData().getFixed().toArray(Variable[]::new);
        Variable[] free = bw.getData().getFree().toArray(Variable[]::new);

        // Here the blocks are stacked on a single stack, the stack 1 and 2 are free, the blocks 0 and 1 are fixed and only the top one (block 2) is okay to moove
        state.put(on[0], -1);
        state.put(on[1], 0);
        state.put(on[2], 1);
        state.put(fixed[0], true);
        state.put(fixed[1], true);
        state.put(fixed[2], false);
        state.put(free[0], false);
        state.put(free[1], true);
        state.put(free[2], true);

        // GoalState
        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(on[0], -1);
        goalState.put(on[1], 0);
        goalState.put(on[2], -3);

        // Creating constraints
        new BWRegularConstraintsBuilder(bw).build();
        
        // Creating the basic goal 
        BasicGoal goal = new BasicGoal(goalState);

        // Creating the planner (BFSPlanner)
        BWActionsBuilder actionBuilder = new BWActionsBuilder(bw.getData());
        actionBuilder.build();
        //System.out.println(actionBuilder.getActions());
        DFSPlanner planner = new DFSPlanner(state, actionBuilder.getActions(), goal);
        List<Action> goalAction = planner.plan();

        // Calling the GUI 
        DisplayBW display = new DisplayBW("Blocks World", bw.getData(), 600, 350);
        display.display(state);

        for (Action action : goalAction) {
            state = action.successor(state);
            display.display(state);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

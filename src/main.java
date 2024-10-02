import blocksworld.*;
import modelling.Variable;
import modelling.Constraint;

import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {

        // Create a blockworld and test the bas constraints on the initialisation process
        BlocksWorld bw = new BlocksWorld(3, 3);
        System.out.println("Block amount "+bw.getData().getBlocksAmount());
        System.out.println("Stack amount "+bw.getData().getStackAmount());
        System.out.println("Constraints amount "+bw.getConstraints().size());

        Map<Variable, Object> state = new HashMap<Variable, Object>();
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

        System.out.println("Assert that in a viable world the base constraints are working  :");
        System.out.println(bw.getConstraints().stream().allMatch(c -> c.isSatisfiedBy(state)));

        // Test the regular constraints
        new BWRegularConstraintsBuilder(bw);
        System.out.println("New constraints size after adding regular constraints : " + bw.getConstraints().size());

        // Test the ascending constraints
        new BWAscendingConstraintsBuilder(bw);
        System.out.println("New constraints size after adding ascending constraints : " + bw.getConstraints().size());


        /////////////////////////////////////////////////////////////
        // Create an impossible world to test the constraint logic //     
        /////////////////////////////////////////////////////////////
        System.out.println("\n===================");
        System.out.println("SETS UP A NEW WORLD");
        System.out.println("===================\n");

        BlocksWorld bw2 = new BlocksWorld(3, 3);
        System.out.println("Block amount "+bw2.getData().getBlocksAmount());
        System.out.println("Stack amount "+bw2.getData().getStackAmount());
        System.out.println("Constraints amount "+bw2.getConstraints().size());

        Map<Variable, Object> state2 = new HashMap<Variable, Object>();
        Variable[] on2 = bw2.getData().getOn().toArray(Variable[]::new);
        Variable[] fixed2 = bw2.getData().getFixed().toArray(Variable[]::new);
        Variable[] free2 = bw2.getData().getFree().toArray(Variable[]::new);

        // Here the blocks are stacked on a single stack, the stack 1 and 2 are free, the blocks 0 and 1 are fixed and only the top one (block 2) is okay to moove
        state.put(on2[0], -1);
        state.put(on2[1], -1);
        state.put(on2[2], 1);
        state.put(fixed2[0], true);
        state.put(fixed2[1], true);
        state.put(fixed2[2], false);
        state.put(free2[0], false);
        state.put(free2[1], true);
        state.put(free2[2], true);
        
        System.out.println("Assert that it does not match the base constraints with 2 blocks on the same block :");
        System.out.println(bw.getConstraints().stream().allMatch(c -> c.isSatisfiedBy(state)));
    }
}

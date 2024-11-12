import modelling.Variable;

import java.util.HashMap;
import java.util.Map;

import blocksworld.BWAscendingConstraintsBuilder;
import blocksworld.BWRegularConstraintsBuilder;
import blocksworld.BlocksWorld;

public class main {
    public static void main(String[] args) {

        // Create a blockworld and test the bas constraints on the initialisation process
        BlocksWorld bw = new BlocksWorld(3, 3);
        System.out.println("Block amount "+bw.getData().getBlocksAmount());
        System.out.println("Stack amount "+bw.getData().getStackAmount());
        System.out.println("Constraints amount "+bw.getConstraints().size());

        Map<Variable, Object> state = new HashMap<Variable, Object>();
        Variable[] on = bw.getData().getOnArray();
        Variable[] fixed = bw.getData().getFixedArray();
        Variable[] free = bw.getData().getFreeArray();

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
        new BWRegularConstraintsBuilder(bw).build();
        System.out.println("New constraints size after adding regular constraints : " + bw.getConstraints().size());

        // Test the ascending constraints
        new BWAscendingConstraintsBuilder(bw).build();
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
        Variable[] on2 = bw2.getData().getOnArray();
        Variable[] fixed2 = bw2.getData().getFixedArray();
        Variable[] free2 = bw2.getData().getFreeArray();

        // Here blocks 1 and 2 are both on block 0, which is impossible. Stack 1 and 2 are free, blocks 0 and 1 are fixed, and only block 2 is free to move.
        state2.put(on2[0], -1);
        state2.put(on2[1], 0); // Block 1 is on Block 0
        state2.put(on2[2], 0); // Block 2 is also on Block 0, making it impossible
        state2.put(fixed2[0], true);
        state2.put(fixed2[1], true);
        state2.put(fixed2[2], false);
        state2.put(free2[0], false);
        state2.put(free2[1], true);
        state2.put(free2[2], true);
        
        System.out.println("Assert that it does not match the base constraints with 2 blocks on the same block :");
        System.out.println(bw.getConstraints().stream().allMatch(c -> c.isSatisfiedBy(state)));
    }
}

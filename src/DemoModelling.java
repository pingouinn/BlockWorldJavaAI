import modelling.Variable;

import java.util.HashMap;
import java.util.Map;

import blocksworld.BWAscendingConstraintsBuilder;
import blocksworld.BWData;
import blocksworld.BWRegularConstraintsBuilder;
import blocksworld.BlocksWorld;

public class DemoModelling {
    public static void main(String[] args) {

        // Create a blockworld and test the bas constraints on the initialisation process
        BlocksWorld bw = new BlocksWorld(3, 3);

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

        checkWorldPrint(bw, state, "a working world");

        // Test the regular constraints
        new BWRegularConstraintsBuilder(bw).build();
        System.out.println("New constraints size after adding regular constraints : " + bw.getConstraints().size());

        // Test the ascending constraints
        new BWAscendingConstraintsBuilder(bw).build();
        System.out.println("New constraints size after adding ascending constraints : " + bw.getConstraints().size());


        /////////////////////////////////////////////////////////////
        // Create an impossible world to test the constraint logic //
        /////////////////////////////////////////////////////////////
        BlocksWorld bw2 = new BlocksWorld(3, 3);

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

        checkWorldPrint(bw2, state2, "An impossible world, blocks 1 and 2 are on block 0");

        // Test FIxed constraints
        BlocksWorld bw3 = new BlocksWorld(3, 3);
        Map<Variable, Object> state3 = new HashMap<Variable, Object>();
        Variable[] on3 = bw3.getData().getOnArray();
        Variable[] fixed3 = bw3.getData().getFixedArray();
        Variable[] free3 = bw3.getData().getFreeArray();

        // Here block 1 is not fixed and block 2 is on block 1
        state3.put(on3[0], -1);
        state3.put(on3[1], 0);
        state3.put(on3[2], 1);

        state3.put(fixed3[0], true);
        state3.put(fixed3[1], false);
        state3.put(fixed3[2], false);

        state3.put(free3[0], false);
        state3.put(free3[1], true);
        state3.put(free3[2], true);
        
        checkWorldPrint(bw3, state3, "A world where block 1 is not fixed but there is block on top of it");


        BlocksWorld bw4 = new BlocksWorld(3, 3);
        Map<Variable, Object> state4 = new HashMap<Variable, Object>();

        Variable[] on4 = bw4.getData().getOnArray();
        Variable[] fixed4 = bw4.getData().getFixedArray();
        Variable[] free4 = bw4.getData().getFreeArray();

        // Here stack 1 is free but there is a block on it
        state4.put(on4[0], -1);
        state4.put(on4[1], 0);
        state4.put(on4[2], 1);

        state4.put(fixed4[0], true);
        state4.put(fixed4[1], true);
        state4.put(fixed4[2], false);

        state4.put(free4[0], true);
        state4.put(free4[1], true);
        state4.put(free4[2], true);

        checkWorldPrint(bw4, state4, "A world where stack 1 is free but there is block on it");

        // Check the regular constraints

        BlocksWorld bw5 = new BlocksWorld(6, 3);
        new BWRegularConstraintsBuilder(bw5).build();
        Map<Variable, Object> state5 = new HashMap<Variable, Object>();

        Variable[] on5 = bw5.getData().getOnArray();
        Variable[] fixed5 = bw5.getData().getFixedArray();
        Variable[] free5 = bw5.getData().getFreeArray();

        // Check Regular constraints
        state5.put(on5[0], -1);
        state5.put(on5[1], -2);
        state5.put(on5[2], 0);
        state5.put(on5[3], 1);
        state5.put(on5[4], 2);
        state5.put(on5[5], 3);

        state5.put(fixed5[0], true);
        state5.put(fixed5[1], true);
        state5.put(fixed5[2], true);
        state5.put(fixed5[3], true);
        state5.put(fixed5[4], false);
        state5.put(fixed5[5], false);

        state5.put(free5[0], false);
        state5.put(free5[1], false);
        state5.put(free5[2], true);

        checkWorldPrint(bw5, state5, "A working world with regular");

        BlocksWorld bw6 = new BlocksWorld(6, 3);
        new BWRegularConstraintsBuilder(bw6).build();

        Map<Variable, Object> state6 = new HashMap<Variable, Object>();

        Variable[] on6 = bw6.getData().getOnArray();
        Variable[] fixed6 = bw6.getData().getFixedArray();
        Variable[] free6 = bw6.getData().getFreeArray();

        state6.put(on6[0], -1);
        state6.put(on6[1], -2);
        state6.put(on6[2], 0);
        state6.put(on6[3], 2);
        state6.put(on6[4], 1);
        state6.put(on6[5], 3);

        state6.put(fixed6[0], true);
        state6.put(fixed6[1], true);
        state6.put(fixed6[2], true);
        state6.put(fixed6[3], true);
        state6.put(fixed6[4], false);
        state6.put(fixed6[5], false);

        state6.put(free6[0], false);
        state6.put(free6[1], false);
        state6.put(free6[2], true);

        checkWorldPrint(bw6, state6, "A impossible world with regular");

        // Check Ascending constraints
        BlocksWorld bw7 = new BlocksWorld(3, 3);
        new BWAscendingConstraintsBuilder(bw7).build();

        Map<Variable, Object> state7 = new HashMap<Variable, Object>();

        Variable[] on7 = bw7.getData().getOnArray();
        Variable[] fixed7 = bw7.getData().getFixedArray();
        Variable[] free7 = bw7.getData().getFreeArray();

        state7.put(on7[0], -1);
        state7.put(on7[1], 0);
        state7.put(on7[2], 1);

        state7.put(fixed7[0], true);
        state7.put(fixed7[1], true);
        state7.put(fixed7[2], false);

        state7.put(free7[0], false);
        state7.put(free7[1], false);
        state7.put(free7[2], true);

        checkWorldPrint(bw7, state7, "A working world with ascending");

        BlocksWorld bw8 = new BlocksWorld(3, 3);
        new BWAscendingConstraintsBuilder(bw8).build();

        Map<Variable, Object> state8 = new HashMap<Variable, Object>();

        Variable[] on8 = bw8.getData().getOnArray();
        Variable[] fixed8 = bw8.getData().getFixedArray();
        Variable[] free8 = bw8.getData().getFreeArray();

        state8.put(on8[0], -1);
        state8.put(on8[1], 2);
        state8.put(on8[2], 0);

        state8.put(fixed8[0], true);
        state8.put(fixed8[1], false);
        state8.put(fixed8[2], true);

        state8.put(free8[0], false);
        state8.put(free8[1], true);
        state8.put(free8[2], true);

        checkWorldPrint(bw8, state8, "A impossible world with ascending");
    }

    private static void checkWorldPrint(BlocksWorld bw, Map<Variable, Object> state, String message) {
        BWData data = bw.getData();
        System.out.println("\n===================");
        System.out.println("SETS UP A NEW WORLD");
        System.out.println("===================");

        System.out.println(message + "\n");

        System.out.println("Block amount " + data.getBlocksAmount());
        System.out.println("Stack amount " + data.getStackAmount());
        System.out.println("Constraints amount " + bw.getConstraints().size());
        System.out.println("\nState of the blocksWorld:");
        for (int i = 0; i < data.getBlocksAmount(); i++) {
            Variable on = data.getOnArray()[i];
            Variable fixed = data.getFixedArray()[i];
            System.out.println("\t"+ on + " : " + state.get(on));
            System.out.println("\t" + fixed + " : " + state.get(fixed) + "\n");
        }
        System.out.println("\n");
        for (int i = 0; i < data.getStackAmount(); i++) {
            Variable free = data.getFreeArray()[i];
            System.out.println("\t" + free + " : " + state.get(free));
        }
        
        System.out.println("\nCheck if for all constraints is satisfied by the state : " + bw.getConstraints().stream().allMatch(c -> c.isSatisfiedBy(state)));
    }
}

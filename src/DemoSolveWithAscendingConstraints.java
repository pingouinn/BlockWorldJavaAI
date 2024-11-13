import java.util.Map;
import java.util.Random;

import blocksworld.BlocksWorld;
import blocksworld.DisplayBW;
import cp.BacktrackSolver;
import cp.DomainSizeVariableHeuristic;
import cp.MACSolver;
import cp.HeuristicMACSolver;
import cp.ValueHeuristic;
import cp.VariableHeuristic;
import cp.RandomValueHeuristic;
import cp.Solver;
import cp.NbConstraintsVariableHeuristic;
import modelling.Variable;
import blocksworld.BWAscendingConstraintsBuilder;

public class DemoSolveWithAscendingConstraints {
    
    public static void main(String[] args) {
        BlocksWorld world = new BlocksWorld(5, 3);

        // Creating constraints
        new BWAscendingConstraintsBuilder(world).build();

        // GoalState
        // For the final goal, we want to have the blocks in the same order on the stack 2
        ValueHeuristic valueHeuristic = new RandomValueHeuristic(new Random());
        VariableHeuristic nbConstraintsVariableHeuristic = new NbConstraintsVariableHeuristic(world.getConstraints(), true);
        VariableHeuristic domainSizeVariableHeuristic = new DomainSizeVariableHeuristic(true);
        Solver[] solvers = new Solver[] {
            new BacktrackSolver(world.getData().getAll(), world.getConstraints()),
            new MACSolver(world.getData().getAll(), world.getConstraints()),
            new HeuristicMACSolver(world.getData().getAll(), world.getConstraints(), nbConstraintsVariableHeuristic, valueHeuristic),
            new HeuristicMACSolver(world.getData().getAll(), world.getConstraints(), domainSizeVariableHeuristic, valueHeuristic)
        };
        
        int i = 0;
        int x = 325;
        int y = 100;
        String[] names = new String[] {"Backtrack", "MAC", "Heuristic MAC(nbConstraints)", "Heuristic MAC(domainSize)"};
        for (Solver solver : solvers) {
            long startTime = System.currentTimeMillis();
            Map<Variable, Object> state = solver.solve();
            long endTime = System.currentTimeMillis();
            if (x > 1200) {x = 325;y += 400;}
            new DisplayBW(names[i], world.getData(), x, y, state);
            System.out.println("Time to solve " + names[i++] + " : " + (endTime - startTime) + "ms");
            x += 650;
        }
    }

}

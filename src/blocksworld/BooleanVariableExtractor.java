package blocksworld;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modelling.BooleanVariable;

public class BooleanVariableExtractor {
    private int numberOfBlocks;
    private int numberOfStacks;

    public BooleanVariableExtractor(int numberOfBlocks, int numberOfStacks) {
        this.numberOfBlocks = numberOfBlocks;
        this.numberOfStacks = numberOfStacks;
    }

    public Set<BooleanVariable> getAllBooleanVariables() {
        Set<BooleanVariable> booleanVariables = new HashSet<>();
        for (int i = 0; i < numberOfBlocks; i++) {
            booleanVariables.add(new BooleanVariable("fixed" + i));
            for (int j = 0; j < numberOfBlocks; j++) 
                booleanVariables.add(new BooleanVariable("on_" + i + "_" + j));
            for (int j = 0; j < numberOfStacks; j++) {
                if (i == 0) {
                    booleanVariables.add(new BooleanVariable("free_" + j));
                } else {
                    booleanVariables.add(new BooleanVariable("on-table" + i + "_" + j));
                }
            }
        }
        return booleanVariables;
    }

    public Set<BooleanVariable> getBooleanVariablesfromState(List<List<Integer>> state) {
        Set<BooleanVariable> booleanVariables = new HashSet<>();
        for (int i = 0; i < state.size(); i++) {
            List<Integer> columns = state.get(i);
            if (columns.isEmpty()) {
                booleanVariables.add(new BooleanVariable("free_" + i));
            }
            
            for (int j = 0; j < columns.size(); j++) {
                if (j < columns.size() - 1) {
                    booleanVariables.add(new BooleanVariable("fixed_" + columns.get(j)));
                }

                if (j == 0) {
                    booleanVariables.add(new BooleanVariable("on-table_" + columns.get(j) + "_" + i));
                } else {
                    booleanVariables.add(new BooleanVariable("on_" + columns.get(j - 1) + "_" + columns.get(j)));
                }
            }
        }
        return booleanVariables;
    }
}


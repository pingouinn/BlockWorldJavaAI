package blocksworld;

import java.util.Set;

import modelling.Constraint;

//TODO: EX2
public class BlocksWorld {
    
    private BlocksWorldVariables variables;
    private Set<Constraint> constraints;

    
    public BlocksWorld(int blocksAmount, int stackAmount) {
        this.variables = new BlocksWorldVariables(blocksAmount, stackAmount);
        this.constraints = null; // TODO
    }
}

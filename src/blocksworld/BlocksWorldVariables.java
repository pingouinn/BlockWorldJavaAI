package blocksworld;

import java.util.Set;

import modelling.BooleanVariable;
import modelling.Variable;

import java.util.HashSet;

public class BlocksWorldVariables {

    private int blocksAmount;
    private int stackAmount;

    private Set<Variable> on;
    private Set<Variable> fixed;
    private Set<Variable> free;
    private Set<Variable> all;

    public BlocksWorldVariables(int blocksAmount, int stackAmount) {
        this.blocksAmount = blocksAmount;
        this.stackAmount = stackAmount;

        this.all = new HashSet<>();

        createOnVariables();
        createFixedVariables();
        createFreeVariables();
    }

    public int getBlocksAmount() {
        return this.blocksAmount;
    }

    public int getStackAmount() {
        return this.stackAmount;
    }

    public Set<Variable> getOn() {
        return this.on;
    }

    public Set<Variable> getFixed() {
        return this.fixed;
    }

    public Set<Variable> getFree() {
        return this.free;
    }

    public Set<Variable> getAll() {
        return this.all;
    }

    private void createOnVariables() {
        this.on = new HashSet<>();
        Set<Object> wholeDomain = createDomain();

        for (int i = 0; i < this.blocksAmount; i++) {
            Set<Object> domain = new HashSet<>(wholeDomain);
            domain.remove(i);
            Variable var = new Variable("on" + i, domain);
            this.on.add(var);
            this.all.add(var);
        }
    }

    private void createFixedVariables() {
        this.fixed = new HashSet<>();
        for (int i = 0; i < this.blocksAmount; i++) {
            Variable var = new BooleanVariable("fixed" + i);
            this.fixed.add(var);
            this.all.add(var);
        }
    }

    private void createFreeVariables() {
        this.free = new HashSet<>();
        for (int i = 0; i < this.stackAmount; i++) {
            Variable var = new BooleanVariable("free" + i);
            this.free.add(var);
            this.all.add(var);
        }
    }


    private Set<Object> createDomain() {
        Set<Object> domain = new HashSet<>();
        for (int i = -(this.stackAmount); i < this.blocksAmount; i++) {
            domain.add(i);
        }
        return domain;
    }
}
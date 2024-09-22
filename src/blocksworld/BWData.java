package blocksworld;

import java.util.Set;

import modelling.BooleanVariable;
import modelling.Variable;

import java.util.HashSet;

public class BWData {

    private int blocksAmount;
    private int stackAmount;

    private Set<Variable> on;
    private Set<Variable> fixed;
    private Set<Variable> free;
    private Set<Variable> all;

    public BWData(int blocksAmount, int stackAmount) {
        this.blocksAmount = blocksAmount;
        this.stackAmount = stackAmount;

        this.all = new HashSet<>();

        createFreeVariables();
        createBlockVariables();
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

    private void createFreeVariables() {
        this.free = new HashSet<>();
        for (int i = 0; i < this.stackAmount; i++) {
            Variable freeP = new BooleanVariable("free" + i);
            this.free.add(freeP);
            this.all.add(freeP);
        }
    }

    private void createBlockVariables() {
        this.on = new HashSet<>();
        this.fixed = new HashSet<>();

        Set<Object> wholeDomain = createDomain();

        for (int i = 0; i < this.blocksAmount; i++) {
            Set<Object> domain = new HashSet<>(wholeDomain);
            domain.remove(i);
            Variable onB = new Variable("on" + i, domain);
            this.on.add(onB);
            this.all.add(onB);

            Variable fixedB = new BooleanVariable("fixed" + i);
            this.fixed.add(fixedB);
            this.all.add(fixedB);
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
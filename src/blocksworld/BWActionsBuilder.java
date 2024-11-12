package blocksworld;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import planning.Action;
import planning.BasicAction;
import modelling.Variable;

/*
 * This class is used to create the actions for the blocksworld problem
 * We took a liberty to use BWData instead of the nbBlocks and nbPiles arguments because we think it's more convenient and straightforward
 * 4 methods are used to create the actions :
 *   1. moveAFromBToBprime : move a block from a block to another block
 *   2. moveAFromBToPile : move a block from a block to a free pile
 *   3. moveAFromPileToB : move a block from a pile to a block
 *   4. moveAFromPileToPileprime : move a block from a pile to a free pile
 */

public class BWActionsBuilder {
    private BWData data;

    private Set<Action> actions;

    public BWActionsBuilder(BWData data) {
        this.data = data;
        this.actions = new HashSet<Action>();
    }

    public void build() {
        this.moveAFromBToBprime();
        this.moveAFromBToPile();
        this.moveAFromPileToB();
        this.moveAFromPileToPileprime();
    }

    public Set<Action> getActions() {
        return this.actions;
    }

    private void moveAFromBToBprime() {
        // Precondition : on = b', fixedB'' = false -> on = b'', fixedB' = false, fixedB'' = true (fixedB = always false)
        Variable[] on = this.data.getOnArray();
        Variable[] fixed = this.data.getFixedArray();

        for (int b = 0; b < this.data.getBlocksAmount(); b++) {
            for (int bPrime = 0; bPrime < this.data.getBlocksAmount(); bPrime++) {
                for (int bPrimePrime = 0; bPrimePrime < this.data.getBlocksAmount(); bPrimePrime++) {
                    if (b != bPrime && b != bPrimePrime && bPrime != bPrimePrime) {
                        Map<Variable, Object> preconditions = new HashMap<>();
                        preconditions.put(on[b], bPrime);
                        preconditions.put(fixed[b], false);
                        preconditions.put(fixed[bPrimePrime], false);
                        Map<Variable, Object> effects = new HashMap<>();
                        effects.put(on[b], bPrimePrime);
                        effects.put(fixed[bPrime], false);
                        effects.put(fixed[bPrimePrime], true);
                        this.actions.add(new BasicAction(preconditions, effects, 1));
                    }
                }
            }
        }
    }

    private void moveAFromBToPile() {
        // Precondition : on = b', freeP = true -> on = p, fixedB' = false, freeP = false (fixedB = always false)
        Variable[] on = this.data.getOnArray();
        Variable[] fixed = this.data.getFixedArray();
        Variable[] free = this.data.getFreeArray();

        for (int b = 0; b < this.data.getBlocksAmount(); b++) {
            for (int bPrime = 0; bPrime < this.data.getBlocksAmount(); bPrime++) {
                for (int p = 0; p < this.data.getStackAmount(); p++) {
                    if (b != bPrime) {
                        Map<Variable, Object> preconditions = new HashMap<>();
                        preconditions.put(on[b], bPrime);
                        preconditions.put(fixed[b], false);
                        preconditions.put(free[p], true);
                        Map<Variable, Object> effects = new HashMap<>();
                        effects.put(on[b], -(p + 1));
                        effects.put(fixed[bPrime], false);
                        effects.put(free[p], false);
                        this.actions.add(new BasicAction(preconditions, effects, 1));
                    }
                }
            }
        }
    }

    private void moveAFromPileToB() {
        // Precondition : on = p, fixedB' = false -> on = b', fixedB' = true, freeP = true (fixedB = always false)
        Variable[] on = this.data.getOnArray();
        Variable[] fixed = this.data.getFixedArray();
        Variable[] free = this.data.getFreeArray();

        for (int b = 0; b < this.data.getBlocksAmount(); b++) {
            for (int p = 0; p < this.data.getStackAmount(); p++) {
                for (int bPrime = 0; bPrime < this.data.getBlocksAmount(); bPrime++) {
                    if (b != bPrime) {
                        Map<Variable, Object> preconditions = new HashMap<>();
                        preconditions.put(on[b], -(p + 1));
                        preconditions.put(fixed[b], false);
                        preconditions.put(fixed[bPrime], false);
                        Map<Variable, Object> effects = new HashMap<>();
                        effects.put(on[b], bPrime);
                        effects.put(fixed[bPrime], true);
                        effects.put(free[p], true);
                        this.actions.add(new BasicAction(preconditions, effects, 1));
                    }
                }
            }
        }
    }

    private void moveAFromPileToPileprime() {
        // Precondition : on = p, fixedB = false, freeP' = true -> on = p', freeP = true, freeP' = false (fixedB = always false)
        Variable[] on = this.data.getOnArray();
        Variable[] fixed = this.data.getFixedArray();
        Variable[] free = this.data.getFreeArray();

        for (int b = 0; b < this.data.getBlocksAmount(); b++) {
            for (int p = 0; p < this.data.getStackAmount(); p++) {
                for (int pPrime = 0; pPrime < this.data.getStackAmount(); pPrime++) {
                    if (p != pPrime) {
                        Map<Variable, Object> preconditions = new HashMap<>();
                        preconditions.put(on[b], -(p + 1));
                        preconditions.put(fixed[b], false);
                        preconditions.put(free[pPrime], true);
                        Map<Variable, Object> effects = new HashMap<>();
                        effects.put(on[b], -(pPrime + 1));
                        effects.put(free[pPrime], false);
                        effects.put(free[p], true);
                        this.actions.add(new BasicAction(preconditions, effects, 1));
                    }
                }
            }
        }
    }  
}
package cp;

import java.util.Collections;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import modelling.Variable;

public class RandomValueHeuristic implements ValueHeuristic {
    
    private Random random;

    public RandomValueHeuristic(Random random) {
        this.random = random;
    }

    @Override
    public List<Object> ordering(Variable variable, Set<Object> domain) {
        List<Object> ordered = new ArrayList<>(domain);
        Collections.shuffle(ordered, this.random);
        return ordered;
    }

}
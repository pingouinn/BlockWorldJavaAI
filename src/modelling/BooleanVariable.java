package modelling;

import java.util.Set;

public class BooleanVariable extends Variable {
    public BooleanVariable(String name) {
        super(name, Set.of(true, false));
    }
}

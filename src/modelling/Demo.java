package modelling;

import java.util.Map;
import java.util.Set;

public class Demo {
    public static void main(String[] args) {

        //Custom tests and application

        Variable v1 = new BooleanVariable("First");
        Variable v2 = new BooleanVariable("Second");
        Constraint c1 = new DifferenceConstraint(v1, v2); //La contrainte c1 garantit que v1 et v2 sont différents.
        Constraint c2 = new Implication(v1, Set.of(true), v2, Set.of(false)); //La contrainte c2 garantit que si v1 est vrai, alors v2 doit être faux.
        Constraint c3 = new UnaryConstraint(v2, Set.of(false)); //La contrainte c3 garantit que v2 doit être faux.

    
        Map<Variable, Object> workingExample = Map.of(v1, true, v2, false);
        Map<Variable, Object> failingExample = Map.of(v1, true, v2, true);

        System.out.println("Failing example based on the second case where v1 is containing true and v2 is false:");
        System.out.println("c1: " + c1.isSatisfiedBy(workingExample));
        System.out.println("c2: " + c2.isSatisfiedBy(workingExample));
        System.out.println("c3: " + c3.isSatisfiedBy(workingExample));
        
        System.out.println("Working example based on the first case where v1 and v2 are containing true:");
        System.out.println("c1: " + c1.isSatisfiedBy(failingExample));
        System.out.println("c2: " + c2.isSatisfiedBy(failingExample));
        System.out.println("c3: " + c3.isSatisfiedBy(failingExample));

    }
}
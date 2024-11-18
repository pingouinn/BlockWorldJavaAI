package cp;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import modelling.Constraint;
import modelling.Variable;

public class ArcConsistency {
    private Set<Constraint> unaryConstraints;
    private Set<Constraint> binaryConstraints;

    public ArcConsistency(Set<Constraint> constraints) {
        this.unaryConstraints = new HashSet<>();
        this.binaryConstraints = new HashSet<>();

        for (Constraint c : constraints) {
            int scopeSize = c.getScope().size();
            if (scopeSize > 2)
                throw new IllegalArgumentException("Constraint with more than 2 variables");
            if (scopeSize == 1)
                unaryConstraints.add(c);
            else
                binaryConstraints.add(c);
        }
    }

    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains) {
        boolean allDomainsNonEmpty = true;
        Map<Variable, Set<Object>> tmp = new HashMap<>();
    
        // Create a mutable copy of each domain set
        for (Map.Entry<Variable, Set<Object>> entry : domains.entrySet()) {
            tmp.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
    
        for (Map.Entry<Variable, Set<Object>> entry : tmp.entrySet()) {
            Variable v = entry.getKey();
            Set<Object> domain = entry.getValue();
            
            domain.removeIf(value -> unaryConstraints.stream()
                .anyMatch(c -> 
                    c.getScope().contains(v)
                    && !c.isSatisfiedBy(Map.of(v, value))
                )
            );
    
            if (domain.isEmpty()) {
                allDomainsNonEmpty = false;
            }
            
            // Update the original domains map with the modified domain
            domains.put(v, domain);
        }
        return allDomainsNonEmpty;
    }

    public boolean revise(Variable v1, Set<Object> domain1, Variable v2, Set<Object> domain2) {
        Set<Object> toRemove = new HashSet<>();
        for (Object value1 : domain1) {
            boolean keepValue1 = domain2.stream().anyMatch(value2 ->
                binaryConstraints.stream().allMatch(c -> 
                    !c.getScope().containsAll(Set.of(v1, v2)) 
                    || c.isSatisfiedBy(Map.of(v1, value1, v2, value2))
                )
            );
            if (!keepValue1)
                toRemove.add(value1);
        }
        if (!toRemove.isEmpty()) {
            domain1.removeAll(toRemove);
            return true;
        }
        return false;
    }

    public boolean ac1(Map<Variable, Set<Object>> domains) {
        if (!enforceNodeConsistency(domains))
            return false;
        boolean changed;
        do {
            changed = false;
            for (Constraint binaryConstraint : binaryConstraints) {
                Iterator<Variable> scopeIt = binaryConstraint.getScope().iterator();
                Variable v1 = scopeIt.next();
                Variable v2 = scopeIt.next();
                Set<Object> domain1 = domains.get(v1);
                Set<Object> domain2 = domains.get(v2);
                if (revise(v1, domain1, v2, domain2) || revise(v2, domain2, v1, domain1))
                    changed = true;
            }
        } while (changed);
        return domains.values().stream().allMatch(domain -> !domain.isEmpty());
    }
}

package datamining;

import java.util.Set;

public interface AssociationRuleMiner {
    BooleanDatabase getDatabase();
    Set<AssociationRule> extract(float minFrequency, float minConfidence);
}
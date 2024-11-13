package datamining;

import java.util.HashSet;
import java.util.Set;
import modelling.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    protected BooleanDatabase db;

    public AbstractAssociationRuleMiner(BooleanDatabase db) {
        this.db = db;
    }

    public BooleanDatabase getDatabase() {
        return this.db;
    }

    // On parcourt la liste d'items, et on calcule la fréquence de chaque item.
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> frequent) {
        for (Itemset itemset : frequent) {
            if (itemset.getItems().equals(items)) {
                return itemset.getFrequency();
            }
        }
        throw new IllegalArgumentException("Itemset not found in frequent itemsets");
    }

    public static float confidence(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion, Set<Itemset> frequent) {
        // Calculer l'union de la prémisse et de la conclusion
        Set<BooleanVariable> finalConditions = new HashSet<>(premisse);
        finalConditions.addAll(conclusion);
        return frequency(finalConditions, frequent) / frequency(premisse, frequent);
    }
}
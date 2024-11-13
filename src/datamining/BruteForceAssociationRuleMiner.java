package datamining;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modelling.BooleanVariable;


public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    private BooleanDatabase database;

    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
        this.database = database;
    }

    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> result = new HashSet<>();
        if (items.isEmpty()) { 
            return result; 
        }

        List<BooleanVariable> itemList = new ArrayList<>(items);
        int numOfSubsets = 1 << itemList.size(); //Left Bitshift dégeu pour avoir 2^n subsets


        // Retourne l'ensemble des sous ensembles
        for (int i = 1; i < numOfSubsets - 1; i++) {
            Set<BooleanVariable> subset = new HashSet<>();
            for (int j = 0; j < itemList.size(); j++) {
                if ((i & (1 << j)) > 0) { // Si le j-ième bit est à 1 dans i, on ajoute l'élément à l'ensemble (et on évite les doublons). EX : si i = 3 (11 en bin) et j = 1, on a 1 << 1 = 2 (10 en bin) et 3 &(ET bit à bit) 2 = 2 > 0 donc on ajoute l'élément à l'ensemble
                    subset.add(itemList.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }

    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {
        Set<AssociationRule> result = new HashSet<>();
        
        Apriori apriori = new Apriori(database);
        Set<Itemset> frequentItemsets = apriori.extract(minFrequency);
        for (Itemset itemset : frequentItemsets) {
            Set<BooleanVariable> items = itemset.getItems();
            Set<Set<BooleanVariable>> candidatePremises = allCandidatePremises(items);
            for (Set<BooleanVariable> premise : candidatePremises) {
                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premise);
                float freqX = frequency(items, frequentItemsets);
                float confidence = confidence(premise, conclusion, frequentItemsets);
                if (confidence >= minConfidence) {
                    result.add(new AssociationRule(premise, conclusion, freqX, confidence));
                }
            }
        }

        return result;
    }

}
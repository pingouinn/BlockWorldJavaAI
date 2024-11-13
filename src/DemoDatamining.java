import java.util.List;
import java.util.Random;
import java.util.Set;

import bwgeneratordemo.Demo;

import blocksworld.BooleanVariableExtractor;
import datamining.BooleanDatabase;
import modelling.BooleanVariable;
import datamining.Apriori;
import datamining.BruteForceAssociationRuleMiner;
import datamining.AssociationRule;
import datamining.Itemset;

public class DemoDatamining {
    public static void main(String[] args) {
        int blocksAmount = Demo.NB_BLOCKS;
        int stacksAmount = Demo.NB_STACKS;
        BooleanVariableExtractor extractor = new BooleanVariableExtractor(blocksAmount, stacksAmount);

        BooleanDatabase db = new BooleanDatabase(extractor.getAllBooleanVariables());
        for (int i = 0; i < 10_000; i++) {
            // Drawing a state at random
            List<List<Integer>> state = Demo.getState(new Random());
            // Converting state to instance
            Set<BooleanVariable> instance = extractor.getBooleanVariablesfromState(state);
            // Adding state to database
            db.add(instance);
        }

        // Creating an Apriori object and a BruteForceAssociationRuleMiner object
        Apriori apriori = new Apriori(db);
        BruteForceAssociationRuleMiner miner = new BruteForceAssociationRuleMiner(db);

        // Extracting frequent itemsets and association rules
        Set<Itemset> frequentItemsets = apriori.extract(.35f);
        Set<AssociationRule> rules = miner.extract(.35f, .95f);

        // Displaying frequent itemsets and association rules
        System.out.println("Frequent itemsets:");
        for (Itemset itemset : frequentItemsets) {
            System.out.println(itemset);
        }

        System.out.println("Association rules:");
        for (AssociationRule rule : rules) {
            System.out.println(rule);
        }
    }
}
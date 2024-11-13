package datamining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.List;

import modelling.BooleanVariable;

public class Apriori extends AbstractItemsetMiner {
    
    public Apriori(BooleanDatabase database) {
        super(database);
    }

    public Set<Itemset> frequentSingletons(float minFrequency) {
        Set<Itemset> singletons = new HashSet<>();
        for (BooleanVariable item : getDatabase().getItems()) {
            Set<BooleanVariable> items = new HashSet<>();
            items.add(item);
            float frequency = frequency(items);
            if (frequency >= minFrequency) {
                singletons.add(new Itemset(items, frequency));
            }
        }
        return singletons;
    }

    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> set1, SortedSet<BooleanVariable> set2) {
        int size = set1.size();
        if (size != set2.size() || size == 0)
            return null;

        Iterator<BooleanVariable> it1 = set1.iterator();
        Iterator<BooleanVariable> it2 = set2.iterator();
        
        for (int i = 0; i < size - 1; i++) {
            if (!it1.next().equals(it2.next()))
                return null;
        }

        BooleanVariable lastItem2 = it2.next();

        if (it1.next().equals(lastItem2))
            return null;

        SortedSet<BooleanVariable> combinedSet = new TreeSet<>(set1);
        combinedSet.add(lastItem2);

        return combinedSet;
    }

    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> frequentItemsets) {
        if (items.size() == 1)
            return true;

        Set<BooleanVariable> subset = new HashSet<>(items);
        for (BooleanVariable item : items) {
            subset.remove(item);
            if (!frequentItemsets.contains(subset))
                return false;
            subset.add(item);
        }

        return true;
    }
    
    @Override
    public Set<Itemset> extract(float minFrequency) {
        Set<Itemset> res = frequentSingletons(minFrequency);
        List<SortedSet<BooleanVariable>> frequentItemsetsK = new ArrayList<>();

        for (Itemset singleton : res) {
            SortedSet<BooleanVariable> tmp = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            tmp.addAll(singleton.getItems());
            frequentItemsetsK.add(tmp);
        }

        while (!frequentItemsetsK.isEmpty()) {
            List<SortedSet<BooleanVariable>> newFrequentItemsets = new ArrayList<>();
            for (int i = 0; i < frequentItemsetsK.size(); i++) {
                for (int j = i + 1; j < frequentItemsetsK.size(); j++) {
                    SortedSet<BooleanVariable> combined = combine(frequentItemsetsK.get(i), frequentItemsetsK.get(j));
                    if (combined != null && allSubsetsFrequent(combined, frequentItemsetsK)) {
                        float freq = frequency(combined);
                        if (freq >= minFrequency) {
                            res.add(new Itemset(combined, freq));
                            newFrequentItemsets.add(combined);
                        }
                    }
                }
            }
            frequentItemsetsK = newFrequentItemsets;
        }
        return res;
    }

    
}

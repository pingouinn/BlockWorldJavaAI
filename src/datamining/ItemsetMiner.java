package datamining;

import java.util.Set;

public interface ItemsetMiner {
    
    public BooleanDatabase getDatabase();

    Set<Itemset> extract(float minFrequency);
}

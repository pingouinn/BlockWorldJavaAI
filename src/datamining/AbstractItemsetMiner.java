package datamining;

import java.util.Set;
import java.util.Comparator;
import java.util.List;

import modelling.BooleanVariable;

public abstract class AbstractItemsetMiner implements ItemsetMiner {
    private BooleanDatabase database;
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());

    public AbstractItemsetMiner(BooleanDatabase database) {
        this.database = database;
    }

    public BooleanDatabase getDatabase() {
        return this.database;
    }
    
    public float frequency(Set<BooleanVariable> items) {
        int count = 0;
        List<Set<BooleanVariable>> transactions = database.getTransactions();
        for (Set<BooleanVariable> transaction : transactions) {
            if (transaction.containsAll(items))
                count++;
        }
        return (float) count / transactions.size();
    }
}

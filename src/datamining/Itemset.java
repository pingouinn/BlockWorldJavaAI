package datamining;

import java.util.Set;

import modelling.BooleanVariable;

public class Itemset {
    private Set<BooleanVariable> items;
    private float frequency;

    public Itemset(Set<BooleanVariable> items, float frequency) {
        if (frequency < 0 || frequency > 1) {
            throw new IllegalArgumentException("Frequency must be in [0; 1]");
        }
        this.items = items;
        this.frequency = frequency;
    }

    public Set<BooleanVariable> getItems() {
        return this.items;
    }

    public float getFrequency() {
        return this.frequency;
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (BooleanVariable item : items) {
            sb.append(item.getName());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("} : ");
        sb.append(frequency);
        return sb.toString();
    }
}

package datamining;

import java.util.Set;

import modelling.BooleanVariable;

public class AssociationRule {

    private Set<BooleanVariable> premisses;
    private Set<BooleanVariable> conclusions;
    private float frequency;
    private float confidence;
    
    public AssociationRule(Set<BooleanVariable> premisses, Set<BooleanVariable> conclusions, float frequency, float confidence) {
        this.premisses = premisses;
        this.conclusions = conclusions;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    public Set<BooleanVariable> getPremise() {
        return this.premisses;
    }

    public Set<BooleanVariable> getConclusion() {
        return this.conclusions;
    }

    public float getFrequency() {
        return this.frequency;
    }

    public float getConfidence() {
        return this.confidence;
    }

    @Override
    public String toString() {
        return this.premisses + " : " + this.conclusions + " frequency=" + this.frequency + ", confidence=" + this.confidence + "";
    }
}
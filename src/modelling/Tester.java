package modelling;

import modellingtests.VariableTests;
import modellingtests.BooleanVariableTests;
import modellingtests.DifferenceConstraintTests;
import modellingtests.ImplicationTests;
import modellingtests.UnaryConstraintTests;

public class Tester {
    public static void main(String[] args) {
        boolean ok = true;
        ok = ok && VariableTests.testGetName();
        ok = ok && VariableTests.testGetDomain();
        ok = ok && VariableTests.testEquals();
        ok = ok && VariableTests.testHashCode();
        ok = ok && BooleanVariableTests.testConstructor();
        ok = ok && BooleanVariableTests.testEquals();
        ok = ok && BooleanVariableTests.testHashCode();
        System.out.println(ok ? "All tests OK" : "At least one test KO");


        ok = ok && DifferenceConstraintTests.testGetScope();
        ok = ok && DifferenceConstraintTests.testIsSatisfiedBy();
        ok = ok && ImplicationTests.testGetScope();
        ok = ok && ImplicationTests.testIsSatisfiedBy();
        ok = ok && UnaryConstraintTests.testGetScope();
        ok = ok && UnaryConstraintTests.testIsSatisfiedBy();
        System.out.println(ok ? "All tests OK" : "At least one test KO");
    }
}

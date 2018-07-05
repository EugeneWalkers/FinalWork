package ew.finalwork.utilities;

import java.util.Comparator;

import ew.finalwork.model.TestResult;

public class TestResultComparator implements Comparator<TestResult> {
    @Override
    public int compare(TestResult result, TestResult t1) {
        return result.getTestName().compareTo(t1.getTestName());
    }
}

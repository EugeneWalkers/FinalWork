package ew.finalwork.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TestResult implements Parcelable {
    private String testName;
    private String testResult;

    public TestResult(String testName, String testResult) {
        this.testName = testName;
        this.testResult = testResult;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public static final Parcelable.Creator<TestResult> CREATOR = new Parcelable.Creator<TestResult>() {
        public TestResult createFromParcel(Parcel in) {
            return new TestResult(in);
        }

        public TestResult[] newArray(int size) {
            return new TestResult[size];
        }
    };

    private TestResult(Parcel in) {
        testName = in.readString();
        testResult = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(testName);
        parcel.writeString(testResult);
    }

    public String toString() {
        return testName + ":" + testResult;
    }
}

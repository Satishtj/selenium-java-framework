package com.qaframework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * Owns a single {@link ExtentReports} instance for the run and a thread-local
 * {@link ExtentTest} so parallel tests log to their own nodes.
 */
public final class ExtentManager {

    private static final ExtentReports EXTENT = create();
    private static final ThreadLocal<ExtentTest> CURRENT = new ThreadLocal<>();

    private ExtentManager() {
    }

    private static ExtentReports create() {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report/index.html");
        spark.config().setDocumentTitle("Selenium Java Framework - Test Report");
        spark.config().setReportName("UI Automation Results");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Framework", "Selenium 4 + TestNG");
        return extent;
    }

    public static ExtentTest createTest(String name) {
        ExtentTest test = EXTENT.createTest(name);
        CURRENT.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return CURRENT.get();
    }

    public static void flush() {
        EXTENT.flush();
    }
}

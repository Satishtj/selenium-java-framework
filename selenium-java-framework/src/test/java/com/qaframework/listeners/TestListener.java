package com.qaframework.listeners;

import com.aventstack.extentreports.Status;
import com.qaframework.driver.DriverFactory;
import com.qaframework.reports.ExtentManager;
import com.qaframework.utils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Bridges TestNG events to logging and the Extent report, and attaches a
 * screenshot to the report whenever a test fails.
 */
public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Starting test: {}", result.getMethod().getMethodName());
        ExtentManager.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Passed: {}", result.getMethod().getMethodName());
        ExtentManager.getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String name = result.getMethod().getMethodName();
        log.error("Failed: {}", name, result.getThrowable());

        ExtentManager.getTest().log(Status.FAIL, result.getThrowable());
        try {
            String base64 = ScreenshotUtil.captureAsBase64(DriverFactory.getDriver());
            if (base64 != null) {
                ExtentManager.getTest().addScreenCaptureFromBase64String(base64, "Failure screenshot");
            }
            ScreenshotUtil.capture(DriverFactory.getDriver(), name);
        } catch (Exception e) {
            log.warn("Could not capture failure screenshot: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Skipped: {}", result.getMethod().getMethodName());
        ExtentManager.getTest().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }
}

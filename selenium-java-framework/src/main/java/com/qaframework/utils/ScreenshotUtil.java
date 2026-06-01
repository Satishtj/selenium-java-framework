package com.qaframework.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Captures screenshots on demand (used on test failure) and saves them under target/screenshots.
 */
public final class ScreenshotUtil {

    private static final String DIR = "target/screenshots";
    private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtil() {
    }

    /**
     * @return the path to the saved PNG, or {@code null} if capture failed.
     */
    public static String capture(WebDriver driver, String testName) {
        if (!(driver instanceof TakesScreenshot)) {
            return null;
        }
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dir = Paths.get(DIR);
            Files.createDirectories(dir);
            String fileName = testName + "_" + LocalDateTime.now().format(STAMP) + ".png";
            Path target = dir.resolve(fileName);
            Files.copy(src.toPath(), target);
            return target.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static String captureAsBase64(WebDriver driver) {
        if (!(driver instanceof TakesScreenshot)) {
            return null;
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}

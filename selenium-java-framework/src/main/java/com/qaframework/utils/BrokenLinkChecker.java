package com.qaframework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Utility: BrokenLinkChecker
 * Crawls all anchor tags on a given page and reports broken links (HTTP 4xx/5xx).
 * Uses HEAD requests for efficiency (no response body downloaded).
 */
public class BrokenLinkChecker {

    private final WebDriver driver;
    private int brokenCount = 0;
    private int validCount = 0;

    public BrokenLinkChecker(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Runs the broken link check on the currently loaded page.
     * Prints each link's status to stdout.
     */
    public void check() {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Total links found: " + links.size());

        for (WebElement link : links) {
            String url = link.getAttribute("href");

            if (url == null || url.isEmpty()) {
                System.out.println("SKIP    [---] : (null/empty href)");
                continue;
            }

            // Only process http/https links; skip mailto:, javascript:, etc.
            if (!url.startsWith("http")) {
                System.out.println("SKIP    [---] : " + url);
                continue;
            }

            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("HEAD");
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                // Mimic a real browser to avoid 403 from bot-blocking servers
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.connect();

                int responseCode = conn.getResponseCode();

                if (responseCode >= 400) {
                    System.out.println("BROKEN  [" + responseCode + "] : " + url);
                    brokenCount++;
                } else {
                    System.out.println("VALID   [" + responseCode + "] : " + url);
                    validCount++;
                }
                conn.disconnect();

            } catch (Exception e) {
                System.out.println("ERROR   [EXC] : " + url + " | " + e.getMessage());
                brokenCount++;
            }
        }

        System.out.println("\n--- Summary ---");
        System.out.println("Valid  : " + validCount);
        System.out.println("Broken : " + brokenCount);
        System.out.println("Total  : " + (validCount + brokenCount));
    }

    public int getBrokenCount() { return brokenCount; }
    public int getValidCount()  { return validCount;  }
}
# Selenium Java Test Automation Framework

A clean, production-style UI automation framework built with **Selenium 4**, **TestNG**, and the **Page Object Model**. It runs out of the box against the public [Swag Labs](https://www.saucedemo.com) demo site, so you can clone it and watch the suite pass in minutes.

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4.x-43B02A?logo=selenium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-7.x-FF6C37)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?logo=apachemaven&logoColor=white)

## Features

- **Page Object Model** with a shared `BasePage` for explicit-wait interactions
- **Parallel-safe execution** via a `ThreadLocal` `WebDriver` factory
- **Cross-browser** support (Chrome / Firefox) and **headless** mode, switchable from the CLI
- **Data-driven tests** with TestNG `DataProvider`
- **ExtentReports** HTML reporting with **screenshot-on-failure** capture
- **Log4j2** console + file logging
- **Zero manual driver setup** — Selenium Manager resolves browser drivers automatically
- **CI ready** — GitHub Actions runs the suite headless on every push and pull request

## Tech stack

| Area | Tools |
|---|---|
| Language | Java 17 |
| UI automation | Selenium WebDriver 4 |
| Test runner | TestNG |
| Build | Maven |
| Reporting | ExtentReports |
| Logging | Log4j2 |
| CI | GitHub Actions |

## Project structure

```
selenium-java-framework/
├── pom.xml
├── testng.xml
├── .github/workflows/ci.yml
└── src
    ├── main/java/com/qaframework
    │   ├── config/ConfigReader.java        # properties + CLI overrides
    │   ├── driver/DriverFactory.java       # thread-local driver lifecycle
    │   ├── pages/                          # page objects (BasePage, Login, Products, Cart)
    │   └── utils/ScreenshotUtil.java
    ├── main/resources
    │   ├── config.properties
    │   └── log4j2.xml
    └── test/java/com/qaframework
        ├── base/BaseTest.java
        ├── listeners/TestListener.java     # reporting + screenshot on failure
        ├── reports/ExtentManager.java
        └── tests/                          # LoginTest, CartTest
```

## Getting started

### Prerequisites
- JDK 17+
- Maven 3.9+
- Chrome or Firefox installed

### Run the suite
```bash
# Default: Chrome, headed
mvn test

# Headless (useful for CI or servers)
mvn test -Dbrowser=chrome -Dheadless=true

# Firefox
mvn test -Dbrowser=firefox
```

### Reports
After a run, open the HTML report:
```
target/extent-report/index.html
```
Failure screenshots are saved under `target/screenshots/` and embedded in the report.

## Configuration

Defaults live in `src/main/resources/config.properties` and can be overridden by a system property of the same key (`-Dkey=value`):

| Key | Default | Description |
|---|---|---|
| `browser` | `chrome` | `chrome` or `firefox` |
| `headless` | `false` | run without a visible window |
| `base.url` | `https://www.saucedemo.com` | application under test |
| `explicit.wait.seconds` | `15` | explicit wait timeout |

## What this demonstrates

This framework is intentionally small but shows the patterns I rely on in larger suites: a clear separation between test intent and page mechanics, parallel-safe driver handling, configuration that travels well between local and CI, and reporting that makes failures easy to diagnose.

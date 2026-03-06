# 🍊 OrangeHRM Automation Framework

A Selenium + TestNG automation framework built for the [OrangeHRM](https://opensource-demo.orangehrmlive.com) demo application. This project covers login flows, employee CRUD operations, parallel test execution, Docker-based grid testing, and HTML reporting — all wired together in a clean Page Object Model structure.

---

## 📌 What This Project Tests

| Module | What's Covered |
|---|---|
| Login | Valid login, multiple invalid login scenarios (data-driven) |
| Employee (PIM) | Add employee, update name, delete employee — full CRUD cycle |

---

## 🏗️ Project Structure

```
src/
├── main/java/com/orangehrm/
│   ├── base/
│   │   ├── BasePage.java          # Shared page methods (click, type, waits)
│   │   ├── BaseTest.java          # Driver setup/teardown for every test
│   │   ├── LoggedInBaseTest.java  # Auto-logs in before test starts
│   │   └── DriverFactory.java     # ThreadLocal driver, local + remote support
│   ├── pages/
│   │   ├── LoginPage.java
│   │   ├── DashboardPage.java
│   │   └── PIMPage.java
│   └── utils/
│       ├── ConfigReader.java       # Reads config.properties
│       ├── WaitUtils.java          # Explicit wait helpers
│       ├── ScreenshotUtils.java    # Auto-screenshot on failure
│       └── ExtentReportManager.java
│
├── test/java/com/orangehrm/
│   ├── tests/
│   │   ├── LoginTest.java
│   │   └── EmployeeTest.java
│   ├── listeners/
│   │   └── TestListener.java       # Hooks for reporting + screenshots
│   └── testdata/
│       └── TestDataProvider.java   # TestNG @DataProvider for negative tests
│
├── test/resources/
│        ├── config.properties
│        ├── log4j2.xml
├── testng.xml                  # Default suite
├── grouping.xml                # Run by group (smoke, crud, etc.)
├── paralleltesting.xml         # Parallel browser runs
└── testing-docker.xml          # Grid / Docker execution
```

---

## ⚙️ Tech Stack

| Tool | Purpose |
|---|---|
| Java 11+ | Language |
| Selenium 4 | Browser automation |
| TestNG | Test runner |
| Page Object Model | Design pattern |
| ExtentReports | HTML test reports |
| Log4j2 | Logging |
| Apache Commons IO | Screenshot file handling |
| Docker + Selenium Grid | Remote / parallel execution |
| Maven | Build & dependency management |

---

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Chrome / Firefox / Edge (for local runs)
- Docker (for grid runs)

### Clone the repo

```bash
git clone https://github.com/SalunkeGaurav/OrangeHRM-Automation-Framework.git
cd OrangeHRM-Automation-Framework
```

### Configure the run

Edit `src/test/resources/config.properties`:

```properties
url=https://opensource-demo.orangehrmlive.com
browser=chrome          # chrome | firefox | edge
headless=false          # true for CI / headless runs
execution_mode=local    # local | remote
username=Admin
password=admin123
explicitWait=15
selenium_grid_url=http://localhost:4444/wd/hub   # only needed for remote
```

---

## ▶️ Running Tests

### Run the default suite
```bash
mvn test
```

### Run by group (smoke, login, crud, employee)
```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/grouping.xml
```

### Run in parallel across browsers
```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/paralleltesting.xml
```

### Run on Docker Selenium Grid
```bash
# Start the grid
docker-compose up -d

# Run tests targeting the grid
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testing-docker.xml

# Stop the grid when done
docker-compose down
```

---

## 📊 Test Reports

After a run, an HTML report is auto-generated in the `reports/` folder:

```
reports/
└── TestReport_20240315_143022.html
```

The report opens automatically in your browser after the suite finishes. It includes pass/fail status, logs, and screenshots for every failed test.

Screenshots for failures are saved to:
```
screenshots/
└── testName_20240315_143022.png
```

---

## 🧪 Test Suites & Groups

| XML File | What it runs |
|---|---|
| `testng.xml` | Full regression suite |
| `grouping.xml` | Filter by group tag (`smoke`, `login`, `crud`, `employee`, `negative`) |
| `paralleltesting.xml` | Parallel execution across browsers |
| `testing-docker.xml` | Remote execution via Selenium Grid |

---

## 🔀 Branches

| Branch | Description |
|---|---|
| `main` | Stable, production-ready tests |
| `testing-docker` | Docker / Selenium Grid integration work |

---

## 🧩 Key Design Decisions

**ThreadLocal WebDriver** — `DriverFactory` uses `ThreadLocal<WebDriver>` so parallel tests never share a driver instance.

**Two base test classes** — `BaseTest` sets up the browser. `LoggedInBaseTest` extends it and handles login automatically, so employee tests don't repeat the login step.

**Smart waits** — `WaitUtils` and `waitForLoaderToDisappear()` in `BasePage` handle OrangeHRM's loading spinners before every interaction, preventing flaky tests.

**Data-driven negative tests** — `TestDataProvider` feeds multiple bad credential combinations into `testInvalidLogin` using TestNG's `@DataProvider`, so one test method covers several scenarios.

**Auto-retry on delete** — `deleteEmployeeByFullName` retries up to 3 times, which handles OrangeHRM's occasional slow response after deletion.

---

## 👤 Author

**Gaurav Salunke**
[GitHub](https://github.com/SalunkeGaurav/OrangeHRM-Automation-Framework)

---

> Built as a portfolio project to demonstrate Selenium automation skills, Page Object Model design, and test framework architecture.

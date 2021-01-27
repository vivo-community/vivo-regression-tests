# vivo-regression-tests - release 2020-
For UQAM-DEV Environment: https://wiki.uqam.ca/display/VIVOPUB/3%29+vivo-regression-test%3A+a+Test+Bench+Tool++for+the+Continuous+Evaluation+of+VIVO%27s+Development#id-3)vivo-regression-test:aTestBenchToolfortheContinuousEvaluationofVIVO'sDevelopment-run_aRunningATestCaseintheTestBenchunderUQAM-DEV


## Setup

Update the `settings.xml` based on the usernames/passwords for your local VIVO installations and the appropriate Selenium driver for your platform.

## Build

```
mvn clean install -s settings.xml
```

## Run

```
mvn exec:java
```


# vivo-regression-tests - release 2020-04-30
vivo-regression-tests is a Selenium test bench for VIVO with testNG

# Prerequisite

The following prerequisites are necessary for the test bench to function properly:

- Installation of Firefox
- Configure `src/main/resources/runtime.properties` with appropriate credential of commands and `/src/test/resources/runtime.properties` for Tests
  (see also `runtime.properties_example`)
- Configure `log4j.properties` in respective directory for logging parametrization
- Vivo must be clean of sample data (The loading of the test data is assumed by each testcase.)
- see also  [ReleaseNote.md](ReleaseNote.md)  for more information

# Context

- In this release, the test cases concern the non-i18n version (VIVO-1.11.1) and the i18n version for the en_US
- The VIVO Instance is currently running and available at http://localhost:8080/vivo.

# RUN

## For VIVO-1.11.1 - non-i18n

In the root directory, run the command

```
mvn clean verify -Pcore_Test
```

## Run with VIVO with i18n setup

In the root directory, run the command

```
mvn clean verify -Pen_US_Test
```

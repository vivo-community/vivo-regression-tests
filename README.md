# vivo-regression-tests
vivo-regression-tests is a Selenium test bench for VIVO

## Release 2020-04-21

### Prerequisite

The following prerequisites are necessary for the test bench to function properly:

- Installation of Firefox
- The VIVO-1.11.1 (NON i18n) Instance is currently running and available at http://localhost:8080/vivo.
- Vivo must be clean of sample data (The loading of the test data is assumed by each testcase.)
- Configure `src/main/resources/runtime.properties` with appropriate credential of commands and `/src/test/resources/runtime.properties` for unitTests 
  (see also `runtime.properties_example`)
- Configure `log4j.properties` in respective directory for logging parametrization

### RUN

In the root directory, run the command

```
mvn clean install
```


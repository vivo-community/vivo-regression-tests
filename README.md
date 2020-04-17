# vivo-regression-tests
vivo-regression-tests is a Selenium test bench for VIVO

## First run

### Prerequisite

The following prerequisites are necessary for the test bench to function properly:

- Installation of Firefox
- The VIVO-1.11.1 Instance is currently running and available at http://localhost:8080/vivo.
- Vivo must be clean of sample data
- Configure `src/main/resources/runtime.properties` with appropriate credential
  (see also `runtime.properties_example`)

### RUN

In the root directory, run the command

```
mvn clean install
```


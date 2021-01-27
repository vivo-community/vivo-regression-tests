# vivo-regression-tests - release 2021-
For UQAM-DEV Environment: For UQAM-DEV Environment: https://wiki.uqam.ca/display/VIVOPUB/4%29+vivo-regression-test%3A+a+Test+Bench+Tool++for+the+Continuous+Evaluation+of+VIVO%27s+Development


## Setup

- rename `example.settings.xml` to `settings.xml` and Update the `settings.xml` based on the usernames/passwords for your local VIVO installations and the appropriate Selenium driver for your platform.
- in `./src/main/resources` rename `example.runtime.properties` to `runtime.properties` and `example.log4j.properties` to `log4j.properties`
- The list of tests is available under: ./src/main/resources/testsuites/
- Make sure that the password for `http://localhost:8080/vivo_orig is` correctly assigned. The first call to this instance requires the password `rootPasswd`. It is not necessary to do this procedure for `http://localhost:8080/vivo_i18n`.
## Build

```
mvn clean install -s settings.xml

```

## Run
### For all languages
```
mvn -s settings.xml clean test -Dxml.file=EmailAddress-testSuite.xml -DskipTests.value=false
```
### For specific language (eg.: fr_CA)
```
mvn -s settings.xml clean test -Dxml.file=fr_CA/EmailAddress-testSuite.xml -DskipTests.value=false
```


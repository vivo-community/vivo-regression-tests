# 2020-06-19

- Revision of the sample_fr ontology (addition of missing links on expertises)
- Replacement of Hard Timeout by Implicit Timeout
- Creation of fr_CA_EmailAddressUnitTest, fr_CA_HeadOfFacultyUnitTest, fr_CA_ResearchOverviewToPersonUnitTest
- DataProvider Implementation in the appropriated classes
- Work with i18n and non i18n instance of Vivo
- Documentation at https://wiki.uqam.ca/display/VIVOPUB/3%29+vivo-regression-test%3A+a+Test+Bench+Tool++for+the+Continuous+Evaluation+of+VIVO%27s+Development

# 2020-04-27

- Using Selenium with TestNG
- Implemented for both Classical VIVO-1.11.1 & VIVO-1.11.2 (i18n)
- Same UnitTest working for both VIVO's installation
- Choice of the test case by options passed to Maven

# 2020-04-21

## Adding Selenium testcase (add/modify/delete): 

- EmailAddressTest 
- HeadOfFacultyTest
- ResearchOverviewToPersonTest

## Adding Helper

- SeleniumHelper - Devoted to function standardization e.g.: login()
- SampleGraphUtil - Dedicated to the wrapping of specific functions for data manipulation in the triplestore

## Adding command

- PopulateSampleCommand - Utility command to load the samplefile into the tripletore. Other commands are to be expected e.g. cleanSample, DescribeURI, etc.




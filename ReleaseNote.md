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




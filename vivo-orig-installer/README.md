# vivo-orig-installer
This directory contains the requirements to install the original instance of VIVO without internationalization. It serves as a reference platform for comparing Selenuim tests. 

# Prerequisite

Make sure that the configurations are done correctly (see the vivo-config-proposal directory for the vivo_orig and solr configuration files).
For more details on how to install VIVO, see: https://wiki.lyrasis.org/display/VIVODOC111x/Installing+VIVO

# Installation for Windows
```
mvn clean -s vivo_orig_settings.xml install
```



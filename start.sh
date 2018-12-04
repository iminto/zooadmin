#!/bin/bash
mvn compiler:compile
mvn resources:resources
mvn dependency:copy-dependencies
java  -cp  $(echo target/dependency/*.jar | tr ' ' ':'):"target/classes" com.baicai.core.Main
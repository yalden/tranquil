#!/usr/bin/env bash
#author: yooonn


./mvnw clean verify -Pjacoco -f pom.xml -Dall-ut=true

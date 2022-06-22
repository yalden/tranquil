#!/usr/bin/env sh
# author: yooonn

set -eux

./mvnw generate-resources -f pom.xml -DskipAsciidoc=false

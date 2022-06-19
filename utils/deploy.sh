#!/usr/bin/env sh
# author: yooonn

RED=$(tput setaf 1)
GREEN=$(tput setaf 2)
BLUE=$(tput setaf 4)
RESET=$(tput sgr0)

#set -eux
set -eux

./mvnw clean deploy -Prelease
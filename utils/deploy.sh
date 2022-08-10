#!/usr/bin/env bash
# author: yooonn

RED=$(tput setaf 1)
GREEN=$(tput setaf 2)
BLUE=$(tput setaf 4)
RESET=$(tput sgr0)

greenText() {
    echo -e "${GREEN}$1${RESET}"
}

redText() {
    echo -e "${RED}$1${RESET}"
}

set -eu

read -r -p "Will execute './mvnw clean deploy -Prelease' \
$(redText 'Are You Sure?') [Y/n] " input

case $input in
[yY][eE][sS] | [yY])
    greenText "Yes"
    ./mvnw clean deploy -Prelease
    greenText '\nDone'
    exit 0
    ;;

*)
    greenText "Stopped..."
    exit 0
    ;;
esac

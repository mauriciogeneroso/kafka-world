#!/bin/sh

APPS=("app-1" "app-2" "app-3" "app-4")
./shared-script.sh docker-compose-streams.yml streams "${APPS[@]}"

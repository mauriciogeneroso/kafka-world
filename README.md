# Kafka World

This repository contains examples using a Spring boot Java Application and Kafka. 

This repository is part of a presentation and used to demonstrate a few behaviours. 

## Docker files

This repository contains docker compose files to set up everything. 

- `./kafka/docker-compose.yaml`
  - This file contains the containers for `zookeeper`, `kafka broker` and `kafka ui`
- `./tools/docker-compose.yaml`
  - This file contains the containers for `prometheus` and `grafana`
- `./docker-compose-classic.yaml`
  - This file imports the config from kafka and tools [folders] and set up the consumers as a normal Kafka Consumer.

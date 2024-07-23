#!/bin/bash
docker run -it --name eureka-service -p 8761:8761 \
      -e EUREKA_HOST="172.17.0.1" \
      -d eureka-service
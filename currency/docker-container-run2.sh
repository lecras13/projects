#!/bin/bash
docker run -it --name currency-service-2 -p 8087:8085 -h currency-service-2 \
      -e EUREKA_HOST="172.17.0.1" \
      -d currency-service
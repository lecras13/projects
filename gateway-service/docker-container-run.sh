#!/bin/bash
docker run -it --name api-gateway-service -p 8080:8080 \
      -e EUREKA_HOST="172.17.0.1" \
      -e PROCESSING_URL="http://172.17.0.1:8090" \
      -e CURRENCY_URL="http://172.17.0.1:8085" \
      -d api-gateway-service
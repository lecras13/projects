#!/bin/bash
docker run --name processing-service -p 8090:8090 \
      -e EUREKA_HOST="172.17.0.1" \
      -e AUTH_TOKEN_URL="http://172.17.0.1:9000/oauth/check_token" \
      -e USER_INFO_URL="http://172.17.0.1:9000/user" \
      -d processing-service
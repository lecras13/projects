FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine
ADD target/*.jar app.jar

ENV EUREKA_HOST=localhost
ENV AUTH_TOKEN_URL="http://localhost:9000/oauth/check_token"
ENV USER_INFO_URL="http://localhost:9000/user"

EXPOSE 8090

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Duser.country=RU -Duser.language=ru -Dspring.profiles.active=cloud \
            -jar /app.jar \
            --cloud.eureka-host=$EUREKA_HOST \
            --spring.datasource.host=$EUREKA_HOST \
            --cloud.auth-token-url=$AUTH_TOKEN_URL \
            --cloud.user-info-url=$USER_INFO_URL \
            " ]

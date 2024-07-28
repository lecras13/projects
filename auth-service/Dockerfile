FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine
ADD target/*.jar app.jar

ENV EUREKA_HOST=localhost

EXPOSE 9000

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Duser.country=RU -Duser.language=ru -Dspring.profiles.active=cloud \
            -jar /app.jar \
            --cloud.eureka-host=$EUREKA_HOST \
            " ]

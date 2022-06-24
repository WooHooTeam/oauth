FROM openjdk:8-jdk-alpine
ADD ./deploy/oauth-0.0.1-SNAPSHOT.jar app.jar
ADD wallet.tar.gz /var/oracle
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/app.jar"]

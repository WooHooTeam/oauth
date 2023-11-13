FROM openjdk:8-jdk-alpine
ADD ./deploy/oauth-0.0.2-SNAPSHOT.jar app.jar
RUN mkdir /var/oracle
COPY wallet.tar.gz /tmp/
RUN tar -xzf /tmp/wallet.tar.gz -C /var/oracle/
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/app.jar"]

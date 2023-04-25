#FROM openjdk:8u191-jre-alpine3.9
FROM openjdk:8-jre-alpine
#FROM openjdk:8u322-jdk-oraclelinux7

RUN apk add curl jq

#Workspace
WORKDIR /usr/share/stkDemo

#ADD .jar under target from host
# into this image

ADD ./target/SeleniumFrameworkWeb.jar         SeleniumFrameworkWeb.jar          
ADD ./target/SeleniumFrameworkWeb-tests.jar   SeleniumFrameworkWeb-tests.jar          
ADD ./target/libs                                  libs
#Selenium Resources
ADD ./src/test/resources                           src/test/resources
ADD ./healthcheck.sh							   healthcheck.sh 

# without healthcheck 
#ENTRYPOINT java -cp SeleniumFrameworkWeb.jar:SeleniumFrameworkWeb-tests.jar:libs/*  org.testng.TestNG $MODULE 
ENTRYPOINT sh healthcheck.sh 

FROM openjdk:8u191-jre-alpine3.8

# Workspace
WORKDIR /usr/share/autoui

#ADD .jar under target from host
# into this image

ADD target /src/tmp
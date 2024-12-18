FROM amazoncorretto:17-alpine

RUN mkdir /apps

COPY build/libs/streams.jar /apps/streams.jar

RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

RUN apk add --no-cache libstdc++

ENTRYPOINT exec java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar /apps/streams.jar

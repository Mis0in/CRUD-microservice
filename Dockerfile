#base image
FROM amazoncorretto:21-alpine-jdk

#create work directory
WORKDIR /app

#copy app to docker container
COPY . .

RUN ./gradlew clean build -x test

#set port env
ENV PORT=8080

#expose the port so computer can access it
EXPOSE 8080

CMD ["java", "-jar", "./build/libs/taskhandler-0.0.1-SNAPSHOT.jar"]
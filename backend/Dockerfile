# Use a JDK base image
FROM amazoncorretto:21.0.7 AS build
WORKDIR /app

# Copy and build the app
COPY . .
RUN sed -i 's/\r$//' ./gradlew
RUN chmod +x ./gradlew
RUN sed -i 's/\r$//' ./gradlew
RUN ./gradlew :app:build -x test



#Use a lighter image for runtime
FROM amazoncorretto:21.0.7-alpine
WORKDIR /app

COPY --from=build /app/app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

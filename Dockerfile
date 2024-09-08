FROM openjdk:17-jdk-slim
COPY --chown=app:app build/libs/*.jar /libs/app.jar

# just for test
# for real: we can setup database info via config map, and load password using secret
# pass database info to connect
ENV DATABASE_URL=jdbc:postgresql://db:5432/customer_app_db
ENV DATABASE_USER=admin
ENV DATABASE_PASSWORD=admin
ENTRYPOINT ["java", "-jar", "/libs/app.jar"]
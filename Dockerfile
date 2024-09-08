FROM openjdk:17-jdk-slim

# Install dependencies to be able to use Gradle
RUN apt-get update && apt-get install -y curl unzip

# Install Gradle 7.5
ARG GRADLE_VERSION=7.5
RUN curl -sLO https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip && \
    mv gradle-${GRADLE_VERSION} /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle && \
    rm gradle-${GRADLE_VERSION}-bin.zip

# Set environment variables for Gradle
ENV GRADLE_HOME /opt/gradle
ENV PATH $GRADLE_HOME/bin:$PATH

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

RUN mkdir -p /libs && cp build/libs/*.jar /libs/app.jar

# just for test
# for real: we can setup database info via config map, and load password using secret
# pass database info to connect
ENV DATABASE_URL=jdbc:postgresql://db:5432/customer_app_db
ENV DATABASE_USER=admin
ENV DATABASE_PASSWORD=admin

# Run the application
ENTRYPOINT ["java", "-jar", "/libs/app.jar"]
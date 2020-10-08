FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} oasis_api.jar
EXPOSE 8081
CMD ["java", "jar", "./oasis_api.jar"]
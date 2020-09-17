FROM openjdk:8
COPY jmetal-webPage-0.1.0.jar /usr/src
COPY wait-for-it.sh /usr/src	
RUN chmod +x /usr/src/wait-for-it.sh
WORKDIR /usr/src
CMD ["java", "-jar", "jmetal-webPage-0.1.0.jar"]
# Use a Linux image with Tomcat 9 (javax.servlet compatible)
FROM tomcat:9.0-jdk11-openjdk-slim

# Copy in our ROOT.war to the right place in the container
COPY ROOT.war /usr/local/tomcat/webapps/
FROM payara/micro:jdk11
RUN wget -O ./database-connector.jar https://repo1.maven.org/maven2/org/apache/derby/derbyclient/10.15.2.0/derbyclient-10.15.2.0.jar
#COPY target/RosterService-1.0-SNAPSHOT.war $DEPLOY_DIR
# Adds an application to be loaded
ADD target/RosterService-1.0-SNAPSHOT.war ROOT.war
ENTRYPOINT java -jar payara-micro.jar --addJars ./database-connector.jar --deploy ROOT.war

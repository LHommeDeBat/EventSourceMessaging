echo "Build EventSourceMessaging"
mvn clean install -DskipTests
echo "Removing quantum service container..."
docker container rm -f eventsourcefilesystem
echo "Removing quantum service image..."
docker image rm -f eventsourcefilesystem

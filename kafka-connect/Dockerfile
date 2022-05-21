FROM debezium/connect-base:1.9
#https://github.com/debezium/docker-images/blob/main/connect-base/1.9/Dockerfile

ENV DEBEZIUM_VERSION="1.9.2.Final" \
    MAVEN_REPO_CENTRAL="" \
    MAVEN_REPOS_ADDITIONAL="" \
    MAVEN_DEP_DESTINATION=$KAFKA_CONNECT_PLUGINS_DIR \
    MYSQL_MD5=2f43e2e0108cf62ade1811d9442ad24b \
    SCRIPTING_MD5=22f7b63f840ce8b4499bdd871935d4af

RUN docker-maven-download debezium mysql "$DEBEZIUM_VERSION" "$MYSQL_MD5" && \
    docker-maven-download debezium-optional scripting "$DEBEZIUM_VERSION" "$SCRIPTING_MD5" \
    docker-maven-download central org/apache/kafka/connect-transforms connect-transforms 3.2.0 54fb74e3d59804c78262051b9e13c36d

ADD target/transform-4.2.jar $KAFKA_CONNECT_PLUGINS_DIR

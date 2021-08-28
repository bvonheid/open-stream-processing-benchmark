FROM openjdk:8-slim

# data to ingest
COPY src/main/resources/data/ /data
ENV LOCAL_PATH=/data/time*/*

# executable
ADD target/scala-2.11/ospbench-data-stream-generator-assembly-3.0.jar /

# execute
ENTRYPOINT ["java","-jar","ospbench-data-stream-generator-assembly-3.0.jar"]

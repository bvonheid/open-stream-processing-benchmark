FROM bde2020/spark-submit:3.0.2-hadoop3.2

WORKDIR /app

ENV ENABLE_INIT_DAEMON false
ENV SPARK_APPLICATION_JAR_LOCATION /app/structured-streaming-benchmark-assembly-3.0.jar
ENV SPARK_APPLICATION_MAIN_CLASS structuredstreaming.benchmark.StructuredStreamingTrafficAnalyzer

COPY target/scala-2.12/structured-streaming-benchmark-assembly-3.0.jar /app

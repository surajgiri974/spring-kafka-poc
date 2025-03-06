# Kafka Producer & Comsumer Spring Boot Example

## Documents

### Open Source Kafka Startup in Local

1. **Start Zookeeper Server**
    ```sh
    bin/zookeeper-server-start.sh config/zookeeper.properties
    ```

2. **Start Kafka Server / Broker**
    ```sh
    bin/kafka-server-start.sh config/server.properties
    ```

3. **Create Topic**
    ```sh
    bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic NewTopic --partitions 3 --replication-factor 1
    ```

4. **List All Topic Names**
    ```sh
    bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
    ```

5. **Describe Topics**
    ```sh
    bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic NewTopic
    ```

6. **Produce Message**
    ```sh
    bin/kafka-console-producer.sh --broker-list localhost:9092 --topic NewTopic
    ```

7. **Consume Message**
    ```sh
    bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic NewTopic --from-beginning
    ```

---

### Confluent Kafka Community Edition in Local

1. **Start Zookeeper Server**
    ```sh
    bin/zookeeper-server-start etc/kafka/zookeeper.properties
    ```

2. **Start Kafka Server / Broker**
    ```sh
    bin/kafka-server-start etc/kafka/server.properties
    ```

3. **Create Topic**
    ```sh
    bin/kafka-topics --bootstrap-server localhost:9092 --create --topic NewTopic1 --partitions 3 --replication-factor 1
    ```

4. **List All Topic Names**
    ```sh
    bin/kafka-topics --bootstrap-server localhost:9092 --list
    ```

5. **Describe Topics**
    ```sh
    bin/kafka-topics --bootstrap-server localhost:9092 --describe --topic NewTopic1
    ```

6. **Produce Message**
    ```sh
    bin/kafka-console-producer --broker-list localhost:9092 --topic NewTopic1
    ```

7. **Consume Message**
    ```sh
    bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic NewTopic1 --from-beginning
    ```

8. **Send CSV File Data to Kafka**
    ```sh
    bin/kafka-console-producer --broker-list localhost:9092 --topic NewTopic1 < bin/customers.csv
    ```

### REST API Endpoints for Kafka Producer

Use the following endpoints with a running Spring Boot application:

1. **Send a Message to Kafka Topic**
    ```sh
    curl -X POST http://localhost:8080/producer/send/{message}
    ```

2. **Create a New Kafka Topic**
    ```sh
    curl -X POST http://localhost:8080/producer/create-topic/{name}/{partition}
    ```

Replace `{message}`, `{name}`, and `{partition}` with appropriate values.

### Additional Tool: Offset Explorer

To manage Kafka topics and messages, you can use the **Offset Explorer** tool:
- Download: [Offset Explorer](https://www.kafkatool.com/download.html)
- Ensure Zookeeper and Kafka are running before using Offset Explorer:
    ```sh
    .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
    .\bin\windows\kafka-server-start.bat .\config\server.properties
    ```
- Run the above commands in two separate command prompt windows within your Kafka installation directory.


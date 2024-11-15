# Kafka启停脚本

```shell
#! /bin/bash

KAFKA_HOME="/opt/stanlong/kafka/kafka"
case $1 in
"start"){
        for i in node01 node02 node03 node04
        do
                echo " --------启动 $i Kafka-------"
                # 用于KafkaManager监控
                ssh $i "export JMX_PORT=9988 && $KAFKA_HOME/bin/kafka-server-start.sh -daemon $KAFKA_HOME/config/server.properties"
        done
};;
"stop"){
        for i in node01 node02 node03 node04
        do
                echo " --------停止 $i Kafka-------"
                ssh $i "$KAFKA_HOME/bin/kafka-server-stop.sh stop"
        done
};;
esac
```


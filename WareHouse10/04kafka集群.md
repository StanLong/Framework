# kafka集群

kafka安装参考 Hadoop\17Kafka

## kafka集群启动脚本

```shell
[root@node01 appmain]# pwd
/opt/stanlong/appmain
[root@node01 appmain]# vi fc.sh 

#! /bin/bash
# 注意等号左右不能有空格
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
[root@node01 appmain]# chmod +x kf.sh
```

说明：启动Kafka时要先开启JMX端口，是用于后续KafkaManager监控

## 创建topic

```shell
[root@node01 kafka]# pwd
/opt/stanlong/kafka/kafka
```

**创建启动日志主题**

```shell
bin/kafka-topics.sh --zookeeper node01:2181,node02:2181,node03:2181,node04:2181 --create --replication-factor 3 --partitions 1 --topic topic-start
```

**创建事件日志主题**

```shell
bin/kafka-topics.sh --zookeeper node01:2181,node02:2181,node03:2181,node04:2181 --create --replication-factor 3 --partitions 1 --topic topic-event
```

## 查看topic

```shell
bin/kafka-topics.sh --zookeeper node01:2181,node02:2181,node03:2181,node04:2181 --list
```

## 查看topic详情

```shell
bin/kafka-topics.sh --zookeeper node01:2181,node02:2181,node03:2181,node04:2181  --describe --topic topic-start
```

## 删除topic

删除启动日志主题

```shell
bin/kafka-topics.sh --delete --zookeeper node01:2181,node02:2181,node03:2181,node04:2181 --topic topic-start
```

删除事件日志主题

```shell
bin/kafka-topics.sh --delete --zookeeper node01:2181,node02:2181,node03:2181,node04:2181 --topic topic-event
```

## kafka生产者

```shell
bin/kafka-console-producer.sh --broker-list node01:9092,node02:9092,node03:9092,node04:9092 --topic topic-event
```

## kafka消费者

```shell
bin/kafka-console-consumer.sh --bootstrap-server node01:9092,node02:9092,node03:9092,node04:9092 --from-beginning --topic topic-event
```




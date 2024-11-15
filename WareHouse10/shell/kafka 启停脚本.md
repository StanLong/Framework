# kafka 启停脚本

```shell
#!/bin/bash

case $1 in
"start"){
        for i in node01 node02 node03 node04
        do
                echo "***********$i***********"
                ssh $i "export JMX_PORT=9988 && /opt/stanlong/kafka/bin/kafka-server-start.sh -daemon /opt/stanlong/kafka/config/server.properties"
        done
};;
"stop"){
        for i in node01 node02 node03 node04
        do
                echo "***********$i***********"
                ssh $i "/opt/stanlong/kafka/bin/kafka-server-stop.sh"
        done
};;
esac
```

启动kafka时要开启JMX端口，时用于后续kafkamanager监控

jmx端口主要用来监控kafka集群的。

在启动kafka的脚本kafka-server-start.sh中找到堆设置，添加export JMX_PORT="9999"

```
if [ "x$KAFKA_HEAP_OPTS" = "x" ]; then
    export KAFKA_HEAP_OPTS="-Xmx1G -Xms1G"
    export JMX_PORT="9999"
fi
```

或者在启动脚本中添加如下：

```
#!/bin/sh
JMX_PORT=9999 ./bin/kafka-server-start.sh -daemon config/server.properties
```

这样在kafka-manager中就不会看到出错信息了。
# Kafka Manager启停脚本

```shell
#! /bin/bash

case $1 in
"start"){
        echo " -------- 启动 KafkaManager -------"
        nohup /opt/stanlong/kafka-manager/bin/kafka-manager -Dhttp.port=7456 >/opt/stanlong/kafka-manager/logs/start.log 2>&1 &
};;
"stop"){
        echo " -------- 停止 KafkaManager -------"
        ps -ef | grep ProdServerStart | grep -v grep |awk '{print $2}' | xargs kill 
};;
esac
```


# 日志消费flume启停脚本

```shell
case $1 in
"start"){
        for i in node03 node04
        do
                echo " --------启动 $i 消费flume-------"
                ssh $i "nohup /opt/stanlong/flume/bin/flume-ng agent --conf-file /opt/stanlong/flume/conf/kafka-flume-hdfs.conf --name a1 -Dflume.root.logger=INFO,LOGFILE >/opt/stanlong/flume/logs/log.txt   2>&1 &"
        done
};;
"stop"){
        for i in node03 node04
        do
                echo " --------停止 $i 消费flume-------"
                ssh $i "ps -ef | grep kafka-flume-hdfs | grep -v grep |awk '{print \$2}' | xargs kill"
        done

};;
esac
```


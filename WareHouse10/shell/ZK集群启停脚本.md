# ZK集群启停脚本

```she
#! /bin/bash

case $1 in
"start"){
	for i in node02 node03 node04
	do
		ssh $i "/opt/stanlong/zookeeper-3.4.11/bin/zkServer.sh start"
	done
};;
"stop"){
	for i in node02 node03 node04
	do
		ssh $i "/opt/stanlong/zookeeper-3.4.11/bin/zkServer.sh stop"
	done
};;
"status"){
	for i in node02 node03 node04
	do
		ssh $i "/opt/stanlong/zookeeper-3.4.11/bin/zkServer.sh status"
	done
};;
esac

```


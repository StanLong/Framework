# 启动Hadoop-HA

**Hadoop-HA启停脚本**

```shell
#!/bin/bash
if [ $# -lt 1 ];then
    echo "Please input Operation: start or stop"
fi

start() { 
    #启动hadoop ha集群
    echo "----------------启动zk集群------------------"
    for i in node{02..04}
    do
            ssh $i zkServer.sh start
    done

    #启动hadoop ha集群
    echo "----------------sleep10 确保zookeeper全部正常启动------------------"
    for i in {10..2}
    do
            echo  -n  "$i "         ###echo -n 不换行输出
            sleep 1
    done
    echo "1"

    
    echo "----------------检查zk状态------------------"
    for i in node{02..04}
    do
            ssh $i zkServer.sh status
    done

    echo "----------------启动HDFS------------------"
    start-dfs.sh
    
    echo "----------------启动Yarn------------------"
    start-yarn.sh
    
    echo "----------------启动历史服务------------------"
    ssh node03 "mapred --daemon start historyserver"
    
    echo "----------------集群各节点进程状态------------------"
    echo "node01"
    jps
    for i in node{02..04}
    do
            echo "$i"
            ssh $i jps
    done

}

stop() { 

    echo "----------------关闭历史服务------------------"
    ssh node03 "mapred --daemon stop historyserver"

    
    echo "----------------关闭Yarn------------------"
    stop-yarn.sh

    echo "----------------关闭DFS------------------"
    stop-dfs.sh


    echo "----------------关闭zk集群-----------------"
    for i in node{02..04}
    do
            ssh $i zkServer.sh stop
    done

    echo "----------------检查zk状态------------------"
    for i in node{02..04}
    do
            ssh $i zkServer.sh status
    done


    echo "----------------集群关机------------------"
    for i in node{02..04}
    do
            ssh $i poweroff
    done
    poweroff
} 

case "$1" in 
    start) 
        $1 
        ;; 
    stop) 
        $1 
        ;; 
    *)    
      echo $"Usage: $0 {start|stop}" 
        exit 2 
esac
```


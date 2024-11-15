# 集群所有进程查看脚本

```shell
#! /bin/bash

for i in node01 node02 node03 node04
do
        echo --------- $i ----------
        ssh $i "$*"
done
```


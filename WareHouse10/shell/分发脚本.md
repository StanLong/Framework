# 分发脚本

安装 rsync 工具(所有节点都要安装)

```shell
[root@node01 stanlong]# yum install rsync -y
```

启动rsync服务

```shell
[root@node01 stanlong]# systemctl start rsyncd.service
[root@node01 stanlong]# systemctl enable rsyncd.service
```

```shell
#!/bin/bash
#获取输入参数的个数.没有参数直接退出
pcount=$#
if((pcount==0));then
echo no args;
exit;
fi

#2.获取文件名称
p1=$1
fname=`basename $p1`
echo fname=$fname

#3.获取上级目录到绝对路径
pdir=`cd -P $(dirname $p1); pwd`
echo pdir=$pdir

#4.获取当前用户名称
user=`whoami`

#5.循环
for i in node02 node03 node04
do
        echo ----------------$i------------------
        rsync -av $pdir/$fname $user@$i:$pdir
done
```




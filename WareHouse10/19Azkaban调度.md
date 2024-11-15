# Azkaban调度

azkaban的安装及使用参考 26Azkaban

这里简单记录两个

ods_log.job

```shell
type=command
command=/opt/stanlong/appmain/ods_log.sh 2021-02-27
```

dwd_start_log.job

```shell
type=command
command=/opt/stanlong/appmain/dwd_start_log.sh ${pt_d}
dependencies=ods_log
```




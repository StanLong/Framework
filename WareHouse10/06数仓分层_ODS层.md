# 数仓分层

## 数仓分层

ODS层：原始数据层，存放原始数据，直接加载原始数据，数据保持原貌不做处理

DWD层：结构和粒度与原始表保持一致，对ODS层数据进行清洗（去除空值，脏数据）

DWS层：以DWD为基础，进行轻度汇总

ADS层：为各种统计报表提供数据

## 优点

- 数据隔离

  每一个数据分层都有它的作用域和职责，在使用表的时候能更方便地定位和理解

- 减少重复开发

  规范数据分层，开发一些通用的中间层数据，能减少极大的重复计算

- 复杂问题简单化

  将一个复杂的任务分解成多个步骤来完成，每一层解决特定的问题

## 数仓命名规范

 ODS层命名为ods

 DWD层命名为dwd

 DWS层命名为dws

 ADS层命名为ads

 临时表数据库命名为xxx_tmp

 备份数据数据库命名为xxx_bak

## 搭建ODS层

### 创建gmall数据库

```sql
0: jdbc:hive2://node01:10000> create database gmall;
```

整个数仓操作都在gmall下执行。

### 启动日志表

**建表**

```sql
drop table if exists ods_start_log;
CREATE EXTERNAL TABLE ods_start_log (`line` string)
PARTITIONED BY (`pt_d` string)
STORED AS
  INPUTFORMAT 'com.hadoop.mapred.DeprecatedLzoTextInputFormat'
  OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION '/warehouse/gmall/ods/ods_start_log';
```

注意：时间格式都配置成YYYY-MM-DD格式，这是Hive默认支持的时间格式

**加载数据**

```sql
load data inpath '/origin_data/gmall/log/topic-start/2021-02-19' into table gmall.ods_start_log partition(pt_d='2021-02-19');
```

**查询**

```sql
select * from ods_start_log limit 2;
```

### 事件日志表

**建表**

```sql
drop table if exists ods_event_log;
CREATE EXTERNAL TABLE ods_event_log(`line` string)
PARTITIONED BY (`pt_d` string)
STORED AS
  INPUTFORMAT 'com.hadoop.mapred.DeprecatedLzoTextInputFormat'
  OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION '/warehouse/gmall/ods/ods_event_log';
```

**加载数据**

```sql
load data inpath '/origin_data/gmall/log/topic-event/2021-02-19' into table gmall.ods_event_log partition(pt_d='2021-02-19');
```

**查询**

```sql
select * from ods_event_log limit 2;
```

### ODS层加载数据脚本

```shell
[root@node01 appmain]# pwd
/opt/stanlong/appmain
[root@node01 appmain]# vi ods_log.sh
```

```shell
#!/bin/bash

# 定义变量方便修改
APP=gmall
hive=/opt/stanlong/hive/apache-hive-1.2.2-bin/bin/hive

# 如果是输入的日期按照取输入日期；如果没输入日期取当前时间的前一天
if [ -n "$1" ] ;then
   do_date=$1
else 
   do_date=`date -d "-1 day" +%F`
fi 

echo "===日志日期为 $do_date==="
sql="
load data inpath '/origin_data/gmall/log/topic-start/$do_date' into table "$APP".ods_start_log partition(pt_d='$do_date');

load data inpath '/origin_data/gmall/log/topic-event/$do_date' into table "$APP".ods_event_log partition(pt_d='$do_date');
"

$hive -e "$sql"
```

```shell
[root@node01 appmain]# chmod +x ods_log.sh
```

说明1：

[ -n 变量值 ] 判断变量的值，是否为空

变量的值非空，返回true

变量的值为空，返回false
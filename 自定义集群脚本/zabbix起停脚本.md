# zabbix启停脚本

```shell
#!/bin/bash

case $1 in
"start"){
    echo "***********启动zabbix集群***********"
    ssh node01 "systemctl start zabbix-server zabbix-agent httpd rh-php72-php-fpm"
    ssh node02 "systemctl start zabbix-agent"
    ssh node03 "systemctl start zabbix-agent"
    sleep 3s
};;
"stop"){
    echo "***********关闭zabbix集群***********"
    ssh node01 "systemctl stop zabbix-server zabbix-agent httpd rh-php72-php-fpm"
    ssh node02 "systemctl stop zabbix-agent"
    ssh node03 "systemctl stop zabbix-agent"
};;
"status"){
    echo "***********zabbix集群状态***********"
    echo "***********node01***********"
    ssh node01 "systemctl status zabbix-server zabbix-agent httpd rh-php72-php-fpm"
    sleep 1s
    echo "***********node02***********"
    ssh node02 "systemctl status zabbix-agent"
    sleep 1s
    echo "***********node03***********"
    ssh node03 "systemctl status zabbix-agent"
};;

esac
```


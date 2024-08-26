# Anaconda

# 一、Anaconda安装及配置下载源

1. 环境信息

   系统centos7.4

2. anacodna版本

   anaconda 各版本下载站： https://repo.anaconda.com/archive/

3. 安装

   ```shell
   [root@node01 opt]# wget https://repo.anaconda.com/archive/Anaconda3-5.3.1-Linux-x86_64.sh --no-check-certificate
   100%[========================================================================================>] 667,976,437 4.74MB/s   in 3m 13s 
   
   [root@node01 opt]# ll
   total 652324
   -rw-r--r-- 1 root root 667976437 2018-11-19 20:00:13 Anaconda3-5.3.1-Linux-x86_64.sh
   [root@node01 opt]# sh Anaconda3-5.3.1-Linux-x86_64.sh 
   ```

4. 配置Anaconda环境变量

   ```shell
   [root@node01 ~]# cat .bashrc
   export anaconda3=/root/anaconda3/bin
   export PATH=$PATH:anaconda3
   
   [root@node01 ~]# conda --version
   conda 4.5.11
   ```

5. 配置Anaconda源

   ```shell
   conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
   conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main/
   conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/pytorch/
   conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/msys2/
   conda config --set show_channel_urls yes
   
   配置的anaconda安装源文件默认是保存在 家目录下的 .condarc 文件
   ```

6. 新建Anaconda环境

   ```shell
   conda create -n conda3 python=3.9
   
   ```

   




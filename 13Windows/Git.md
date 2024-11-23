# Git 版本更新

直接打开git-bash.exe，输入`git update-git-for-windows`

# git 相关命令整理

git log --name-status 每次修改的文件列表, 显示状态

git log --name-only 每次修改的文件列表

git log --stat 每次修改的文件列表, 及文件修改的统计

git whatchanged 每次修改的文件列表

git whatchanged --stat 每次修改的文件列表, 及文件修改的统计

git show 显示最后一次的文件改变的具体内容

git show -5 显示最后 5 次的文件改变的具体内容

git show commitid 显示某个 commitid 改变的具体内容

# 解决git status中文乱码问题

参考地址： https://zhuanlan.zhihu.com/p/452682481

产生乱码的原因： 在默认设置下，中文文件名在工作区状态输出，中文名不能正确显示，而是显示为八进制的字符编码。

step1:解决git bash 终端显示中文乱码

将git 配置文件 `core.quotepath`项设置为false。quotepath表示引用路径， 加上`--global`表示全局配置

git bash 终端输入命令：

```text
git config --global core.quotepath false
```


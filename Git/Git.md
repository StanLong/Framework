# [Git 版本更新（Windows下）](https://www.cnblogs.com/shi-win-snoopy/p/12811818.html)

直接打开git-bash.exe，输入`git update-git-for-windows`



**git 查看最近或某一次提交修改的文件列表相关命令整理**。

git log --name-status 每次修改的文件列表, 显示状态
git log --name-only 每次修改的文件列表
git log --stat 每次修改的文件列表, 及文件修改的统计
git whatchanged 每次修改的文件列表
git whatchanged --stat 每次修改的文件列表, 及文件修改的统计
git show 显示最后一次的文件改变的具体内容
git show -5 显示最后 5 次的文件改变的具体内容
git show commitid 显示某个 commitid 改变的具体内容
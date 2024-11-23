# xftp6提示要继续使用此程序,您必须应用最新的更新

## 解决办法：

本机安装ultraedit、notepad++（需要先安装HEX-Editor插件）等能读写十六进制的编辑器
右键xftp6快捷方式，选择打开文件所在位置，
使用ultraedit或者notepad++等（hex）模式打开该目录下的文件nslicense.dll
ctrl+f，查找7F0C 81F9 8033 E101，将紧随其后的0F86，替换为0F83，保存文件，有时可能需要管理员身份运行编辑器来覆盖保存修改后的文件
重新双击打开xftp6即可。
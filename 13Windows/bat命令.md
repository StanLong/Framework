# bat命令

1. 拷贝所有 .txt 结尾的文件内容到同一个文件里

   ```
   C:\Users\Administrator> copy *.txt all.txt
   ```

2. 在windows可以通过dos的dir命令实现将目录下的文件名输出到指定文件：

   ```java
   如 dir /B/OE > jar.txt 
   /B:表示去掉日期，路径等信息，只保留文件名和扩展名
   /OE:以扩展名分类列出文件名信息
   >:重定向，表示把控制台的内容输出到后面指定的文件中
   ```

   
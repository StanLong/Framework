package com.stanlong.xml;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 修改xml文档
 * 标签
 * 属性
 * 文本
 */
public class Dom4j {
    public static void main(String[] args) throws Exception{
        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));

        // 指定文件的输出位置
        FileOutputStream outputStream = new FileOutputStream("D:/StanLong/git_repository/Framework/XML/xml/Dom4jProject/src/resources/output.xml");

        // 指定写出的格式
        // OutputFormat format = OutputFormat.createCompactFormat(); // 创建一个紧凑的格式， 没有空格和换行
        OutputFormat format = OutputFormat.createPrettyPrint();  // 创建一个好看的格式
        format.setEncoding("utf-8");

        // 创建写出对象
        // XMLWriter writer = new XMLWriter(outputStream); // 不带格式
        XMLWriter writer = new XMLWriter(outputStream, format); // 带格式

        // 写出对象
        writer.write(doc);

        // 关闭流
        writer.close();



    }
}

# Dom4j修改xml

# 输出文档

```java
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
```

# 修改文档

```java
package com.stalong;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

public class XmlTest {

    /**
     * 修改 Document
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        // 创建文档
        Document doc = DocumentHelper.createDocument();

        // 增加标签
        Element rootElem = doc.addElement("contactList");
        Element contactElem = rootElem.addElement("contact");
        Element nameElem = contactElem.addElement("name");

        // 增加属性
        nameElem.addAttribute("id", "001");

        // 输出Document对象到xml文件中
        FileOutputStream outputStream = new FileOutputStream("D:/StanLong/git_repository/Framework/XML/xml/Dom4jProject/src/resources/output.xml");
        OutputFormat format = OutputFormat.createPrettyPrint();  // 创建一个好看的格式
        format.setEncoding("utf-8");
        XMLWriter writer = new XMLWriter(outputStream, format); // 带格式
        writer.write(doc);
        writer.close();

    }

    /**
     * 修改属性值，文本
     */
    @Test
    public void test2() throws Exception{
        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));

        // 修改属性值: 方式一
        // 得到标签对象
//        Element contactElem = doc.getRootElement().element("contact");
//        // 得到属性对象
//        Attribute idAttr = contactElem.attribute("id");
//        // 修改属性值
//        idAttr.setValue("003");

        // 修改属性值: 方式二
        // 通过增加同名的属性来修改属性
        Element contactElem = doc.getRootElement().element("contact");
        contactElem.addAttribute("id", "004");

        // 修改文本
        Element nameElem = doc.getRootElement().element("contact").element("name");
        nameElem.setText("张三三");

        // 输出Document对象到xml文件中
        FileOutputStream outputStream = new FileOutputStream("D:/StanLong/git_repository/Framework/XML/xml/Dom4jProject/src/resources/output.xml");
        OutputFormat format = OutputFormat.createPrettyPrint();  // 创建一个好看的格式
        format.setEncoding("utf-8");
        XMLWriter writer = new XMLWriter(outputStream, format); // 带格式
        writer.write(doc);
        writer.close();

    }

    @Test
    public void test3() throws Exception{

        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));

        // 删除标签
        Element ageElem = doc.getRootElement().element("contact").element("age");
        // 方式一
        // ageElem.detach();
        // 方式二
        ageElem.getParent().remove(ageElem);
        
        // 删除属性
        Element contactElem1 = doc.getRootElement().elements().get(1);
        Attribute idAttr = contactElem1.attribute("id");
        idAttr.detach();
        // idAttr.getParent().remove(idAttr);


        // 输出Document对象到xml文件中
        FileOutputStream outputStream = new FileOutputStream("D:/StanLong/git_repository/Framework/XML/xml/Dom4jProject/src/resources/output.xml");
        OutputFormat format = OutputFormat.createPrettyPrint();  // 创建一个好看的格式
        format.setEncoding("utf-8");
        XMLWriter writer = new XMLWriter(outputStream, format); // 带格式
        writer.write(doc);
        writer.close();

    }
}
```


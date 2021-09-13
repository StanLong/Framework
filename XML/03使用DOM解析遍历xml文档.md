# 遍历xml文档

文档格式

```xml
<?xml version="1.0" encoding="utf-8"?>
<contactList>
	<contact id="001">
		<name>张三</name>
		<age>20</age>
		<phone>134222223333</phone>
		<email>zhangsan@qq.com</email>
		<qq>432221111</qq>
	</contact>
	<contact id="002">
		<name>李四</name>
		<age>20</age>
		<phone>134222225555</phone>
		<email>lisi@qq.com</email>
		<qq>432222222</qq>
	</contact>
</contactList>
```

遍历代码

```java
package com.stalong;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * 完整读取xml文档的内容
 */
public class XmlTest1 {

    @Test
    public void test() throws Exception{
        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));

        // 读取根标签
        Element rootElement = doc.getRootElement();

        StringBuffer sb = new StringBuffer();

        getChildNodes(rootElement, sb);

        System.out.println(sb.toString());
    }

    /**
     * 获取当前标签的子标签
     */
    private void getChildNodes(Element elem, StringBuffer sb){
        //System.out.println(elem.getName());
        // 开始标签
        sb.append("<" + elem.getName());

        // 得到标签的属性列表
        List<Attribute> attributeList = elem.attributes();
        if(attributeList != null){
            for(Attribute attr : attributeList){
                // System.out.println(attr.getName() + "=" + attr.getValue());
                sb.append(" " + attr.getName() + "=\"" + attr.getValue() + "\"");
            }
        }

        sb.append(">");

        Iterator<Node> nodeIterator = elem.nodeIterator();

        while (nodeIterator.hasNext()){
            Node node = nodeIterator.next();

            // 标签
            if(node instanceof Element){
                Element el = (Element)node;
                getChildNodes(el, sb);
            }

            // 文本
            if(node instanceof Text){
                Text text = (Text)node;
                sb.append(text.getText());
            }
        }

        // 结束标签
        sb.append("</" + elem.getName() + ">");
    }
}
```

# 将xml文档信息封装到对象里

新建对象

```java
package com.stanlong.xml;

public class Contact {
    private String id;
    private String name;
    private String age;
    private String phone;
    private String email;
    private String qq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }
}
```

代码

```java
package com.stanlong.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 将xml文档信息封装到对象里
 */
public class Dom4j {
    public static void main(String[] args) throws Exception{
        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));

        // 读取Contact标签
        Iterator<Element> it = doc.getRootElement().elementIterator("contact");
        List<Contact> contactList = new ArrayList<>();
        while (it.hasNext()){
            Element next = it.next();
            Contact contact = new Contact();
            contact.setId(next.attributeValue("id"));
            contact.setName(next.elementText("name"));
            contact.setAge(next.elementText("age"));
            contact.setPhone(next.elementText("phone"));
            contact.setEmail(next.elementText("email"));
            contact.setQq(next.elementText("qq"));
            contactList.add(contact);

        }

        for(Contact contact : contactList){
            System.out.println(contact.toString());
        }

    }
}
```


# xml_DOM解析API

```java
package com.stalong;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class XmlTest {

    /**
     * 获取Document对象
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));
        System.out.println(doc);
    }

    /**
     * 得到节点信息
     */
    @Test
    public void test2() throws Exception{
        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));

        //nodeIterator： 得到当前节点下的所有子节点对象(不包括孙节点)
        Iterator<Node> iterator = doc.nodeIterator();
        while (iterator.hasNext()){
            Node node = iterator.next();
            String name = node.getName(); // 得到节点名称
            System.out.println(name);

            // 继续取出其下的子节点， 只有标签节点才有子节点
            if(node instanceof Element){
                Element elem = (Element) node;
                Iterator<Node> it2 = elem.nodeIterator();
                while (it2.hasNext()){
                    Node n2 = it2.next();
                    System.out.println(n2.getName());

                }
            }

        }
    }

    /**
     * 打印所有标签信息
     * @throws Exception
     */
    @Test
    public void test3() throws Exception{
        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));

        // 得到根标签
        Element element = doc.getRootElement();
        getChildNode(element);

    }

    /**
     * 获取传入的标签下的所有子节点
     * @param element
     */
    public void getChildNode(Element element){
        System.out.println(element.getName());

        // 得到子节点
        Iterator<Node> node = element.nodeIterator();
        while (node.hasNext()){
            Node node1 = node.next();

            if(node1 instanceof  Element){
                Element el = (Element) node1;
                // 递归
                getChildNode(el);
            }
        }
    }

    /**
     * 根据标签名取标签
     * @throws Exception
     */
    @Test
    public void test4() throws Exception{
        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));

        Element rootElement = doc.getRootElement();
        System.out.println(rootElement.getName());

        // 得到当前标签下指定名称的子标签
        Element contactElement = rootElement.element("contact");
        System.out.println(contactElement.getName());

        // 得到当前标签下指定名称的所有子标签
        Iterator<Element> itElement = rootElement.elementIterator("contact");
        while (itElement.hasNext()){
            System.out.println(itElement.next().getName());
        }

        // 得到当前标签下的所有子标签
        List<Element> listElement = rootElement.elements();
        for (Element elem : listElement){
            System.out.println(elem.getName());
        }


    }

    /**
     * 获取标签属性
     * @throws Exception
     */
    @Test
    public void test5() throws Exception{
        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));

        // 获取属性需要先获得属性的标签对象
        Element contactElement = doc.getRootElement().element("contact");
        String idValue = contactElement.attributeValue("id"); // 获取标签的属性值
        System.out.println(idValue);

        // 得到指定属性名称的属性对象
        Attribute idAttr = contactElement.attribute("id");
        System.out.println(idAttr.getName() + ": " + idAttr.getValue());

        // 得到所有属性对象: List集合
        List<Attribute> attributes = contactElement.attributes();
        for(Attribute attr : attributes){
            System.out.println(attr.getName() + ": " + attr.getValue());
        }

        // 得到所有属性对象： 迭代器
        Iterator<Attribute> attributeIterator = contactElement.attributeIterator();
        while (attributeIterator.hasNext()){
            Attribute attr = attributeIterator.next();
            System.out.println(attr.getName() + ": " + attr.getValue());
        }

    }

    /**
     * 获取标签文本内容
     */
    @Test
    public void test6() throws Exception{
        // 读取xml文档，返回document对象
        SAXReader reader = new SAXReader();
        String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
        Document doc = reader.read(new File(path));

        Element nameElement = doc.getRootElement().element("contact").element("name");
        // 获得文本
        String text = nameElement.getText();
        System.out.println(text);

        // 得到指定子标签内容的文本
        String name = doc.getRootElement().element("contact").elementText("name");
        System.out.println(name);

    }
}
```


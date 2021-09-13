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

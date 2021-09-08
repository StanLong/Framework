package com.stanlong.xml;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.File;

public class Dom4j {
    public static void main(String[] args) {
        try{
            // 创建一个解析器对象
            SAXReader reader = new SAXReader();
            String path = "D:\\StanLong\\git_repository\\Framework\\XML\\xml\\Dom4jProject\\src\\resources\\contact.xml";
            Document doc = reader.read(new File(path));
            System.out.println(doc);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}

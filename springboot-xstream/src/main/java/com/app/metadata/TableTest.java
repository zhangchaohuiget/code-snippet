package com.app.metadata;

import com.thoughtworks.xstream.XStream;

import java.io.File;

public class TableTest {
    public static void main(String[] args) {
        xml2Object();
    }

    public static void xml2Object() {
        XStream xStream = new XStream();
        XStream.setupDefaultSecurity(xStream);// 设置安全权限
        xStream.allowTypes(new Class[]{Table.class, Line.class});// 设置可转换类型
        xStream.processAnnotations(Table.class);
        xStream.processAnnotations(Line.class);
        Table p = (Table) xStream.fromXML(new File("E:/dp.xml"));
        p.getLines().forEach(e -> {
            System.out.println(e.get注释());
        });
    }
}

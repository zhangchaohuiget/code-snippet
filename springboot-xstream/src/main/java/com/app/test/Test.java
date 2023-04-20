package com.app.test;

import com.app.model.Person;
import com.thoughtworks.xstream.XStream;

import java.io.File;

public class Test {
    public static void object2Xml() {
//        Job job = new Job();
//        job.setJobName("it");
//        job.setJobLife(4);
//        Person p = new Person();
//        p.setName("zs");
//        p.setAge(6);
//        p.setSex(true);
//        p.setJob(job);

//        XStream xStream =new XStream();
//        xStream.alias("Person", Person.class);
//        xStream.aliasField("jobInfo", Person.class, "job");
//        xStream.processAnnotations(Person.class);
//        String xml = xStream.toXML(p);
//        System.out.println(xml);
    }

    public static void xml2Object() {
        XStream xStream = new XStream();
        XStream.setupDefaultSecurity(xStream);// 设置安全权限
        xStream.allowTypes(new Class[]{Person.class});// 设置可转换类型
        xStream.processAnnotations(Person.class);
        Person p = (Person) xStream.fromXML(new File("E:/Person.xml"));
        System.out.println(p);
    }

    public static void main(String[] args) {
//        object2Xml();
        xml2Object();
    }

}

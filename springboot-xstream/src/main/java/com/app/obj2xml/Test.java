package com.app.obj2xml;

import com.app.model.Job;
import com.app.model.Person;
import com.thoughtworks.xstream.XStream;

/**
 * test
 *
 * @author ch
 * @date 2024/2/27 17:06
 */
public class Test {

    public static void main(String[] args) {
        Job job = new Job();
        job.setJobName("it");
        job.setJobLife(4);
        Person p = new Person();
        p.setName("zs");
        p.setAge(6);
        p.setSex(true);
        p.setJob(job);
        XStream xStream = new XStream();
//        // 给Person类设置别名为Person
//        xStream.alias("Person", Person.class);
//        // 给Person类的字段job设置别名为jobInfo
//        xStream.aliasField("jobInfo", Person.class, "job");
        xStream.processAnnotations(Person.class);
        String xml = xStream.toXML(p);
        System.out.println(xml);
    }
}

package com.app.xml2obj.xstreamimplicit;

import com.thoughtworks.xstream.XStream;

/**
 * @author ch
 * @date 2024/2/27 17:31
 */
public class Test {
    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Person>\n" +
                "  <name>zs</name>\n" +
                "  <age>6</age>\n" +
                "  <sex>true</sex>\n" +
                "  <jobInfo>\n" +
                "    <jobName>it</jobName>\n" +
                "    <jobLife>4</jobLife>\n" +
                "  </jobInfo>\n" +
                "  <jobInfo>\n" +
                "    <jobName>it</jobName>\n" +
                "    <jobLife>3</jobLife>\n" +
                "  </jobInfo>\n" +
                "  <jobInfo>\n" +
                "    <jobName>it</jobName>\n" +
                "    <jobLife>2</jobLife>\n" +
                "  </jobInfo>\n" +
                "</Person>";
        // 读取xml代码省略...
        XStream xStream = new XStream();
        // 设置转换类型
        xStream.allowTypes(new Class[]{Person.class});
        // 读取注解配置
        xStream.processAnnotations(Person.class);
        Person p = (Person) xStream.fromXML(xml);
        System.out.println(p);
    }
}

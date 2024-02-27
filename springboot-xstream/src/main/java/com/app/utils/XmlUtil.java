package com.app.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * xml util
 *
 * @author ch
 * @date 2024/2/27 17:46
 */
public class XmlUtil {

    public static <T> T xml2Bean(Class<T> clazz, String xml) {
        T obj = null;
        if (xml != null && !"".equals(xml)) {
            // 解决java_下划线转义异常问题
            XStream xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xstream.allowTypes(new Class[]{clazz});
            // 设置可转换类型
            // xstream.allowTypes(new Class[]{...});
            xstream.processAnnotations(clazz);
            xstream.autodetectAnnotations(true);
            obj = (T) xstream.fromXML(xml);
        }
        return obj;
    }
}

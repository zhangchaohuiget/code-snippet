package com.app.xml2obj.xstreamasattribute;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.ToString;

/**
 * obj2xml Person
 *
 * @author ch
 * @date 2024/2/27 17:03
 */
@Data
@ToString
@XStreamAlias("Person")
public class Person {
    private String name;
    private int age;
    private boolean sex;
    @XStreamAlias("jobInfo")
    private Job job;

}

package com.app.xml2obj.xstreamimplicit;

import com.app.model.Job;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.ToString;

import java.util.List;

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
    @XStreamImplicit(itemFieldName = "jobInfo")
    private List<Job> jobs;

}

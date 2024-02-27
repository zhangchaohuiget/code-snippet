package com.app.xml2obj.xstreamasattribute;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

/**
 * obj2xml Job
 *
 * @author ch
 * @date 2024/2/27 17:05
 */
@Data
public class Job {
    @XStreamAsAttribute
    private String status;
    private String jobName;
    private int jobLife;
}

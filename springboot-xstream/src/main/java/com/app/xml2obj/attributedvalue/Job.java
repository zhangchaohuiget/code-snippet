package com.app.xml2obj.attributedvalue;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import lombok.Data;

@Data
@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"value"})
public class Job {
    @XStreamAsAttribute
    private String status;
    private String value;
}
package com.app.config.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface Field {
    FieldType type() default FieldType.Auto;

    boolean index() default true;

    String pattern() default "";

    boolean store() default false;

    boolean fielddata() default false;

    String searchAnalyzer() default "";

    String analyzer() default "";

    String[] ignoreFields() default {};

    boolean includeInParent() default false;
}

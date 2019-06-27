package com.cims.common.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExcelTitle {

    String value(); // 标题

    boolean enable() default true; // 是否启用，默认启用

    int order() default Integer.MAX_VALUE; // 顺序，默认为Integer.MAX_VALUE，排在最后

}

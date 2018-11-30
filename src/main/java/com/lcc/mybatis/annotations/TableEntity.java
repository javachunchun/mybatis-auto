package com.lcc.mybatis.annotations;

import java.lang.annotation.*;

/**
 * Created by liuchunchun on 2018/11/28.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableEntity {
    String table();
    String pk();
}

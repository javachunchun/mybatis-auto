package com.lcc.mybatis.service.base;

import java.util.List;

/**
 * Created by liuchunchun on 2018/11/28.
 */
public interface BaseService<T> {

    public List<T> find(T t);

    public int save(T t);

    public int update(T t);

    public T get(String pkId);

    public int delete(String pkId);

    public boolean isExists(T t);
}

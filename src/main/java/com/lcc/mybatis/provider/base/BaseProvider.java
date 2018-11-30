package com.lcc.mybatis.provider.base;

import com.lcc.mybatis.annotations.TableEntity;
import com.lcc.mybatis.utils.StringUtil;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by liuchunchun on 2018/11/28.
 */
public abstract class BaseProvider<T> {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaseProvider.class);
    private String tableName;
    private String primaryKey;

    public BaseProvider(Class<T> clazz) {
        TableEntity annotation = clazz.getAnnotation(TableEntity.class);
        this.tableName = annotation.table();
        this.primaryKey = annotation.pk();
        if (tableName == null || primaryKey == null) {
            LOGGER.error("获取表名、主键异常！");
        }
    }

    public String find(T t) {
        Map<String, Object> columns = getNotNullColumns(t);

        SQL select = new SQL().FROM(tableName).SELECT("*");

        Set<String> columnSet = columns.keySet();
        for (String column : columnSet) {
            select.WHERE(StringUtil.camelToUnderline(column) + " like concat(concat('%',#{"+column+"}),'%')");
        }

        LOGGER.info("SQL:"+select.toString());

        return select.toString();
    }

    public String save(T t){
        SQL insert = new SQL().INSERT_INTO(tableName);
        Map<String, Object> columns = getNotNullColumns(t);
        Set<String> columnSet = columns.keySet();
        for (String column : columnSet) {
            insert.INTO_COLUMNS(StringUtil.camelToUnderline(column));
            insert.INTO_VALUES("#{" + column + "}");
        }

        LOGGER.info("SQL:"+insert.toString());
        return insert.toString();
    }


    public String update(T t){
        SQL update = new SQL().UPDATE(tableName);
        Map<String, Object> columns = getNotNullColumns(t);
        Set<String> columnSet = columns.keySet();
        for (String column : columnSet) {
            if (StringUtil.underlineToCamel(primaryKey).equals(column)) {
                update.WHERE(StringUtil.camelToUnderline(column) + "=#{" + column + "}");
            } else {
                update.SET(StringUtil.camelToUnderline(column) + "=#{" + column + "}");
            }
        }

        LOGGER.info("SQL:"+update.toString());
        return update.toString();
    }

    public String get(String pkId){
        SQL select = new SQL().FROM(tableName).SELECT("*").WHERE(primaryKey + "='" + pkId+"'");
        LOGGER.info("SQL:"+select.toString());
        return select.toString();
    }

    public String delete(String pkId){
        SQL delete = new SQL().DELETE_FROM(tableName);
        delete.WHERE(StringUtil.camelToUnderline(primaryKey) + "='" + pkId + "'");

        LOGGER.info("SQL:"+delete.toString());
        return delete.toString();
    }

    public String isExists(T t){
        SQL isExists = new SQL().FROM(tableName).SELECT("COUNT(*)");
        Map<String, Object> columns = getNotNullColumns(t);
        Set<String> columnSet = columns.keySet();
        if (columnSet != null && columnSet.size() > 1) {
            LOGGER.info("SQL:"+isExists.toString());
            LOGGER.error("非法查询!");
        }
        for (String column : columnSet) {
            isExists.SET(StringUtil.camelToUnderline(column) + "=#{" + column + "}");
        }

        LOGGER.info("SQL:"+isExists.toString());
        return isExists.toString();
    }

    protected Map<String,Object> getNotNullColumns(T t) {
        Map<String,Object> columns = new HashMap<>();
        try {

            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method method = pd.getReadMethod();//获得读方法
                Object invoke = method.invoke(t);
                if (invoke != null) {
                    columns.put(field.getName(), invoke);
                }
            }
        } catch (Exception e) {
            //获取表列名失败
            LOGGER.error("获取非空列名失败!");
            throw new RuntimeException(e);
        }

        return columns;
    }

}

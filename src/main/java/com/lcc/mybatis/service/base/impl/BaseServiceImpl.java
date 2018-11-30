package com.lcc.mybatis.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.lcc.mybatis.annotations.TableEntity;
import com.lcc.mybatis.service.base.BaseService;
import com.lcc.mybatis.utils.StringUtil;
import com.lcc.mybatis.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by liuchunchun on 2018/11/28.
 */
public class BaseServiceImpl<RECORD,MAPPER> implements BaseService<RECORD> {
    private final static Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private MAPPER mapper;

    @Override
    public List<RECORD> find(RECORD t) {
        try {
            Class<?> clazz = t.getClass();
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null && "com.hoze.pf.bean.model.fms.base.PageRequest".equals(superclass.getName())) {
                Field pageNumField = superclass.getDeclaredField("pageNum");
                Field pageSizeField = superclass.getDeclaredField("pageSize");
                Field orderByField = superclass.getDeclaredField("orderBy");

                PropertyDescriptor numPd = new PropertyDescriptor(pageNumField.getName(), superclass);
                Method numM = numPd.getReadMethod();//获得读方法
                PropertyDescriptor sizePd = new PropertyDescriptor(pageSizeField.getName(), superclass);
                Method sizeM = sizePd.getReadMethod();//获得读方法
                PropertyDescriptor orderPd = new PropertyDescriptor(orderByField.getName(), superclass);
                Method orderM = orderPd.getReadMethod();//获得读方法

                int pageNum = (int) numM.invoke(t);
                int pageSize = (int) sizeM.invoke(t);
                String orderSize = (String) orderM.invoke(t);

                PageHelper.startPage(pageNum, pageSize);
                if (orderSize != null) {
                    PageHelper.orderBy(orderSize);
                }
            }

            Class<?> mapperClass = mapper.getClass();
            Method find = mapperClass.getDeclaredMethod("find",clazz);
            List<RECORD> list = (List<RECORD>) find.invoke(mapper, t);
            return list;
        } catch (Exception e) {
            LOGGER.error("Method find in "+mapper.getClass().getSimpleName()+" invoke failed!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public int save(RECORD t) {
        try {
            Class<?> clazz = t.getClass();
            TableEntity annotation = clazz.getAnnotation(TableEntity.class);
            String pk = annotation.pk();
            Field field = clazz.getDeclaredField(StringUtil.underlineToCamel(pk));

            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method writeMethod = pd.getWriteMethod();
            writeMethod.invoke(t, UUIDUtil.getUUID32());

            Class<?> mapperClass = mapper.getClass();
            Method find = mapperClass.getDeclaredMethod("save",clazz);
            return (int) find.invoke(mapper, t);
        } catch (Exception e) {
            LOGGER.error("Method save in "+mapper.getClass().getSimpleName()+" invoke failed!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(RECORD t) {
        try {
            Class<?> mapperClass = mapper.getClass();
            Method find = mapperClass.getDeclaredMethod("update",t.getClass());
            return (int) find.invoke(mapper, t);
        } catch (Exception e) {
            LOGGER.error("Method update in "+mapper.getClass().getSimpleName()+" invoke failed!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public RECORD get(String pkId) {
        try {
            Class<?> mapperClass = mapper.getClass();
            Method get = mapperClass.getDeclaredMethod("get",pkId.getClass());
            return (RECORD) get.invoke(mapper, pkId);
        } catch (Exception e) {
            LOGGER.error("Method get "+mapper.getClass().getSimpleName()+" invoke failed!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(String pkId) {
        try {
            Class<?> mapperClass = mapper.getClass();
            Method delete = mapperClass.getDeclaredMethod("delete",pkId.getClass());
            return (int) delete.invoke(mapper, pkId);
        } catch (Exception e) {
            LOGGER.error("Method delete in "+mapper.getClass().getSimpleName()+" invoke failed!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isExists(RECORD record) {
        try {
            Class<?> mapperClass = mapper.getClass();
            Method isExists = mapperClass.getDeclaredMethod("isExists",record.getClass());
            Long count = (Long) isExists.invoke(mapper, record);
            return (count == null || count == 0) ? false : true;
        } catch (Exception e) {
            LOGGER.error("Method delete in "+mapper.getClass().getSimpleName()+" invoke failed!");
            throw new RuntimeException(e);
        }
    }
}

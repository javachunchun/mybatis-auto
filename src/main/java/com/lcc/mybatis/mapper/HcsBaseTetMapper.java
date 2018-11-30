package com.lcc.mybatis.mapper;

import com.lcc.mybatis.entity.bus.HcsBaseTet;
import com.lcc.mybatis.provider.bus.HcsBaseTetProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * Created by liuchunchun on 2018/11/28.
 */
public interface HcsBaseTetMapper {

    @SelectProvider(type = HcsBaseTetProvider.class,method = "find")
    List<HcsBaseTet> find(HcsBaseTet hcsBaseTet);

    @InsertProvider(type = HcsBaseTetProvider.class,method = "save")
    int save(HcsBaseTet hcsBaseTet);

    @UpdateProvider(type = HcsBaseTetProvider.class,method = "update")
    int update(HcsBaseTet hcsBaseTet);

    @SelectProvider(type = HcsBaseTetProvider.class,method = "get")
    HcsBaseTet get(String pkId);

    @DeleteProvider(type = HcsBaseTetProvider.class,method = "delete")
    int delete(String pkId);

    @SelectProvider(type = HcsBaseTetProvider.class,method = "isExists")
    Long isExists(HcsBaseTet hcsBaseTet);

}

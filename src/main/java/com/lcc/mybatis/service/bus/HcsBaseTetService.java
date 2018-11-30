package com.lcc.mybatis.service.bus;


import com.lcc.mybatis.entity.bus.HcsBaseTet;
import com.lcc.mybatis.service.base.BaseService;

/**
 * Created by liuchunchun on 2018/11/28.
 */
public interface HcsBaseTetService extends BaseService<HcsBaseTet> {

    int add(HcsBaseTet hcsBaseTet) throws Exception;
}

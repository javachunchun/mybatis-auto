package com.lcc.mybatis.service.bus.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcc.mybatis.entity.bus.HcsBaseTet;
import com.lcc.mybatis.mapper.HcsBaseTetMapper;
import com.lcc.mybatis.service.base.impl.BaseServiceImpl;
import com.lcc.mybatis.service.bus.HcsBaseTetService;
import com.lcc.mybatis.utils.StringUtil;

/**
 * Created by liuchunchun on 2018/11/28.
 */
@Service
public class HcsBaseTetServiceImpl extends BaseServiceImpl<HcsBaseTet,HcsBaseTetMapper> implements HcsBaseTetService {

    @Override
    public int add(HcsBaseTet hcsBaseTet) throws Exception {
        //校验空
        if (StringUtil.isNull(hcsBaseTet.getCd(), hcsBaseTet.getNa())) {
            throw new Exception("编码、名称不允许为空");
        }
        //校验是否存在
        HcsBaseTet hcs = new HcsBaseTet();
        hcs.setCd(hcsBaseTet.getCd());
        boolean cdExists = super.isExists(hcs);
        if (cdExists) {
            throw new Exception("编码已存在");
        }

        hcs = new HcsBaseTet();
        hcs.setNa(hcsBaseTet.getNa());
        boolean naExists = super.isExists(hcs);
        if (naExists) {
            throw new Exception("名称已存在");
        }

        hcsBaseTet.setFgActive(true);
        return super.save(hcsBaseTet);
    }
}

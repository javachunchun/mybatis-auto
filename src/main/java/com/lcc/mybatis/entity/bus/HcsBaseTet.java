package com.lcc.mybatis.entity.bus;

import com.lcc.mybatis.annotations.TableEntity;
import com.lcc.mybatis.entity.base.PageRequest;

import java.io.Serializable;

/**
 * Created by liuchunchun on 2018/11/28.
 */
@TableEntity(table = "hcs_base_tet",pk = "id_tet")
public class HcsBaseTet extends PageRequest implements Serializable {
    private String idTet;
    private String na;
    private String cd;
    private String instr;
    private String des;
    private Boolean fgActive;

    public String getIdTet() {
        return idTet;
    }

    public void setIdTet(String idTet) {
        this.idTet = idTet;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getInstr() {
        return instr;
    }

    public void setInstr(String instr) {
        this.instr = instr;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Boolean getFgActive() {
        return fgActive;
    }

    public void setFgActive(Boolean fgActive) {
        this.fgActive = fgActive;
    }
}

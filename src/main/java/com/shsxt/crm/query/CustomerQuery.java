package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

public class CustomerQuery extends BaseQuery {
    // 客户编号
    private String khno;

    // 客户名称
    private String name;


    // 信用度
    private String xyd;

    // 客户流失状态
    private Integer state;

    public String getKhno() {
        return khno;
    }

    public void setKhno(String khno) {
        this.khno = khno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXyd() {
        return xyd;
    }

    public void setXyd(String xyd) {
        this.xyd = xyd;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}

package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;


public class SaleChanceQuery extends BaseQuery {
    private String customerName;
    private Integer state;
    private String createMan;


    // 分配人
    private Integer assignMan;

    public Integer getAssignMan() {
        return assignMan;
    }

    public void setAssignMan(Integer assignMan) {
        this.assignMan = assignMan;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }
}

package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

public class CustomerOrderQuery extends BaseQuery {
    // 客户id
    private Integer cusId;

    // 订单编号
    private String orderNo;

    //订单支付状态
    private Integer state;

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}

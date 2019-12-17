package com.shsxt.crm.db.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.CustomerOrder;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {

    /**
     * 查询最后一次客户订单记录
     * @param
     * @return
     */
    public CustomerOrder  queryLastCustomerOrderByCusId(Integer cusId);

}
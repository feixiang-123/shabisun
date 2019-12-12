package com.shsxt.crm.db.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.Customer;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    Customer  queryCustomerByName(String name);
}
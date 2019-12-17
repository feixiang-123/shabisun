package com.shsxt.crm.db.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.Customer;

import java.util.List;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    Customer  queryCustomerByName(String name);

    List<Customer>  querylossCustomers();

    Customer  queryCustomerByCusNo(String cusNo);

    int  updateStateBatch(Integer[] ids);
}
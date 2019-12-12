package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.query.CustomerQuery;
import com.shsxt.crm.service.CustomerService;
import com.shsxt.crm.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {

    @Resource
    private CustomerService customerService;

    @RequestMapping("index")
    public String index(){
        return "customer";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomersByParams(CustomerQuery customerQuery,@RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer rows){
        customerQuery.setPageNum(page);
        customerQuery.setPageSize(rows);
        return customerService.queryByParamsForDataGrid(customerQuery);
    }


    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(Customer customer){
        customerService.saveCustomer(customer);
        return success("客户数据添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(Customer customer){
        customerService.updateCustomer(customer);
        return success("客户数据更新成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        customerService.deleteCustomer(id);
        return success("客户数据删除成功");
    }


}

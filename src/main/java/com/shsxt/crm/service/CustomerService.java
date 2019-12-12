package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.db.dao.CustomerMapper;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.Customer;
import javafx.scene.shape.CubicCurveTo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.SimpleBeanInfo;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CustomerService extends BaseService<Customer, Integer> {


    @Autowired
    private CustomerMapper customerMapper;

    public void saveCustomer(Customer customer) {
        /**
         * 1.参数校验
         *      客户姓名非空 不可重复
         *      手机号非空
         *      法人 非空
         * 2.字段值设置
         *      khno 客户编号  值唯一  后台生成
         *      state   0-未流失(默认)   1-已流失
         *      isValid  1-有效
         *      createDate  updateDate
         * 3.执行添加 判断结果
         */
        checkParams(customer);
        Customer temp = customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null != temp, "客户已存在!");
        // 客户编号唯一值设置  时间毫秒  格式化当前时间yyyyMMddHHmmss  uuid  指定算法生成指定长度的字符串
        String khno = "KH" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        customer.setKhno(khno);
        customer.setState(0);
        customer.setIsValid(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(save(customer) < 1, "客户信息添加失败!");
    }

    private void checkParams(Customer customer) {
        AssertUtil.isTrue(StringUtils.isBlank(customer.getName()), "请输入客户名!");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getPhone()), "请输入手机号!");
        AssertUtil.isTrue(customer.getPhone().length() != 11, "手机号格式非法!");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getFr()), "请指定公司法人代表!");
    }


    public void updateCustomer(Customer customer) {
        /**
         * 1.参数校验
         *      客户姓名非空 不可重复
         *      手机号非空
         *      法人 非空
         *      记录id 必须存在
         * 2.字段值设置
         *       updateDate
         * 3.执行更新 判断结果
         */
        checkParams(customer);
        Customer temp = customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(customer.getId())),"客户已存在!");
        AssertUtil.isTrue(null==customer.getId()||null==queryById(customer.getId()),"待更新记录不存在!");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(update(customer)<1,"客户更新失败!");
    }


    public void deleteCustomer(Integer id) {
        Customer customer=queryById(id);
        AssertUtil.isTrue(null==id||null==customer,"待删除记录不存在!");
        customer.setIsValid(0);
        AssertUtil.isTrue(update(customer)<1,"客户删除失败!");
    }


}

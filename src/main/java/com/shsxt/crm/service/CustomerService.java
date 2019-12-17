package com.shsxt.crm.service;
import com.shsxt.base.BaseService;
import com.shsxt.crm.db.dao.CustomerLossMapper;
import com.shsxt.crm.db.dao.CustomerMapper;
import com.shsxt.crm.db.dao.CustomerOrderMapper;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.Customer;
import com.shsxt.crm.vo.CustomerLoss;
import com.shsxt.crm.vo.CustomerOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService extends BaseService<Customer, Integer> {


    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerOrderMapper customerOrderMapper;

    @Autowired
    private CustomerLossMapper customerLossMapper;

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
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(customer.getId())), "客户已存在!");
        AssertUtil.isTrue(null == customer.getId() || null == queryById(customer.getId()), "待更新记录不存在!");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(update(customer) < 1, "客户更新失败!");
    }


    public void deleteCustomer(Integer id) {
        Customer customer = queryById(id);
        AssertUtil.isTrue(null == id || null == customer, "待删除记录不存在!");
        customer.setIsValid(0);
        AssertUtil.isTrue(update(customer) < 1, "客户删除失败!");
    }


    /**
     * 流失客户转移
     * 将符合流失客户规则的客户数据转移到客户流失表
     * 客户流失规则分析
     * 1.录入的客户数据 距离当前超过半年
     * 2.没有产生订单  或者最后一次下单时间距离当前超过半年
     */
    public void updateCustomerState() {
        /**
         * 1.查询符合规则的流失客户
         *
         * 2.执行转移操作
         *    如果存在流失客户  执行数据转移
         *        t_customer -->t_customer_loss
         *           t_customer  更新state 0-->1   批量更新客户流失状态 根据客户id
         *            t_customer_loss 数据从 t_customer 获取  批量添加客户流失数据到t_customer_loss
         * 3.定时任务配置
         */

        //查询到的流失客户
        List<Customer> lossCustomers = customerMapper.querylossCustomers();
        Integer[] ids = null;
        List<CustomerLoss> customerLosses = null;
        if (!(CollectionUtils.isEmpty(lossCustomers))) {
            ids = new Integer[lossCustomers.size()];
            customerLosses = new ArrayList<CustomerLoss>();
            for (int i = 0; i < lossCustomers.size(); i++) {
                Customer customer = lossCustomers.get(i);
                ids[i] = customer.getId();
                CustomerLoss customerLoss = new CustomerLoss();
                customerLoss.setUpdateDate(new Date());
                // state:最终的流失状态  0-暂缓流失  1-确认流失
                customerLoss.setState(0);
                // 设置客户最后一次下单时间
                CustomerOrder customerOrder = customerOrderMapper.queryLastCustomerOrderByCusId(customer.getId());
                if (null != customerOrder) {
                    customerLoss.setLastOrderTime(customerOrder.getOrderDate());
                }
                customerLoss.setIsValid(1);
                customerLoss.setCusNo(customer.getKhno());
                customerLoss.setCusName(customer.getName());
                customerLoss.setCusManager(customer.getCusManager());
                customerLoss.setCreateDate(new Date());
                customerLosses.add(customerLoss);
            }

            AssertUtil.isTrue(customerLossMapper.saveBatch(customerLosses) != customerLosses.size(), "客户数据流转失败!");
            AssertUtil.isTrue(customerMapper.updateStateBatch(ids) != ids.length, "客户数据流转失败!");
        }

    }
}
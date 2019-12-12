package com.shsxt.crm.service;

import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
       PageInfo<SaleChance> pageInfo= queryForPage(saleChanceQuery);
       Map<String,Object> result= new HashMap<String,Object>();
       result.put("total",pageInfo.getTotal());
       result.put("rows",pageInfo.getList());
       return result;
    }



    public void saveSaleChance(SaleChance saleChance){
        /**
         * 1.参数合法校验
         *       客户名  联系人  联系方式 非空校验
         * 2.分配时间  分配状态  分配人
         *     分配人默认  null  分配时间 默认 null  分配状态 默认值 0-未分配
         *     如果指定分配人 设置分配时间 分配状态 1-已分配
         * 3.开发结果  0-未开发 1-开发中 2-开发成功 3-开发失败
         *      添加时 默认 未开发
         *      如果指定了分配人  此时为开发中
         * 4. is_valid=1 createDate  updateDate
         */
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setState(0);
        saleChance.setDevResult(0);
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setAssignTime(new Date());
            saleChance.setState(1);
            saleChance.setDevResult(1);
        }
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(save(saleChance)<0,"营销机会数据添加失败!");
    }

    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"请输入客户名称!");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"请输入联系人!");
        //  判断手机格式合法性 正则判断  13X  14X  15X 16X 17X 18X 19X
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"请输入手机号!");
    }



    public void updateSaleChance(SaleChance saleChance){
        /**
         * 1. 参数合法校验
         *     客户名  联系人  联系方式 非空校验
         *     更新记录存在性校验
         * 2.分配人
         *    如果添加时 设置分配人   更新时 没有改动分配人  不做处理
         *    如果添加时没有设置分配人 更新时设置分配人
         *          设置分配时间分配状态
         *     如果添加时 设置分配人   更新时清空分配人
         *            分配时间  分配状态
         *3. 开发状态
         *     默认未开发
         *     如果设置分配人  开发中
         * 4.updateDate
         */
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        Integer sid = saleChance.getId();
        SaleChance tempSaleChance =queryById(sid);
        AssertUtil.isTrue(sid==null || null==tempSaleChance,"待更新记录不存在!");
        if(StringUtils.isBlank(tempSaleChance.getAssignMan())&& StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(1);
            saleChance.setAssignTime(new Date());
        }
        if(StringUtils.isNotBlank(tempSaleChance.getAssignMan())&& StringUtils.isBlank(saleChance.getAssignMan())){
            saleChance.setState(0);
            saleChance.setAssignTime(null);
        }

        saleChance.setDevResult(0);
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setDevResult(1);
        }
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(update(saleChance)<1,"营销机会数据更新失败!");
    }


    public void deleteSaleChanceBatch(Integer[] ids){
        AssertUtil.isTrue(null==ids || ids.length==0,"请选择待删除的记录!");
        AssertUtil.isTrue(deleteBatch(ids)!=ids.length,"记录删除失败!");
    }

    public void updateSaleChanceDevResult(Integer devResult, Integer sid) {
        AssertUtil.isTrue(null==devResult,"机会数据开发状态异常!");
        SaleChance saleChance= queryById(sid);
        AssertUtil.isTrue(null==saleChance,"待更新的机会数据不存在!");
        saleChance.setDevResult(devResult);
        AssertUtil.isTrue(update(saleChance)<1,"机会数据开发状态更新失败!");
    }
}

package com.shsxt.crm.service;

import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.crm.query.CusDevPlanQuery;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.CusDevPlan;
import com.shsxt.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {


   /* public Map<String,Object>  queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery){
        PageInfo<CusDevPlan> pageInfo= queryForPage(cusDevPlanQuery);
        Map<String,Object> result= new HashMap<String,Object>();
        result.put("total",pageInfo.getTotal());
        result.put("rows",pageInfo.getList());
        return result;
    }*/

   public void saveCusDevPlan(CusDevPlan cusDevPlan){
       checkParams(cusDevPlan.getPlanDate(),cusDevPlan.getPlanItem(),cusDevPlan.getExeAffect());
       cusDevPlan.setIsValid(1);
       cusDevPlan.setCreateDate(new Date());
       cusDevPlan.setUpdateDate(new Date());
       AssertUtil.isTrue(save(cusDevPlan)<1,"计划项数据添加失败!");
   }

    private void checkParams(Date planDate, String planItem, String exeAffect) {
        AssertUtil.isTrue(null==planDate,"请指定计划项时间!");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"请输入计划项内容!");
        AssertUtil.isTrue(StringUtils.isBlank(exeAffect),"请输入执行效果!");
    }


    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        checkParams(cusDevPlan.getPlanDate(),cusDevPlan.getPlanItem(),cusDevPlan.getExeAffect());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(null==cusDevPlan.getId()|| null==queryById(cusDevPlan.getId()),"待更新记录不存在!");
        AssertUtil.isTrue(update(cusDevPlan)<1,"计划项数据更新失败!");
    }

    public void deleteCusDevPlan(Integer[] ids){
       AssertUtil.isTrue(null==ids || ids.length==0,"请选择待删除的计划项数据!");
       AssertUtil.isTrue(deleteBatch(ids)<ids.length,"计划项数据删除失败!");
    }



}

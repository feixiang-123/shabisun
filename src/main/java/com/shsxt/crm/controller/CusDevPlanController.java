package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.query.CusDevPlanQuery;
import com.shsxt.crm.service.CusDevPlanService;
import com.shsxt.crm.service.SaleChanceService;
import com.shsxt.crm.vo.CusDevPlan;
import com.shsxt.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {




    @Resource
    private SaleChanceService saleChanceService;


    @Resource
    private CusDevPlanService cusDevPlanService;

    @RequestMapping("index")
    public String index(){
        return "cus_dev_plan";
    }


    @RequestMapping("toCusDevPlanManager")
    public String toCusDevPlanManager(Integer sid, Model model){
        SaleChance saleChance= saleChanceService.queryById(sid);
        model.addAttribute("saleChance",saleChance);
        return "cus_dev_plan_manager";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCusDevPlansByParams(CusDevPlanQuery cusDevPlanQuery,
                                                       @RequestParam(defaultValue = "1")Integer page,
                                                       @RequestParam(defaultValue = "10") Integer rows){
        cusDevPlanQuery.setPageNum(page);
        cusDevPlanQuery.setPageSize(rows);
        return cusDevPlanService.queryByParamsForDataGrid(cusDevPlanQuery);
    }


    @RequestMapping("save")
    @ResponseBody
    public  ResultInfo save(CusDevPlan cusDevPlan){
        cusDevPlanService.saveCusDevPlan(cusDevPlan);
        return success("计划项数据添加成功!");
    }

    @RequestMapping("update")
    @ResponseBody
    public  ResultInfo update(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("计划项数据更新成功!");
    }

    // ids=117&ids=115
    @RequestMapping("delete")
    @ResponseBody
    public  ResultInfo delete(Integer[] ids){
        cusDevPlanService.deleteCusDevPlan(ids);
        return success("计划项数据删除成功!");
    }
}

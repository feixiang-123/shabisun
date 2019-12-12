package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.annotations.RequirePermission;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.service.SaleChanceService;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private UserService userService;

    @RequestMapping("index")
    public String index(){
        return "sale_chance";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object>  querySaleChancesByParams(SaleChanceQuery saleChanceQuery,
                                                        @RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer rows,@RequestParam(defaultValue = "0") Integer flag,HttpServletRequest request){
        saleChanceQuery.setPageNum(page);
        saleChanceQuery.setPageSize(rows);
        if(flag==1){
            // 设置分配人
            saleChanceQuery.setAssignMan(LoginUserUtil.releaseUserIdFromCookie(request));
        }
        return saleChanceService.querySaleChancesByParams(saleChanceQuery);
    }

    // ip:port/crm/saleChance/save
    // 营销机会管理添加  101002
    @RequestMapping("save")
    @ResponseBody
    @RequirePermission(aclValue = "101002")
    public ResultInfo saveSaleChance(HttpServletRequest request,SaleChance saleChance){
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        saleChance.setCreateMan(userService.queryById(userId).getTrueName());
        saleChanceService.saveSaleChance(saleChance);
        return success("营销机会数据添加成功");
    }



    @RequestMapping("update")
    @ResponseBody
    @RequirePermission(aclValue = "101004")
    public ResultInfo updateSaleChance(SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会数据更新成功");
    }


    // url  ids=10&ids=20
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteSaleChanceBatch(ids);
        return success("营销机会数据删除成功");
    }


    @RequestMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer devResult,Integer sid){
        saleChanceService.updateSaleChanceDevResult(devResult,sid);
        return success("营销机会开发状态更新成功");
    }
}

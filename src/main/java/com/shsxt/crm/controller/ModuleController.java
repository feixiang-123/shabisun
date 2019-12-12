package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.TreeDto;
import com.shsxt.crm.query.ModuleQuery;
import com.shsxt.crm.service.ModuleService;
import com.shsxt.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *   1语法
 *
 *   2. 方法
 *   3、形参
 *
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {


    @Resource
    private ModuleService moduleService;

    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<Map<String,Object>> queryAllModules(){
        return moduleService.queryAllModules();
    }

    @RequestMapping("queryAllModules02")
    @ResponseBody
    public List<TreeDto> queryAllModules02(Integer rid){
        return moduleService.queryAllModules02(rid);
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryModulesByParams(ModuleQuery moduleQuery, @RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer rows){
        moduleQuery.setPageSize(rows);
        moduleQuery.setPageNum(page);
        return moduleService.queryByParamsForDataGrid(moduleQuery);
    }


    @RequestMapping("index/{grade}")
    public String index(@PathVariable Integer grade, Integer parentId, Model model){
        model.addAttribute("parentId",parentId);
        if(null==grade|| grade==0){
            return "module_1";
        }else  if(grade==1){
            return "module_2";
        }else  if(grade==2){
            return "module_3";
        }else{
            return "error";
        }
    }


    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveModule(Module module){
        moduleService.saveModule(module);
        return success("模块添加成功!");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateModule(Module module){
        moduleService.updateModule(module);
        return success("模块更新成功!");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteModule(Integer id){
        moduleService.deleteModuleByModuleId(id);
        return success("模块删除成功!");
    }
}

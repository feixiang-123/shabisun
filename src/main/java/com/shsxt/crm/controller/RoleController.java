package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.query.RoleQuery;
import com.shsxt.crm.service.RoleService;
import com.shsxt.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;


    @RequestMapping("index")
    private String index(){
        return "role";
    }


    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(){
        return roleService.queryAllRoles();
    }




    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryRolesByParams(RoleQuery roleQuery, @RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer rows){
        roleQuery.setPageSize(rows);
        roleQuery.setPageNum(page);
        return roleService.queryByParamsForDataGrid(roleQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(Role role){
        roleService.saveRole(role);
        return success("角色数据添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(Role role){
        roleService.updateRole(role);
        return success("角色数据更新成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        roleService.deleteRole(id);
        return success("角色数据删除成功");
    }


    /**
     * 角色授权接口
     * @param mid   资源id
     * @param rid   角色id
     * @return
     */
    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer[] mid,Integer rid){
        roleService.addGrant(mid,rid);
        return success("角色授权成功");
    }

}

package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.service.PermissionService;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;


    @Resource
    private PermissionService permissionService;
    @RequestMapping("index")
    public  String index(){
        return "index";
    }

    @RequestMapping("index02")
    public  String index02(){
        return "index";
    }

    @RequestMapping("main")
    public String main(HttpServletRequest request){
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        request.setAttribute("user",userService.queryById(userId));

        List<String> permissions =permissionService.queryAllModuleAclValueByUserId(userId);
        request.getSession().setAttribute("permissions",permissions);
        return "main";
    }
}

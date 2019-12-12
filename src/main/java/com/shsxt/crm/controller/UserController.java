package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@SuppressWarnings("all")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping("test")
    @ResponseBody
    public User queryUserByUserId(Integer userId) {
        return userService.queryById(userId);
    }


    @RequestMapping("user/login")
    @ResponseBody
    public ResultInfo login(String userName, String password, HttpSession session) {
        ResultInfo resultInfo = new ResultInfo();
        UserModel userModel = userService.login(userName, password);
        resultInfo.setResult(userModel);





        return resultInfo;
    }

    @RequestMapping("user/updateUserPassword")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword) {
        ResultInfo resultInfo = new ResultInfo();
        userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request), oldPassword, newPassword, confirmPassword);
        resultInfo.setMsg("密码更新成功!");
        return resultInfo;
    }



    @RequestMapping("user/index")
    public String index(){
        return "user";
    }



    @RequestMapping("user/list")
    @ResponseBody
    public Map<String,Object> queryUsersByParams(UserQuery userQuery, @RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer rows){
        userQuery.setPageSize(rows);
        userQuery.setPageNum(page);
        return userService.queryByParamsForDataGrid(userQuery);
    }


    @RequestMapping("user/save")
    @ResponseBody
    public ResultInfo save(User user){
        userService.saveUser(user);
        return success("用户数据添加成功");
    }
    @RequestMapping("user/update")
    @ResponseBody
    public ResultInfo update(User user){
        userService.updateUser(user);
        return success("用户数据更新成功");
    }
    @RequestMapping("user/delete")
    @ResponseBody
    public ResultInfo delete(@RequestParam(name = "id") Integer userId){
        userService.deleteUser(userId);
        return success("用户数据删除成功");
    }
}

package com.shsxt.crm.service;

import com.mysql.fabric.xmlrpc.base.Param;
import com.shsxt.base.BaseService;
import com.shsxt.crm.db.dao.UserMapper;
import com.shsxt.crm.db.dao.UserRoleMapper;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import com.shsxt.crm.vo.User;
import com.shsxt.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService extends BaseService<User,Integer> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public UserModel login(String userName, String password){
        /**
         * 1.参数校验
         *    用户名  密码非空
         * 2.根据用户名查找记录
         * 3.判断记录是否存在
         *   不存在  方法结束 返回结果
         * 4.记录存在  比对密码
         *    比对错误  方法结束 返回结果
         * 5.比对成功  返回登录用户信息
         */
       /* if(StringUtils.isBlank(userName)){
           throw  new ParamsException("用户名不能为空!");
        }
        if(StringUtils.isBlank(password)){
            throw  new ParamsException("密码不能为空!");
        }*/
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(password),"密码不能为空!");
        User user = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(null==user,"用户记录不存在或者已注销!");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(password))),"密码错误!");
        return  buildUserModelInfo(user);
    }

    private UserModel buildUserModelInfo(User user) {
        UserModel userModel=new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }



    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        /**
         * 1.参数校验
         *    userId 非空  记录必须存在  必须登录
         *    oldPassword 非空 与数据库密码一致
         *    newPassword confirmPassword 非空 必须相同 与原密码不能一致
         * 2.明文密码 md5 加密
         * 3.执行更新 返回结果
         */
        User user =userMapper.queryById(userId);
        AssertUtil.isTrue(null==userId || null == user,"用户不存在或未登录!");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword) || !(user.getUserPwd().equals(Md5Util.encode(oldPassword))),
                "原始密码不正确!");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword) || StringUtils.isBlank(confirmPassword),"新密码不能为空!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码不一致!");
        AssertUtil.isTrue(newPassword.equals(oldPassword),"新密码不能与原始密码一致!");
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(userMapper.update(user)<1,"密码更新失败!");
    }


    public void saveUser(User user){
        /**
         * 1.参数校验
         *     用户名 非空
         *     真实名 非空
         * 2.用户唯一校验
         *     用户名不能重复
         * 3.字段值设置
         *     userPwd=123456
         *     is_valid
         *     createDate
         *     updateDate
         */
        checkParams(user.getUserName(),user.getTrueName());
        AssertUtil.isTrue(null!=userMapper.queryUserByUserName(user.getUserName()),"该用户已存在!");
        user.setUserPwd(Md5Util.encode("123456"));
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(save(user)<1,"用户记录添加失败!");


        /**
         * 关联角色   关联中间表 t_user_role
         *    10  20 30 40
         */
        Integer userId = userMapper.queryUserByUserName(user.getUserName()).getId();
        relationUserRole(userId,user.getRoleIds());
    }

    private void relationUserRole(Integer userId, List<Integer> roleIds) {
        /**
         * 用户添加&更新 角色关联实现思路
         *  1. 如果用户角色记录存在
         *        删除原有用户角色记录
         *  2.如果集合roleIds 记录存在
         *       执行批量添加
         */
        int count = userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            //用户角色记录存在 -->删除原有用户角色记录
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色记录关联失败!");
        }
        if(null !=roleIds && roleIds.size()>0){
            List<UserRole> userRoles=new ArrayList<UserRole>();
            roleIds.forEach(rid->{
                UserRole userRole=new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(rid);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoles.add(userRole);
            });
            AssertUtil.isTrue(userRoleMapper.saveBatch(userRoles)!=roleIds.size(),"用户角色记录关联失败!");
        }


    }

    private void checkParams(String userName, String trueName) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(trueName),"请输入真实名称!");
    }


    public void updateUser(User user){
        /**
         * 1.参数校验
         *     用户名 非空
         *     真实名 非空
         *     id 非null  记录存在校验
         * 2.用户唯一校验
         *     用户名不能重复
         * 3.字段值设置
         *     updateDate
         */
        checkParams(user.getUserName(),user.getTrueName());
        User tempUser =queryById(user.getId());
        AssertUtil.isTrue(null==user.getId()||null == tempUser,"待更新用户不存在!");
        tempUser= userMapper.queryUserByUserName(user.getUserName());
        AssertUtil.isTrue(null !=tempUser && !(tempUser.getId().equals(user.getId())),"用户名已存在!");
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(update(user)<1,"用户更新失败!");

        relationUserRole(user.getId(),user.getRoleIds());




    }

    public void deleteUser(Integer userId){
        User user = queryById(userId);
        AssertUtil.isTrue(null==userId||null == user,"待删除的用户不存在!");

        /**
         * 级联删除从表记录
         */
        int count = userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            //用户角色记录存在 -->删除原有用户角色记录
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户删除失败!");
        }
        user.setIsValid(0);
        AssertUtil.isTrue(update(user)<1,"用户记录删除失败!");

    }




}

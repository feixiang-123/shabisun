package com.shsxt.crm.db.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    public int countUserRoleByUserId(Integer userId);

    public int deleteUserRoleByUserId(Integer userId);


    public int countUserRoleByRoleId(Integer roleId);

    public int deleteUserRoleByRoleId(Integer roleId);

}
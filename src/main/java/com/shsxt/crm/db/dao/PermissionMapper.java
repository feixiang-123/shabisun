package com.shsxt.crm.db.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    int countPermissionByRoleId(Integer rid);

    int  deleteByRoleId(Integer rid);

    List<Integer>  queryAllModuleIdsByRoleId(Integer rid);


    public List<String> queryAllModuleAclValueByUserId(Integer userId);
}
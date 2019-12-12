package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.db.dao.PermissionMapper;
import com.shsxt.crm.vo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Autowired
    private PermissionMapper permissionMapper;


    public List<String> queryAllModuleAclValueByUserId(Integer userId){
        return permissionMapper.queryAllModuleAclValueByUserId(userId);
    }
}

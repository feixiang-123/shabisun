package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.db.dao.ModuleMapper;
import com.shsxt.crm.db.dao.PermissionMapper;
import com.shsxt.crm.db.dao.RoleMapper;
import com.shsxt.crm.db.dao.UserRoleMapper;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.Permission;
import com.shsxt.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    public List<Map<String,Object>> queryAllRoles(){
        return roleMapper.queryAllRoles();
    }


    public void saveRole(Role role){
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名!");
        AssertUtil.isTrue(null != roleMapper.queryRoleByRoleName(role.getRoleName()),"角色已存在!");
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(save(role)<1,"角色记录添加失败!");
    }

    public void updateRole(Role role){
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名!");
        AssertUtil.isTrue(null==role.getId() || null == queryById(role.getId()),"待更新的角色记录不存在!");
        Role tempRole = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null !=tempRole && !(tempRole.getId().equals(role.getId())),"角色已存在!");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(update(role)<1,"角色记录更新失败!");
    }

    public void deleteRole(Integer roleId){
        Role role = queryById(roleId);
        AssertUtil.isTrue(null==roleId || null == role,"待删除的角色记录不存在!");


        /**
         * 从表记录删除
         */
        int count =userRoleMapper.countUserRoleByRoleId(roleId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByRoleId(roleId)!=count,"角色记录删除失败!");
        }

        role.setIsValid(0);
        AssertUtil.isTrue(update(role)<1,"角色记录删除失败!");
    }


    /**
     * 角色授权
     * @param mid   模块id
     * @param rid    角色id
     */
    public void addGrant(Integer[] mid, Integer rid) {
        /**
         *  角色授权支持多次授权
         *    第一次授权
         *        1-->1,,2,3,4
         *    第二次授权
         *       1-->1,3,5,6,7
         *     第三次授权
         *        1-->1
         *     第四次授权
         *        1--> mid==null
         * 数据添加实现思路
         *     如果角色存在原始权限
         *        先执行权限记录删除操作
         *     然后添加角色新的权限记录(mid 数据数据存在时)
         */
        int count = permissionMapper.countPermissionByRoleId(rid);
        if(count>0){
            AssertUtil.isTrue(permissionMapper.deleteByRoleId(rid)!=count,"角色授权失败!");
        }

        if(null !=mid && mid.length>0){
            List<Permission> permissions=new ArrayList<Permission>();
            for (Integer id : mid) {
                Permission permission=new Permission();
                permission.setRoleId(rid);
                permission.setModuleId(id);
                permission.setAclValue(moduleMapper.queryById(id).getOptValue());
                permission.setUpdateDate(new Date());
                permission.setCreateDate(new Date());
                permissions.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.saveBatch(permissions)!=mid.length,"角色授权失败!");
        }



    }
}

package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.db.dao.ModuleMapper;
import com.shsxt.crm.db.dao.PermissionMapper;
import com.shsxt.crm.model.TreeDto;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.Module;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("all")
public class ModuleService extends BaseService<Module, Integer> {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public List<Map<String, Object>> queryAllModules() {
        return moduleMapper.queryAllModules();
    }

    public List<TreeDto> queryAllModules02(Integer rid) {
        List<TreeDto> treeDtos = moduleMapper.queryAllModules02();
        List<Integer> roleHasMids = permissionMapper.queryAllModuleIdsByRoleId(rid);
        if (null != roleHasMids && roleHasMids.size() > 0) {
            treeDtos.forEach(treeDto -> {
                if (roleHasMids.contains(treeDto.getId())) {
                    treeDto.setChecked(true);
                }
            });
        }
        return treeDtos;
    }


    public void saveModule(Module module) {
        /**
         * 1.参数校验
         *     模块名非空   同一层级下  模块名不可重复
         *     grade 层级 非空 0|1|2
         *     顶级菜单 parentId  为空  二三级菜单 parentId 不能为空
         *     权限码  非空  不可重复
         * 2.设置字段值
         *      is_valid
         *      createDate
         *      updateDate
         * 3.执行添加 判断添加结果
         */
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "请输入模块名!");
        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade, "请指定层级!");
        AssertUtil.isTrue(!(grade == 1 || grade == 2 || grade == 0), "层级值非法!");
        Module temp = moduleMapper.queryModuleByGradeAndModuleName(module.getGrade(), module.getModuleName());
        AssertUtil.isTrue(null != temp, "同一层级下模块名不可重复!");
        if (grade == 1 || grade == 2) {
            AssertUtil.isTrue(null == module.getParentId(), "请指定上级菜单!");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "请输入菜单权限码!");
        temp = moduleMapper.queryModuleByOptValue(module.getOptValue());
        AssertUtil.isTrue(null != temp, "权限码不可重复!");
        module.setIsValid((byte) 1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(save(module) < 1, "模块添加失败!");
    }


    public void updateModule(Module module) {
        /**
         * 1.参数校验
         *     id 非空  记录必须存在
         *     模块名非空   同一层级下  模块名不可重复
         *     grade 层级 非空 0|1|2
         *     顶级菜单 parentId  为空  二三级菜单 parentId 不能为空
         *     权限码  非空  不可重复
         *
         * 2.设置字段值
         *      updateDate
         * 3.执行更新 判断更新结果
         */
        Integer mid = module.getId();
        AssertUtil.isTrue(null == mid || null == queryById(mid), "待更新的记录不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "请输入模块名!");
        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade, "请指定层级!");
        AssertUtil.isTrue(!(grade == 1 || grade == 2 || grade == 0), "层级值非法!");
        Module temp = moduleMapper.queryModuleByGradeAndModuleName(module.getGrade(), module.getModuleName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(module.getId())),"同一层级下模块名不可重复!");
        if (grade == 1 || grade == 2) {
            AssertUtil.isTrue(null == module.getParentId(), "请指定上级菜单!");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "请输入菜单权限码!");
        temp = moduleMapper.queryModuleByOptValue(module.getOptValue());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(module.getId())),"权限码不可重复!");
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(update(module)<1,"模块更新失败!");
    }


    public void deleteModuleByModuleId(Integer mid){
        Module module=queryById(mid);
        AssertUtil.isTrue(null == mid || null == module, "待删除的记录不存在!");
        /**
         * 如果当前待删除的菜单 存在子菜单  不允许删除当前菜单
         */
        int count =moduleMapper.countSubModulesByMid(mid);
        AssertUtil.isTrue(count>0,"当前菜单存在子菜单，暂不支持删除!");
        module.setIsValid((byte)0);
        AssertUtil.isTrue(update(module)<1,"模块删除成功!");
    }
}

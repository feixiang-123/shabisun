package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

public class ModuleQuery extends BaseQuery {

    //菜单层级  默认查询一级菜单
    private Integer grade=0;

    // 上级菜单id
    private Integer parentId;

    // 模块名
    private String moduleName;


    // 权限码
    private String optValue;

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOptValue() {
        return optValue;
    }

    public void setOptValue(String optValue) {
        this.optValue = optValue;
    }
}

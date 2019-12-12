function formatterGrade(value) {
    if(value==0){
        return "顶级菜单"
    }else  if(value==1){
        return "二级菜单"
    }else  if(value==2){
        return "三级菜单"
    }else{
        return "菜单未知"
    }
}


function formatterOp(value,rowData) {
    var grade =rowData.grade;
    var name="查看二级菜单";
    if(grade==1){
        name="查看三级菜单";
    }

    var href="javascript:openModuleTab('"+rowData.moduleName+"',"+rowData.id+")";

    return "<a href="+href+">"+name+"</a>";
}

function openModuleTab(title,id) {
    window.parent.openTab(title+"_二级菜单",ctx+"/module/index/1?parentId="+id)
}


function searchModule() {
    $("#dg").datagrid("load",{
        moduleName:$("#s_moduleName").val(),
        optValue:$("#s_optValue").val()
    })
}


function openModuleAddDialog() {
    openDialog("dlg","模块添加");
}

function closeModuleDialog() {
    closeDialog("dlg");
}

function saveOrUpdateModule() {
    saveOrUpdateRecode("fm",ctx+"/module/save",ctx+"/module/update","dlg",searchModule);
}

function openModuleModifyDialog() {
    openModifyDialog("dg","fm","dlg","模块更新");
}

function deleteModule() {
    deleteRecode("dg",ctx+"/module/delete",searchModule);
}


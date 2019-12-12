function searchRole() {
    $("#dg").datagrid("load", {
        roleName: $("#s_roleName").val()
    })
}


$(function () {
    $("#dlg").dialog({
        onClose: function () {
            // 清空表单数据
            $("#roleName").val("");
            $("#roleRemark").val("");
            $("#id").val("");
        }
    })
});

function openRoleAddDialog() {
    openDialog("dlg", "角色添加");
}

function closeRoleDialog() {
    closeDialog("dlg");
}


function saveOrUpdateRole() {
    saveOrUpdateRecode("fm", ctx + "/role/save", ctx + "/role/update", "dlg", searchRole);
}


function openRoleModifyDialog() {
    openModifyDialog("dg", "fm", "dlg", "角色更新");

}


function deleteRole() {

    deleteRecode("dg", ctx + "/role/delete", searchRole);
}

var zTreeObj;
function doGrant() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert("来自crm", "请选择待授权的角色!", "warning");
        return;
    }


    if (rows.length > 1) {
        $.messager.alert("来自crm", "暂不支持批量授权!", "warning");
        return;
    }


    $.ajax({
        type:"post",
        url:ctx+"/module/queryAllModules02?rid="+rows[0].id,
        dataType:"json",
        success:function (data) {
            // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                check: {
                    enable: true,
                    chkboxType: {
                        "Y": "ps",
                        "N": "ps"
                    }
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            zTreeObj=$.fn.zTree.init($("#treeDemo"), setting, data);
        }
    });

    $("#rid").val(rows[0].id);

    openDialog("menus", "角色授权");
}


function zTreeOnCheck(event, treeId, treeNode) {
    //alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
    var nodes= zTreeObj.getCheckedNodes(true);

    var midStr= "mid=";
    if(nodes.length>0){
        for(var i=0;i<nodes.length;i++){
            if(i<nodes.length-1){
                midStr=midStr+nodes[i].id+"&mid=";
            }else{
                midStr=midStr+nodes[i].id
            }
        }
    }

    $.ajax({
        type:"post",
        // 授权接口地址
        url:ctx+"/role/addGrant",
        data:midStr+"&rid="+$("#rid").val(),
        dataType:"json",
        success:function (data) {
            console.log(data);
        }
    })
}
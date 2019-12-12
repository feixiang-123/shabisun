function searchUser() {
    $("#dg").datagrid("load",{
        uname:$("#s_userName").val(),
        email:$("#s_email").val(),
        phone:$("#s_phone").val(),

    })
}


$(function () {
    $("#dlg").dialog({
        onClose:function () {
            // 清空表单数据
            $("#userName").val("");
            $("#email").val("");
            $("#phone").val("");
            $("#trueName").val("");
            $("#id").val("");
            $("#cc").combobox("setValue","");
        }
    })
});

function openUserAddDialog() {
     openDialog("dlg","用户添加");
}

function closeUserDialog() {
    closeDialog("dlg");
}



function saveOrUpdateUser() {
    saveOrUpdateRecode("fm",ctx+"/user/save",ctx+"/user/update","dlg",searchUser);
}



function openUserModifyDialog() {
  /*openModifyDialog("dg","fm","dlg","用户更新");*/
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert("来自crm", "请选择待修改的数据!", "warning");
        return;
    }

    if (rows.length > 1) {
        $.messager.alert("来自crm", "暂不支持批量修改!", "warning");
        return;
    }



    console.log(rows[0]);
    if(!(isEmpty(rows[0].roleIdStr))){
        rows[0].roleIds=rows[0].roleIdStr.split(",");
    }
    console.log(rows[0].roleIds);

    $("#fm").form("load", rows[0]);
    openDialog("dlg","用户更新");
}



function deleteUser() {
    deleteRecode("dg",ctx+"/user/delete",searchUser);
}
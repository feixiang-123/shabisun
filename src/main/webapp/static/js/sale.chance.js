function formatState(value) {
    if(value==0){
        return "未分配";
    }else if(value==1){
        return "已分配"
    }else{
        return "未知"
    }
}

function searchSaleChance() {
    $("#dg").datagrid("load",{
        customerName:$("#s_customerName").val(),
        createMan:$("#s_createMan").val(),
        state:$("#s_state").combobox("getValue")
    })
}


function openSaleChanceAddDialog() {
    $("#dlg").dialog("open").dialog("setTitle","营销机会添加");
}

function closeSaleChanceDialog() {
    $("#dlg").dialog("close");
}

$(function () {
    $("#dlg").dialog({
        onClose:function () {
            $("#customerName").val("");
            $("#chanceSource").val("");
            $("#linkMan").val("");
            $("#linkPhone").val("");
            $("#cgjl").val("");
            $("#overview").val("");
            $("#description").val("");
            $("#assignMan").combobox("setValue","");
            $("#id").val("");
        }
    });
});


function saveOrUpdateSaleChance() {
    var url=ctx+"/sale_chance/save";
    if(!isEmpty($("#id").val())){
        url=ctx+"/sale_chance/update";
    }

    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data = JSON.parse(data);
            if(data.code == 200){
                // 关闭对话框
                closeSaleChanceDialog();
                // 刷新表格
                searchSaleChance();
                // 清空表单数据   通过对话框关闭的监听事件实现
            }else {
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}


function openSaleChanceModifyDialog() {
    var rows= $("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待修改的数据!","warning");
        return;
    }

    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量修改!","warning");
        return;
    }

    $("#fm").form("load",rows[0]);
    $("#dlg").dialog("open").dialog("setTitle","营销机会数据更新");
}




function deleteSaleChance() {
    var rows= $("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待删除的数据!","warning");
        return;
    }

    var ids="ids=";
    for(var i=0;i<rows.length;i++){
        // i=0   ids=10&ids=
        // i=1   ids=10&ids=20&ids=
        // i=2  ids=10&ids=20&ids=30&ids=
        // i=3  ids=10&ids=20&ids=30&ids=40
        if(i<rows.length-1){
            ids=ids+rows[i].id+"&ids=";
        }else{
            ids=ids+rows[i].id;
        }
    }

    $.messager.confirm("来自crm","确定删除选中的记录?",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/sale_chance/delete",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        searchSaleChance();
                    }else {
                        $.messager.alert("来自crm",data.msg,"error");
                    }
                }
            })
        }
    })


}


function test() {
    window.parent.location.href=ctx+"/index";
}
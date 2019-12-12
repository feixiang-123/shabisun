$(function () {
    $('#dg').edatagrid({
        url:ctx+"/cus_dev_plan/list?sid="+$("#saleChanceId").val(),
        saveUrl:ctx+"/cus_dev_plan/save?saleChanceId="+$("#saleChanceId").val(),
        updateUrl:ctx+"/cus_dev_plan/update?saleChanceId="+$("#saleChanceId").val()
        //destroyUrl:ctx+"/cus_dev_plan/delete"
    });


    // 页面加载完毕 控制edatagrid 工具栏是否显示  开发中-显示   开发完毕-隐藏
    var devResult = $("#devResult").val();
    if(devResult==2||devResult==3){
        $("#toolbar").hide();
        // 禁用表格编辑功能
        $("#dg").edatagrid("disableEditing");
    }

});

/**
 * 保存或更新营销机会数据
 */
function saveCusDevPlan() {
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}


function delCusDevPlan() {
    var rows= $("#dg").edatagrid("getSelections");
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
                url:ctx+"/cus_dev_plan/delete",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        $("#dg").edatagrid("load");
                    }else {
                        $.messager.alert("来自crm",data.msg,"error");
                    }
                }
            })
        }
    })
}


function updateSaleChanceDevResult(result) {
    $.ajax({
        type:"post",
        url:ctx+"/sale_chance/updateSaleChanceDevResult",
        data:{
            devResult:result,
            sid:$("#saleChanceId").val()
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                // 隐藏工具栏
                $("#toolbar").hide();
                // 禁用表格编辑功能
                $("#dg").edatagrid("disableEditing");
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}
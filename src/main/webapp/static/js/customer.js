function searchCustomersByParams() {
    $("#dg").datagrid("load",{
        khno:$("#s_khno").val(),
        name:$("#s_name").val(),
        xyd:$("#s_xyd").combobox("getValue"),
        state:$("#s_state").combobox("getValue")
    })
}

function formatterState(val) {
    if(val==0){
        return "未流失"
    }else{
        return "已流失"
    }
}

function openCustomerAddDialog() {
    openDialog("dlg","客户添加");
}

function closeCustomerDialog() {
    closeDialog("dlg");
}


function saveOrUpdateCustomer() {
    saveOrUpdateRecode("fm",ctx+"/customer/save",ctx+"/customer/update","dlg",searchCustomersByParams);
}

function openCustomerModifyDialog() {
    openModifyDialog("dg","fm","dlg","客户更新");
}

function deleteCustomer() {
    deleteRecode("dg",ctx+"/customer/delete",searchCustomersByParams);
}


$(function () {
   $("#dlg").dialog({
       onClose:function () {
           $("#name").val("");
           $("#id").val("");
           $("#xyd").combobox("setValue","");
       }
   })
});
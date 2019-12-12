$(function () {
    /*$("#dlg").dialog({
            onClose: function () {

            }
        }
    )*/
});

/**
 * 打开对话框
 * @param dialogId  对话框id
 * @param title     对话框标题
 */
function openDialog(dialogId, title) {
    $("#" + dialogId).dialog("open").dialog("setTitle", title);
}


/**
 * 关闭对话框
 * @param dialogId
 */
function closeDialog(dialogId) {
    $("#" + dialogId).dialog("close")
}


function saveOrUpdateRecode(formId, saveUrl, updateUrl, dialogId, search) {
    var url = saveUrl;
    if (!isEmpty($("#id").val())) {
        url = updateUrl;
    }

    $("#" + formId).form("submit", {
        url: url,
        onSubmit: function () {
            return $("#" + formId).form("validate");
        },
        success: function (data) {
            data = JSON.parse(data);
            if (data.code == 200) {
                // 关闭对话框
                closeDialog(dialogId);
                // 刷新表格
                search();
            } else {
                $.messager.alert("来自crm", data.msg, "error");
            }
        }
    })
}


function openModifyDialog(dataGridId, formId, dialogId, title) {
    var rows = $("#" + dataGridId).datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert("来自crm", "请选择待修改的数据!", "warning");
        return;
    }

    if (rows.length > 1) {
        $.messager.alert("来自crm", "暂不支持批量修改!", "warning");
        return;
    }
    $("#" + formId).form("load", rows[0]);
    openDialog(dialogId, title);
}


function deleteRecode(dataGridId, deleteUrl, search) {
    var rows = $("#" + dataGridId).datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert("来自crm", "请选择待删除的数据!", "warning");
        return;
    }


    if (rows.length > 1) {
        $.messager.alert("来自crm", "暂不支持批量删除!", "warning");
        return;
    }

    $.messager.confirm("来自crm", "确定删除选中的记录?", function (r) {
        if (r) {
            $.ajax({
                type: "post",
                url: deleteUrl,
                data: {
                    id: rows[0].id
                },
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        search();
                    } else {
                        $.messager.alert("来自crm", data.msg, "error");
                    }
                }
            })
        }
    })
}

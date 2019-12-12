<!--
  角色管理主页面
-->
<!doctype html>
<html>
<head>
    <#include "common.ftl" >
    <script type="text/javascript" src="${ctx}/static/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/base.js"></script>
    <link rel="stylesheet" href="${ctx}/static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/role.js"></script>
</head>
<body style="margin: 1px">
<table id="dg"  class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${ctx}/role/list" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="roleName" width="200" align="center" >角色名</th>
        <th field="roleRemark" width="50" align="center">备注</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:openRoleAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建</a>
        <a href="javascript:openRoleModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:deleteRole()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
        <a href="javascript:doGrant()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">授权</a>
    </div>
    <div>
        角色名： <input type="text" id="s_roleName" size="20" onkeydown="if(event.keyCode==13) searchRole()"/>
        <a href="javascript:searchRole()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>



<div id="dlg" class="easyui-dialog" style="width:600px;height:260px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>角色名：</td>
                <td><input type="text" id="roleName" name="roleName" class="easyui-validatebox" required="true"/> <font color="red">*</font></td>
                <td>    </td>
                <td>备注</td>
                <td><input type="text" id="roleRemark" name="roleRemark" /></td>
            </tr>
        </table>
        <input name="id" type="hidden" id="id"/>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveOrUpdateRole()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeRoleDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>




<div id="menus" class="easyui-dialog" style="width:400px;height:300px;padding: 10px 20px"
     closed="true" >
    <ul id="treeDemo" class="ztree"></ul>
    <input type="hidden" id="rid" />
</div>



</body>
</html>

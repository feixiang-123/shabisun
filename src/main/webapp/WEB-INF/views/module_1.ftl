<!--
  一级菜单管理主页面
-->
<!doctype html>
<html>
<head>
    <#include "common.ftl" >
    <script type="text/javascript" src="${ctx}/static/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/base.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/module_1.js"></script>
</head>
<body style="margin: 1px">
<table id="dg"  class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${ctx}/module/list" fit="true" toolbar="#tb" singleSelect="true">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="moduleName" width="200" align="center" >菜单名</th>
        <th field="moduleStyle" width="50" align="center">样式</th>
        <th field="url" width="50" align="center" >URL</th>
        <th field="grade" width="200" align="center" formatter="formatterGrade">层级</th>
        <th field="optValue" width="200" align="center">权限码值</th>
        <th field="orders" width="200" align="center">排序</th>
        <th field="createDate" width="100" align="center">创建时间</th>
        <th field="updateDate" width="200" align="center" >更新时间</th>
        <th field="op" width="200" align="center"  formatter="formatterOp">操作</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:openModuleAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建</a>
        <a href="javascript:openModuleModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:deleteModule()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>
    <div>
        模块名： <input type="text" id="s_moduleName" size="20" onkeydown="if(event.keyCode==13) searchModule()"/>
        权限码： <input type="text" id="s_optValue" size="20" onkeydown="if(event.keyCode==13) searchModule()"/>
        <a href="javascript:searchModule()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>



<div id="dlg" class="easyui-dialog" style="width:600px;height:260px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>模块名：</td>
                <td><input type="text" id="moduleName" name="moduleName" class="easyui-validatebox" required="true"/> <font color="red">*</font></td>
                <td>    </td>
                <td>样式</td>
                <td><input type="text" id="email" name="moduleStyle" /></td>
            </tr>
            <tr>
                <td>URL：</td>
                <td><input type="text" id="url" name="url"  /></td>
                <td>    </td>
                <td>权限码：</td>
                <td><input type="text" id="optValue" name="optValue"  class="easyui-validatebox" required="true" /><font color="red">*</font></td>
            </tr>
            <tr>
                <td>排序：</td>
                <td><input type="text" id="orders" name="orders"  /></td>
            </tr>

        </table>
        <input name="id" type="hidden" id="id"/>
        <input name="grade" type="hidden" id="grade" value="0"/>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveOrUpdateModule()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeModuleDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


</body>
</html>

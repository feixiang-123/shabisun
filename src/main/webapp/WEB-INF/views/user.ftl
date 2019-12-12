<!--
  用户管理主页面
-->
<!doctype html>
<html>
<head>
    <#include "common.ftl" >
    <script type="text/javascript" src="${ctx}/static/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/base.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/user02.js"></script>
</head>
<body style="margin: 1px">
<table id="dg"  class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${ctx}/user/list" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="userName" width="200" align="center" >用户名</th>
        <th field="email" width="50" align="center">邮箱</th>
        <th field="phone" width="50" align="center" >手机号</th>
        <th field="trueName" width="200" align="center">真实名称</th>
        <th field="roleName" width="200" align="center">角色</th>
        <th field="createDate" width="100" align="center">创建时间</th>
        <th field="updateDate" width="200" align="center" >更新时间</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:openUserAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建</a>
        <a href="javascript:openUserModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:deleteUser()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>
    <div>
        用户名： <input type="text" id="s_userName" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
        邮箱： <input type="text" id="s_email" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
        手机号： <input type="text" id="s_phone" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
        <a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>



<div id="dlg" class="easyui-dialog" style="width:600px;height:260px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>用户名：</td>
                <td><input type="text" id="userName" name="userName" class="easyui-validatebox" required="true"/> <font color="red">*</font></td>
                <td>    </td>
                <td>邮箱</td>
                <td><input type="text" id="email" name="email" /></td>
            </tr>
            <tr>
                <td>真实姓名：</td>
                <td><input type="text" id="trueName" name="trueName" class="easyui-validatebox" required="true" /> <font color="red">*</font></td>
                <td>    </td>
                <td>联系电话：</td>
                <td><input type="text" id="phone" name="phone"  /></td>
            </tr>
            <tr>
                <td>角色：</td>
                <td>
                    <input id="cc" class="easyui-combobox" name="roleIds"
                           valueField="id" textField="roleName"  style="width: 240px" multiple="true" url="${ctx}/role/queryAllRoles" panelHeight="auto" />
                   <#-- <select id="cc" class="easyui-combobox" name="dept" style="width:200px;" panelHeight="auto" multiple="true">
                        <option value="aa">aitem1</option>
                        <option>bitem2</option>
                        <option>bitem3</option>
                        <option>ditem4</option>
                        <option>eitem5</option>
                    </select>-->
                </td>
            </tr>

        </table>
        <input name="id" type="hidden" id="id"/>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveOrUpdateUser()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeUserDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


</body>
</html>

<!--
  营销机会管理主页面
-->
<!doctype html>
<html>
<head>
    <#include "common.ftl" >
    <script type="text/javascript" src="${ctx}/static/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/sale.chance.js"></script>
</head>
<body style="margin: 1px">
<table id="dg"  class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${ctx}/sale_chance/list" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="chanceSource" width="200" align="center" hidden="true">机会来源</th>
        <th field="customerName" width="50" align="center">客户名称</th>
        <th field="cgjl" width="50" align="center" hidden="true">成功几率</th>
        <th field="overview" width="200" align="center">概要</th>
        <th field="linkMan" width="100" align="center">联系人</th>
        <th field="linkPhone" width="100" align="center">联系电话</th>
        <th field="description" width="200" align="center" hidden="true">机会描述</th>
        <th field="createMan" width="100" align="center">创建人</th>
        <th field="createDate" width="100" align="center">创建时间</th>
        <th field="assignMan" width="200" align="center" hidden="true">指派人</th>
        <th field="assignTime" width="200" align="center" hidden="true">指派时间</th>
        <th field="state" width="100" align="center" formatter="formatState">状态</th>
        <th field="devResult" width="200" align="center" hidden="true">客户开发状态</th>
    </tr>
    </thead>
</table>
<#if permissions??>
    <div id="tb">

        <div>
            <#if permissions?seq_contains("101002")>
                <a href="javascript:openSaleChanceAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建</a>
            </#if>
            <#if permissions?seq_contains("101004")>
                <a href="javascript:openSaleChanceModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
            </#if>
            <#if permissions?seq_contains("101003")>
                <a href="javascript:deleteSaleChance()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
            </#if>
        </div>
        <#if permissions?seq_contains("101001")>
            <div>
                客户名称： <input type="text" id="s_customerName" size="20" onkeydown="if(event.keyCode==13) searchSaleChance()"/>
                创建人： <input type="text" id="s_createMan" size="20" onkeydown="if(event.keyCode==13) searchSaleChance()"/>
                分配状态： <select class="easyui-combobox" id="s_state" editable="false" panelHeight="auto" >
                    <option value="">请选择...</option>
                    <option value="0">未分配</option>
                    <option value="1">已分配</option>
                </select>
                <a href="javascript:searchSaleChance()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
            </div>
        </#if>
    </div>
</#if>


<div id="dlg" class="easyui-dialog" style="width:700px;height:450px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>客户名称：</td>
                <td><input type="text" id="customerName" name="customerName" class="easyui-validatebox" required="true"/> <font color="red">*</font></td>
                <td>    </td>
                <td>机会来源</td>
                <td><input type="text" id="chanceSource" name="chanceSource" /></td>
            </tr>
            <tr>
                <td>联系人：</td>
                <td><input type="text" id="linkMan" name="linkMan" class="easyui-validatebox" required="true" /> <font color="red">*</font></td>
                <td>    </td>
                <td>联系电话：</td>
                <td><input type="text" id="linkPhone" name="linkPhone"  class="easyui-validatebox" required="true"/> <font color="red">*</font></td>
            </tr>
            <tr>
                <td>成功几率(%)：</td>
                <td><input type="text" id="cgjl" name="cgjl" /></td>
                <td colspan="3">    </td>
            </tr>
            <tr>
                <td>概要：</td>
                <td colspan="4"><input type="text" id="overview" name="overview" style="width: 420px"/></td>
            </tr>
            <tr>
                <td>机会描述：</td>
                <td colspan="4">
                    <textarea rows="5" cols="50" id="description" name="description"></textarea>
                </td>
            </tr>
            <#--<tr>
                <td>指派给：</td>
                <td><input class="easyui-combobox" id="assignMan" name="assignMan" data-options="panelHeight:'auto',editable:false,valueField:'trueName',textField:'trueName',url:'${ctx}/user/queryCustomerManager.do'"/></td>
            </tr>-->
        </table>

        <input name="id" type="hidden" id="id"/>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveOrUpdateSaleChance()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeSaleChanceDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


</body>
</html>

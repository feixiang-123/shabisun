<html>
<head>
    <#include "common.ftl" >
    <script type="text/javascript" src="${ctx}/static/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/customer.order.js"></script>
</head>
<body style="margin: 15px">
<#--客户信息详情展示-->
<div id="p" class="easyui-panel" title="客户信息" style="width: 700px;height: 200px;padding: 10px">
    <table cellspacing="8px">
        <input type="hidden" id="cid" name="cid" value="${customer.id}"/>
        <tr>
            <td>客户名称：</td>
            <td><input type="text"  name="name" readonly="readonly" value="${customer.name?if_exists}" /></td>
            <td>    </td>
            <td>法人</td>
            <td><input type="text" name="fr" readonly="readonly" value="${customer.fr?if_exists}"/></td>
        </tr>
        <tr>
            <td>客户经理：</td>
            <td><input type="text"  readonly="readonly" value="${customer.cusManager?if_exists}"/></td>
            <td>    </td>
            <td>联系电话：</td>
            <td><input type="text" name="phone" readonly="readonly" value="${customer.phone?if_exists}"/></td>
        </tr>

    </table>
</div>
<br/>


<table id="dg"  class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${ctx}/customer_order/list?cusId=${customer.id}"toolbar="#tb" style="width: 800px;" title="订单列表展示" toolbar="#tb" >
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">id</th>
        <th field="orderNo" width="100" align="center" >订单编号</th>
        <th field="orderDate" width="100" align="center">下单日期</th>
        <th field="address" width="100" align="center">收货地址</th>
        <th field="state" width="50" align="center" formatter="formatterState">支付状态</th>
        <th field="createDate" width="100" align="center">创建日期</th>
        <th field="updateDate" width="100" align="center">更新日期</th>
        <th field="op" width="100" align="center" formatter="formatterOp">操作</th>
    </tr>
    </thead>
</table>




<div id="tb">
    <div>
        订单编号： <input type="text" id="s_orderNo" size="20" onkeydown="if(event.keyCode==13) searchCustomerOrdersByParams()"/>
        支付状态： <select class="easyui-combobox" id="s_state" editable="false" panelHeight="auto">
            <option value="">请选择...</option>
            <option value="1">支付</option>
            <option value="0">未支付</option>
        </select>
        <a href="javascript:searchCustomerOrdersByParams()" class="easyui-linkbutton" iconCls="icon-search"
           plain="true">搜索</a>
    </div>
</div>





</body>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/html">
<head>
    <link rel="stylesheet" href="css/style.css" media="all" />
    <link rel="stylesheet" href="js/layui/css/layui.css" media="all" />
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="js/layer.js"></script>
    <title>lead rsync from gf</title>
</head>

<body >
<!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<div class="layui-container">
    <table class="layui-hide" id="gf_domain_check"></table>
    <input type="hidden" id="domain" value="${domain}">
    <input type="hidden" id="type" value="${type}">
    <input type="hidden" id="env" value="${env}">
    <input type="hidden" id="reason" value="${reason}">
    <input type="hidden" id="total" value="${total}">
</div>
<script type="text/javascript" src="js/layui/layui.js"></script>
<script type="text/javascript">

    layui.use('table', function(){
        var table = layui.table;
        var domain=$('#domain').val();
        var type=$('#type').val();
        var env=$('#env').val();
        var reason=$('#reason').val();
        var total=$('#total').val();

        var queryUrl = '/gfDomainLeadCheckResult?domain='+domain+'&type='+type+'&reason='+reason+'&env='+env+'&total='+total;
        if("full" == type){
            table.render({
                elem: '#gf_domain_check'
                ,url:queryUrl
                ,cols: [[
                    {field:'gfLeadId', width:80, title: 'GfLeadId', sort: true}
                    ,{field:'email', width:280, title: 'Email'}
                    ,{field:'name', width:180, title: 'name', sort: true}
                    ,{field:'msg', minWidth: 350, title: 'Message'}
                ]]
                ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout: ['count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    ,groups: 1 //只显示 1 个连续页码
                    ,limit: 20
                }
            });
        }else{
            table.render({
                elem: '#gf_domain_check'
                ,url:queryUrl
                ,cols: [[
                    {field:'gfLeadId', width:100, title: 'GfLeadId', sort: true}
                    ,{field:'address', width:280, title: 'Address'}
                    ,{field:'msg', width:480, title: 'Message', sort: true}
                ]]
                ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout: ['count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                    ,groups: 5 //只显示 1 个连续页码
//                    ,first: true //不显示首页
//                    ,last: true //不显示尾页
                    ,limit:20
                }
            });
        }
    });
</script>

</body>


</html>

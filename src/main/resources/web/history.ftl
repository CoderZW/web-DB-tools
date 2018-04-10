<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>历史记录</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/bootstrap-table.min.css">
    <link type="text/css" rel="stylesheet" href="css/operater.css">
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-table.js"></script>
</head>

<body>
<header>

    <section class="header-container">
        <div class="logo">

            <embed src="https://static.chimeroi.com/home/images/logo/logo2.svg" onload="this.style.opacity = '1'" style="opacity: 1;">
                <img class="blackChime" style="display: none" src="//static.chimeroi.com/crm/images/logo/chime2.svg" alt="">
                <a href="" class="logo"></a>
        </div>
    </section>
</header>

<div>
    <input id="myinputgsw" type="hidden" value='${historySqlList}' />
</div>

<div class="data-form">
    <span class="topBar">Hi ${user.account}&nbsp;, 上次登录ip：${user.lastLoginIp} , 上次登录时间：${user.lastLoginTime?string("yyyy-MM-dd HH:mm:ss zzzz")} ,  <a href="/operate"> 返回</a> </span>
</div>

<div style="margin-top: 70px;">
    <form id="table-result-div" role="form" onsubmit="return false;">
        <div class="form-group" id="result-table">
            <table id="tb_departments" ></table>
        </div>

    </form>
</div>

<script type="text/javascript" defer=true>
    //格式化代码函数,已经用原生方式写好了不需要改动,直接引用就好

    function getColumns(data){
        var cloumnsStr =[];
        console.log(data.length);
        if(data.length == 0){
            cloumnsStr.push({field:"null_result",title:"执行成功，0跳记录！"})
        }else{
            for(var item in data[0]){
                cloumnsStr.push({field:item,title:item})
            }
        }
        console.log(cloumnsStr);
        return cloumnsStr;
    }

    function formartTable(mdata){
        document.getElementById('result-table').innerHTML='<table id="tb_departments"></table>'
        $('#tb_departments').bootstrapTable({
                                                toolbar: '#toolbar',                //工具按钮用哪个容器
                                                striped: true,                      //是否显示行间隔色
                                                method: 'POST',
                                                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                                                pagination: true,                   //是否显示分页（*）
                                                sortable: false,                     //是否启用排序
                                                sortOrder: "asc",                   //排序方式
//                                     queryParams:queryParams,//传递参数（*）
                                                contentType : "application/x-www-form-urlencoded",
                                                escape : true,
                                                sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                                                pageNumber:1,                       //初始化加载第一页，默认第一页
                                                pageSize: 15,                       //每页的记录行数（*）
                                                pageList: [15, 50, 200,500],        //可供选择的每页的行数（*）
                                                search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                                            //    searchOnEnterKey: true,
                                                //   strictSearch: true,
                                                showColumns: true,                  //是否显示所有的列
                                            //    showPaginationSwitch: true,
//            showRefresh: true,                  //是否显示刷新按钮
                                                minimumCountColumns: 2,             //最少允许的列数
                                                clickToSelect: true,                //是否启用点击选中行
                                                //height: 1000,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                                                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                                                showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
                                                cardView: false,                    //是否显示详细视图
                                                detailView: false,                   //是否显示父子表
                                                columns: getColumns(mdata),
                                                data: mdata
                                            });
    }

    $('#result-table').on('click-row.bs.table', function (e, row, element)
    {
        $(element).css({"color":"blue","font-size":"16px;"});
        window.location.href="/operate?s=" + row.id;
        console.log(row);
    });

    window.onload = function() {
        var reg = /\%\+/g;
        var data=$("#myinputgsw").val();
        data=data.replace(reg,"'");
        var result = eval('(' + data + ')');
        var mdata=eval(result);
        formartTable(mdata);
        $("#table-result-div").show();
    }


</script>

</body>


<#include "/macros/page_macro.ftl">
<@webpage pageCss="css/operater.css" title="域名信息查询">

<header>

    <section class="header-container">
        <div class="logo">

            <embed src="https://static.chimeroi.com/home/images/logo/logo2.svg" onload="this.style.opacity = '1'" style="opacity: 1;">
                <img class="blackChime" style="display: none" src="//static.chimeroi.com/crm/images/logo/chime2.svg" alt="">
                <a href="" class="logo"></a>
        </div>

    </section>
</header>
<body>
<#if user?exists>
<div class="data-form" style="margin-top: 75px;">
    <span class="topBar">Hi ${user.account}&nbsp;, 上次登录ip：${user.lastLoginIp} , 上次登录时间：${user.lastLoginTime?string("yyyy-MM-dd HH:mm:ss zzzz")} , <#if user.level == 9> <a href="/create_user" >添加用户</a> | </#if><a href="/update_password" >修改密码</a> | <a href="/logout" >退出</a> | <a href="/history" >历史记录</a></span>
</div>
</#if>
<HR>
<div class="container-fluid" <#if !(user?exists)>style="margin-top: 75px;"</#if>>

    <div class="row">
        <div style="height: 100px;margin-bottom: 15px;">
            <div class="col-xs-12 col-sm-9 col-md-10">
                <div class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">域名</label>
                        <div class="col-sm-4">
                            <input class="form-control" id="dataExcSql" placeholder="请输入域名"/>
                            <p>域名请不要输入http://或https://,且不要输入参数及uri</p>
                            <p>正确示例：chime.me,site.chime.me,www.site.chime.me</p>
                        </div>

                        <div class="col-sm-2" >
                            <div class="ExcSqlBtn">
                                <button  class="btn btn-default" data-loading-text="Loading..." id="excSubmit">查询</button>
                            </div>
                        </div>
                    </div>


                </div>

            </div>
        </div>

        <div style="width:90%;margin: 0 auto;" >
            <form id="loading" class="form-horizontal" role="form" style="display:none">
                <div class="form-group">
                    <div class="loading">玩儿命加载中...</div>
                </div>

            </form>
            <div id="result_area" style="display: none;">
                <div class="form-group" id="prdoduction_database">
                    <div class="form-group alert alert-warning" id="is_default_ns">

                    </div>
                </div>
                <div>
                    <ul id="myTab" class="nav nav-tabs">
                        <li class="active">
                            <a href="#records" data-toggle="tab">
                                Records
                            </a>
                        </li>
                        <li><a href="#infoDetail" data-toggle="tab">Info Detail</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane fade in active" id="records">
                            <form id="table-result-div" role="form" >

                                <div class="form-group" id="result-table">

                                    <table id="tb_departments" ></table>

                                </div>

                            </form>
                        </div>
                        <div class="tab-pane fade" id="infoDetail">
                            <div class="tab-pane fade in active" id="infoDetail">
                                <form id="table-result-div2" role="form" >

                                    <div class="form-group" id="result-table2">

                                        <table id="tb_departments2" ></table>

                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>


    <script type="text/javascript" defer=true>

        function displayResult(){
            $("#result_area").hide();
        }

        function displayTable(){
            $("#result_area").show();
        }

        function hideResultAndDescription(){
            $("#result_area").hide();
        }

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
            document.getElementById('result-table').innerHTML='<table id="tb_departments"></table>';
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
                showExport: true,                     //是否显示导出
                exportDataType: "all",              //basic', 'all', 'selected'.
                exportTypes: [ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx'],
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 15,                       //每页的记录行数（*）
                pageList: [10, 50, 200,500],        //可供选择的每页的行数（*）
                search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                //          strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                contentType : "application/x-www-form-urlencoded",
                escape : true,
                sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                showExport: true,                     //是否显示导出
                exportDataType: "all",              //basic', 'all', 'selected'.
                exportTypes: [ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx'],
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 15,                       //每页的记录行数（*）
                pageList: [10, 50, 200,500],        //可供选择的每页的行数（*）
                search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                //          strictSearch: true,
                showColumns: true,                  //是否显示所有的列
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


        function formartTable2(mdata){
            document.getElementById('result-table2').innerHTML='<table id="tb_departments2"></table>';
            $('#tb_departments2').bootstrapTable({
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
                showExport: true,                     //是否显示导出
                exportDataType: "all",              //basic', 'all', 'selected'.
                exportTypes: [ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx'],
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 15,                       //每页的记录行数（*）
                pageList: [10, 50, 200,500],        //可供选择的每页的行数（*）
                search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                //          strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                contentType : "application/x-www-form-urlencoded",
                escape : true,
                sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                showExport: true,                     //是否显示导出
                exportDataType: "all",              //basic', 'all', 'selected'.
                exportTypes: [ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx'],
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 15,                       //每页的记录行数（*）
                pageList: [10, 50, 200,500],        //可供选择的每页的行数（*）
                search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                //          strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                //            showRefresh: true,                  //是否显示刷新按钮
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
                //height: 1000,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
                cardView: true,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                columns: getColumns(mdata),
                data: mdata


            });
        }

        $("#excSubmit").on("click", function () {
            console.log($("#dataExcSql").val())
            if (!$("#dataExcSql").val()) {
                layer.msg("输入框不能为空！");
                return false;
            } else {
                var reg = /\s+/g;
                var s=$("#dataExcSql").val().replace(reg,"");
                var redomain = /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/;
                var url=redomain.exec(s);
                if(url == null || url == ''){
                    layer.msg("输入框的域名不正确！");
                    return false;
                }
                $("#excSubmit").attr("disabled",true);
                $('#loading').show();
                hideResultAndDescription();

                $.ajax({
                    type: "POST",
                    url: "domainInfo/getDomainInfo",
                    data: {
                        "domain": $("#dataExcSql").val()
                    },
                    success: function (msg) {
                        if(msg.indexOf('page-id="databse-login-page"')>-1){
                            window.location.reload();
                            return false;
                        }
                        $("#excSubmit").attr("disabled",false);
                        $('#loading').hide();
                        var result = eval('(' + msg + ')');
                        var mdata=eval(result.data.dnsRecords);
                        var infodata=eval(result.data.domainInfo);
                        var nsFlag=result.data.nsFlag;
                        formartTable(mdata);
                        formartTable2(infodata);
                        displayTable();
                        var nsDescription='NS不明确，确认可登录后，由技术人员确认！';
                        if("1" == nsFlag){
                            nsDescription='<p style="color: #009900">该域名NS为godaddy默认NS！</p>';
                        }else if("2" == nsFlag){
                            nsDescription='<p style="color: red">该域名为GF默认NS！</p>';
                        }

                        document.getElementById('is_default_ns').innerHTML=nsDescription;

                    },
                    error: function (error) {
                        $("#excSubmit").attr("disabled",false);
                        $('#loading').hide();
                        layer.msg("server error");
                    }
                })

            }
            return false;
        });



    </script>
</@webpage>
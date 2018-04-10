<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSON在线解析</title>

    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="css/operater.css">
    <link rel="stylesheet" href="css/codemirror.css">
    <link rel="stylesheet" href="css/eclipse.css">
    <link rel="stylesheet" href="css/jquery.jsonview.css">
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.jsonview.js"></script>
    <script type="text/javascript" src="js/codemirror.js"></script>
    <script type="text/javascript" src="js/formatting.js"></script>
    <script type="text/javascript" src="js/vim.js"></script>
    <script type="text/javascript" src="js/javascript.js"></script>
    <script type="text/javascript" src="js/layer.js"></script>

    <style>
        .CodeMirror { height: auto; border: 1px solid #ddd; }
        .CodeMirror-scroll { max-height: 200px; border-left: 30px;}
        .CodeMirror pre { padding-left: 7px; line-height: 1.25; }
    </style>
</head>
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
        <div style="height: 300px">
            <div class="col-xs-12 col-sm-9 col-md-10">
                <div class="form-horizontal" role="form">


                    <div class="form-group dataExcSqlDiv">
                    <#-- <label for="name" class="col-sm-2 control-label">SQL</label> -->
                        <label for="name" class="col-sm-2 control-label">JSON</label>
                        <div class="col-sm-7">
                            <textarea class="form-control sql-textarea" rows="2" cols="10"
                                      id="dataExcSql"  ></textarea>


                            <script type="text/javascript">
                                var editor1=CodeMirror.fromTextArea(document.getElementById("dataExcSql"),{
                                    lineNumbers: true,
                                    lineWrapping: true,
                                    matchBrackets: true,
                                    //Js高亮显示
                                    mode:"application/json",
                                    //设置主题
                                    theme:"eclipse",
                                    //快捷键
                                    extraKeys:{
                                        "F2": function autoFormat(editor1) {
                                            $("#excSubmit").click();
                                        }//代码格式化
                                    },
                                });
                                editor1.setSize('100%','180px');
                            </script>

                        </div>


                    </div>


                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10 dataExcSqlDiv" >
                            <div class="ExcSqlBtn">
                                <button  class="btn btn-default" data-loading-text="Loading..." id="excSubmit">执行</button>
                                <button  class="btn btn-default" id="reset">清空</button>
                                <div class="btn-group" data-toggle="buttons-radio" id="tools_btn" style="margin-left: 1%;display: none">
                                    <button class="btn btn-primary" id="fold_node">折叠</button>
                                    <button class="btn btn-primary" id="open_node">展开</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-group" id="prdoduction_database" style="display: none;height: 80px;">
                    <div class="col-sm-offset-2 col-sm-6 alert alert-warning" style="margin-left: 18%">
                        <a href="#" class="prdoduction_close" data-dismiss="prdoduction_alert">&times;</a>
                        <strong id="json_error"></strong>
                    </div>
                </div>
                <form id="sqlExcResultJson" class="form-horizontal" role="form" style="display:none" enctype="application/x-www-form-urlencoded" >
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">执行结果</label>
                        <div class="col-sm-7">
                            <br>
                        <#--   <textarea class="form-control data-result" rows="5"
                                      id="dataExcResult"></textarea>-->
                            <input type="hidden" value="" id="data_json">
                            <div id="json-collapsed"></div>
                        </div>

                    </div>

                </form>
            </div>
        </div>

        <div style="width:90%;margin: 0 auto;" >
            <form id="loading" class="form-horizontal" role="form" style="display:none">
                <div class="form-group">
                    <div class="loading">玩儿命加载中...</div>
                </div>

            </form>

            <form id="table-result-div" role="form" >

                <div class="form-group" id="result-table">

                    <table id="tb_departments" ></table>

                </div>

            </form>



        </div>
    </div>
</div>


<!-- 模态框（Modal） -->
<#--<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">-->
<#--<div class="modal-dialog">-->
<#--<div class="modal-content">-->
<#--<div class="modal-header">-->
<#--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">-->
<#--&times;-->
<#--</button>-->
<#--<h4 class="modal-title" id="myModalLabel">-->
<#--取个名字吧!-->
<#--</h4>-->
<#--</div>-->
<#--<div class="modal-body">-->
<#--<input class="col-sm-10" id="favoriteSqlName"/>-->
<#--</div>-->
<#--<div class="modal-footer">-->
<#--<button type="button" class="btn btn-default" data-dismiss="modal">关闭-->
<#--</button>-->
<#--<button type="button" class="btn btn-primary" id="1favoriteSubmit">-->
<#--提交-->
<#--</button>-->
<#--</div>-->
<#--</div><!-- /.modal-content &ndash;&gt;-->
<#--</div><!-- /.modal &ndash;&gt;-->
<#--</div>-->
<#--<script type="text/javascript" src="//static.chimeroi.com/lib/jquery-1.11.3.min.js"></script>-->

<script type="text/javascript" defer=true>
    //    $(function () {
    //        $("[data-toggle='popover']").popover();
    //    });

    //    $(".favoriteSql").hover(function(){
    //        $(this).popover('show');
    //    },function(){
    //        $(this).popover('hide');
    //    });
    //    var editor1 = CodeMirror.fromTextArea(document.getElementById("dataExcSql"), {
    //        lineNumbers: true,
    //        mode: "text/x-mysql",
    //        matchBrackets: true
    //    });
    //    var editor2 = CodeMirror.fromTextArea(document.getElementById("#editSql"), {
    //        lineNumbers: true,
    //        mode: "text/x-mysql",
    //        matchBrackets: true
    //    });

    //格式化代码函数,已经用原生方式写好了不需要改动,直接引用就好

    function displayJsonResult(){
        $("#sqlExcResultJson").show();
        $("#table-result-div").hide();
    }

    function displayTable(){
        $("#sqlExcResult").hide();
        $("#table-result-div").show();
    }

    function hideResultAndDescription(){
        $("#sqlExcResult").hide();
        $("#table-result-div").hide();
    }

    function queryParams(params) {
        var temp = {
            env : $("input[name='env']:checked").val(),
            schema: $("#dataSchema").val(),
            statement: editor1.getValue()
        };
        return temp;
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
            showExport: true,                     //是否显示导出
            exportDataType: "all",              //basic', 'all', 'selected'.
            exportTypes: [ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx'],
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 15,                       //每页的记录行数（*）
            pageList: [10, 50, 200,500],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            searchOnEnterKey: true,
            //          strictSearch: true,
            showColumns: true,                  //是否显示所有的列
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


    $("#excSubmit").on("click", function () {
        console.log(editor1.getValue());
        $('#prdoduction_database').hide();
        $('#tools_btn').hide();
        if (!editor1.getValue()) {
            layer.msg("输入框为空");
            return false;
        } else {
            $("#excSubmit").attr("disabled",true);
//            $('#loading').show();
            hideResultAndDescription();
//            var reg = /\ +/g;
//            var a=editor1.getValue().replace(reg," ");
//            var regsn = /\ [\r\n]/g;
//            var b=a.replace(regsn,"\r\n");
//            var regn = /[\r\n]+/g;
//            var c=a.replace(regn,"\r\n");
//            var s = c.split(";\r\n");
//            var isSingle = true;
//            if(s.length>1 && s[1]!=''){
//                isSingle=false;
//            }

            var datajson = editor1.getValue();
            try{
                var formatResult=editor1.formatJson(datajson);
//                $("#dataExcSql").val(formatResult);
                editor1.setValue(formatResult)
            }catch(e){
                console.log(e);
            }
            try{
                //   $("#dataExcResult").val(formatResult);

                try {
                    var regn = /[\r\n]+/g;
                    datajson=formatResult.replace(regn,"");
//                var c=a.replace(regn,"\r\n");
                    $("#json-collapsed").JSONView(datajson, { nl2br: true, recursive_collapser: true });
                    displayJsonResult();
                    $('#tools_btn').show();
                }catch (e){
                    $("#json-collapsed").JSONView(formatResult, { nl2br: true, recursive_collapser: true });
                }
            }
            catch(e){
                document.getElementById('json_error').innerHTML=e.message;
                var errorLine = e.message.substring(e.message.indexOf("at line ")+8,e.message.indexOf(" column "));
                editor1.setCursor(parseInt(errorLine));
                $('#prdoduction_database').show();
            }
            $("#excSubmit").attr("disabled",false);
        }
        return false;
    });

    function collapserlevel(level) {
        $(level).children('li').children('.collapser').click()
    }

    $('#reset').on("click", function () {
        $("#dataExcSql").val('');
        return false;
    });

    $('#fold_node').on("click", function () {
        $('#json-collapsed').JSONView('collapse', 1);
        return false;
    });

    $('#open_node').on("click", function () {
        $('#json-collapsed').JSONView('expand', 1);
        return false;
    });

</script>
</body>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>freemarker语法测试</title>

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
        .CodeMirror-scroll { max-height: 800px; border-left: 30px;}
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
            <div class="col-xs-12 col-sm-9 col-md-10" style="width: 100%;">
                <div class="form-horizontal" role="form">


                    <div class="form-group dataExcSqlDiv">
                    <#-- <label for="name" class="col-sm-2 control-label">SQL</label> -->
                        <div class="col-sm-12" style="display: inline-flex;">
                            <div style="width: 50%">
                                <label for="name" class="col-sm-1 control-label">html</label>
                                <textarea class="form-control sql-textarea" rows="2" cols="10"
                                          id="dataExcSql"  ></textarea>


                                <script type="text/javascript">
                                    var editor2=CodeMirror.fromTextArea(document.getElementById("dataExcSql"),{
                                        lineNumbers: true,
                                        lineWrapping: true,
                                        matchBrackets: true,
                                        //Js高亮显示
                                        mode:"text/html",
                                        //设置主题
                                        theme:"eclipse",
                                        //快捷键
                                        extraKeys:{
                                            "F2": function autoFormat(editor2) {
                                                $("#excSubmit").click();
                                            }//代码格式化
                                        }
                                    });
                                    editor2.setSize('100%','280px');
                                </script>


                                <label for="name" class="col-sm-1 control-label">JSON</label>
                                <textarea class="form-control sql-textarea" rows="2" cols="10"
                                          id="dataExcSql2"  ></textarea>


                                <script type="text/javascript">
                                    var editor1=CodeMirror.fromTextArea(document.getElementById("dataExcSql2"),{
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
                                    editor1.setSize('100%','280px');
                                </script>
                            </div>
                            <div class="form-group" style="height: 100%;margin: auto;">
                                <div class="col-sm-offset-2  dataExcSqlDiv" >
                                    <div class="ExcSqlBtn">
                                        <button  class="btn btn-default" data-loading-text="Loading..." id="excSubmit">执行</button>
                                        <button  class="btn btn-default" id="reset" style="margin-top: 10px;">清空</button>
                                    </div>
                                </div>
                            </div>
                            <div style="width: 50%">

                                <label for="name" class="">执行结果</label>
                                <br>
                                <textarea class="form-control sql-textarea" rows="2" cols="10"
                                          id="dataExcSql3"  ></textarea>


                                <script type="text/javascript">
                                    var editor3=CodeMirror.fromTextArea(document.getElementById("dataExcSql3"),{
                                        lineNumbers: true,
                                        lineWrapping: true,
                                        matchBrackets: true,
                                        //Js高亮显示
                                        mode:"text/html",
                                        //设置主题
                                        theme:"eclipse"
                                    });
                                    editor3.setSize('100%','280px');
                                </script>
                                <input type="hidden" value="" id="data_json">
                                <div id="json-collapsed"></div>

                                <label for="name" class="" style="margin-top: 8px;">结果预览</label>
                                <div id="pre_view_result" style="width:100%;height: 280px;border: 1px solid #ddd;">
                                    <iframe id="iframeResult" style="width:90%;margin: 30px;overflow-y: scroll;height: 240px" frameborder="0">

                                    </iframe>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

            </div>
        </div>

    </div>
</div>


<script type="text/javascript" defer=true>

    $(document).ready(function () {
        editor2.setValue("<div>\n\$\{a\}\n</div>\n<\#if a==1>\n    <div>dasdadad \$\{a\}</div>\n</\#if>\n\$\{c.d\}\n<\#list f as fl>\n    <li>\$\{fl.g\}</li>\n    <li>\$\{fl.t\}</li>\n</\#list>\n<div>\n<strong style=\"color:red\">日期类型命名_date结尾，且值为毫秒Unix时间戳！</strong>\n<br>\n\$\{d_date?string(\"MM/dd/yyyy\")\}\n</div>")
        editor1.setValue("{\n  \"a\":1,\n  \"d_date\":1516159322000,\n  \"b\":\"1234sd\",\n  \"c\":{\n    \"d\":123,\n    \"e\":\"wqe3\"\n  },\n  \"f\":[{\n    \"g\":123,\n    \"t\":321\n  },\n  {\n    \"g\":1233,\n    \"t\":4321\n  }],\n  \"r\":123\n}");
    });

    $("#excSubmit").on("click", function () {
        console.log(editor1.getValue());
        $('#prdoduction_database').hide();
        $('#tools_btn').hide();
        if (!editor1.getValue()) {
            layer.msg("输入框为空");
            return false;
        } else {
            $("#excSubmit").attr("disabled",true);
            $.ajax({
                type: "POST",
                url: "freemarkerTest",
                data: {
                    "freemarkerDom": editor2.getValue(),
                    "freemarkerData": editor1.getValue()
                },
                success: function (msg) {
                    if (typeof msg !== 'string') {
                        msg=JSON.stringify(msg);
                    }
                    if (msg.indexOf('page-id="databse-login-page"') > -1) {
                        window.location.reload();
                        return false;
                    }
                    $("#excSubmit").attr("disabled", false);
                    var result = JSON.parse(msg);

                    editor3.setValue(result.data);
                    var doc = document.getElementById("iframeResult").contentDocument || document.frames["iframeResult"].document;
                    doc.body.innerHTML = result.data;

                },
                error: function (error) {
                    editor3.setValue(error)
                }
            });
            $("#excSubmit").attr("disabled",false);
        }
        return false;
    });

    $("#reset").on("click", function () {
        editor1.setValue("");
        editor2.setValue("");
        editor3.setValue("");
    });

</script>
</body>
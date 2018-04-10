<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>操作</title>

    <link rel="stylesheet" href="css/bootstrap.min.css" >
    <link rel="stylesheet" href="css/bootstrap-table.min.css">
    <link type="text/css" rel="stylesheet" href="css/operater.css">
    <link rel="stylesheet" href="css/codemirror.css">
    <link rel="stylesheet" href="css/jquery.jsonview.css">
    <link rel="stylesheet" href="css/style.css">
<#-- <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/bootstrap-table
.min.js"></script>-->
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-table.js"></script>
    <script src="js/codemirror.js"></script>
    <script src="js/sql.js"></script>
    <script src="js/matchbrackets.js"></script>
    <script src="js/activebookmark.js"></script>
    <script type="text/javascript" src="js/bootstrap-table-export.js"></script>
    <script type="text/javascript" src="js/FileSaver.min.js"></script>
    <script type="text/javascript" src="js/xlsx.core.min.js"></script>
    <script type="text/javascript" src="js/tableExport.js"></script>
    <script src="js/jquery.jsonview.js"></script>

    <style>
        .CodeMirror { height: auto; border: 1px solid #ddd; }
        .CodeMirror-scroll { max-height: 800px; border-left: 30px;}
        .CodeMirror pre { padding-left: 7px; line-height: 1.25; }
    </style>
</head>
<body>
<div class="data-form" style="margin-top: 18px;">
    <span class="topBar">Hi ${user.account}&nbsp;, 上次登录ip：${user.lastLoginIp} , 上次登录时间：${user
    .lastLoginTime?string("yyyy-MM-dd HH:mm:ss zzzz")} , <#if user.level == 9> <a href="/create_user" >添加用户</a> | </#if><a href="/update_password" >修改密码</a> | <a href="/logout" >退出</a> | <a href="/history" >历史记录</a> | <a href="http://wiki.d.xiaonei.com/pages/viewpage.action?pageId=37043965" target="_blank">VPN使用wiki说明</a> </span>
</div>
<HR>
<div id="wrapper">
    <div class="overlay"></div>
    <!-- Sidebar -->
    <nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
        <#include "operate_favorite_sql.ftl" >
    </nav>
    <!-- /#sidebar-wrapper -->
    <button type="button" class="hamburger is-closed animated fadeInLeft" data-toggle="offcanvas">
        <span class="hamb-top"></span>
        <span class="hamb-middle"></span>
        <span class="hamb-bottom"></span>
    </button>
</div>
<div class="row">
    <div style="height: 300px">
        <div class="col-xs-8">
            <div class="form-horizontal" role="form">
                <div class="form-group" style="margin-left: 20px;">
                    <label for="name" class="col-sm-2 control-label">选择环境</label>

                    <div class="col-sm-4" >
                    <#-- <select class="form-control" id="databaseEnv"
                              onchange="changeEnvDatabase()">-->
                        <div class="databaseEnv">
                        <#list databaseEnvList as databaseEnv>
                        <#--  <option value="${databaseEnv}">${databaseEnv}</option>-->
                            <lable><input id="${databaseEnv}" type="radio" name="env"
                                          value="${databaseEnv}"
                                          onchange="changeEnvDatabase (this)"/><label
                                    for="${databaseEnv}">${databaseEnv}</label></lable>
                        </#list>
                        </div>

                    </div>
                    <label for="name" class="col-sm-2 control-label">选择数据库</label>

                    <div class="col-sm-4 col-md-3">

                        <select class="form-control" id="dataSchema">
                        <#list schemaList as schema>
                            <option value="${schema}">${schema}</option>
                        </#list>
                        </select>
                    </div>
                </div>

                <div class="form-group dataExcSqlDiv">
                <#-- <label for="name" class="col-sm-2 control-label">SQL</label> -->
                    <div class="col-xs-10" style="left: 120px">
                            <textarea class="form-control sql-textarea" rows="2" cols="10"
                                      id="dataExcSql"  ></textarea>

                        <script type="text/javascript" defer=true>
                            var editor1 = CodeMirror.fromTextArea(document.getElementById("dataExcSql"), {
                                lineNumbers: true,
                                lineWrapping: true,
                                mode: "text/x-mysql",
                                matchBrackets: true
                            });
                            editor1.setSize('100%','180px');
                        </script>


                    </div>


                </div>

                <div class="form-group editSqlDetail" style="display: none">
                    <label for="name" class="col-sm-2 control-label">SQL</label>
                    <div class="col-xs-9">
                        <textarea class="form-control sql-textarea" rows="2" cols="10" id="editSql"></textarea>

                        <script type="text/javascript" defer=true>
                            var editor2 = CodeMirror.fromTextArea(document.getElementById("editSql"), {
                                // lineNumbers: true,
                                lineWrapping: true,
                                mode: "text/x-mysql",
                                matchBrackets: true
                            });
                            editor2.setSize('100%','180px');
                        </script>

                    </div>
                </div>

                <div class="form-group editSqlDetail" style="display: none">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-7">
                        <input class="form-control" rows="5" id="editSqlName"></input>
                        <input class="form-control" rows="5" id="editSqlId" type="hidden"/>
                    </div>
                </div>

                <#include "/web/tips/not_empty.ftl">

                <#include "../tips/switch_prd.ftl">

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10 dataExcSqlDiv" style="margin-left: 120px !important;">
                        <div class="ExcSqlBtn">
                            <button  class="btn btn-default" data-loading-text="Loading..." id="excSubmit">执行</button>
                            <button  class="btn btn-default" id="excExplainSubmit">执行计划</button>
                            <button  class="btn btn-default" id="favoriteSubmit">收藏该SQL</button>
                        <#--<button  class="btn btn-default" data-toggle="modal" data-target="#myModal">-->
                        <#--收藏该SQL-->
                        <#--</button>-->

                            <input type="file" name="upload" id="upload" accept="text/plain" style="margin-top: 10px;"/>
                            <script type="text/javascript">
                                window.onload = function() {
                                    /**
                                     * 上传函数
                                     * @param fileInput DOM对象
                                     * @param callback 回调函数
                                     */
                                    var getFileContent = function (fileInput, callback) {
                                        if (fileInput.files && fileInput.files.length > 0 && fileInput.files[0].size > 0) {
                                            //下面这一句相当于JQuery的：var file =$("#upload").prop('files')[0];
                                            var file = fileInput.files[0];
                                            if (window.FileReader) {
                                                var reader = new FileReader();
                                                reader.onloadend = function (evt) {
                                                    if (evt.target.readyState == FileReader.DONE) {
                                                        callback(evt.target.result);
                                                    }
                                                };
                                                // 包含中文内容用gbk编码
                                                reader.readAsText(file, 'gbk');
                                            }
                                        }
                                    };

                                    /**
                                     * upload内容变化时载入内容
                                     */
                                    document.getElementById('upload').onchange = function () {
                                        getFileContent(this, function (str) {
                                            editor1.setValue(str);
                                        });
                                    };
                                };
                            </script>
                        </div>
                    </div>
                    <div class="col-sm-offset-2 col-sm-10 editSqlDetail" style="display: none">
                        <button  class="btn btn-default" id="excEditSubmit">保存</button>
                        <button  class="btn btn-default" id="excEditCancel">取消</button>
                    </div>
                </div>
            </div>

            <#include "operate_desc.ftl">

            <form id="sqlExcResultJson" class="form-horizontal" role="form" style="display:none"
                  enctype="application/x-www-form-urlencoded" >
                <div class="form-group">
                    <div style="margin-left: 137px" id="excCountJson"></div>
                    <label for="name" class="col-sm-2 control-label">执行结果</label>
                    <div class="col-sm-7">
                    <#--   <textarea class="form-control data-result" rows="5"
                                  id="dataExcResult"></textarea>-->
                        <input type="hidden" value="" id="data_json">
                        <div id="json-collapsed"></div>
                    </div>

                </div>

            </form>

            <form id="sqlExcResultText" class="form-horizontal" role="form" style="display:none"
                  enctype="application/x-www-form-urlencoded" >
                <div class="form-group">
                    <div style="margin-left: 137px" id="excCount"></div>
                    <label for="name" class="col-sm-2 control-label">执行结果</label>
                    <div class="col-sm-7">
                        <p>该SQL返回Json较为复杂,以text展示</p>
                        <textarea class="form-control" rows="2" cols="20"
                                  id="dataExcResult"></textarea>
                        <script type="text/javascript" defer=true>
                            var editor3 = CodeMirror.fromTextArea(document.getElementById("dataExcResult"), {
//                                lineNumbers: true,
                                lineWrapping: true,
                                mode: "text/x-mysql",
                                matchBrackets: true
                            });
                            editor3.setSize('100%','480');
                        </script>
                        <input type="hidden" value="" id="data_json">
                    </div>

                </div>

            </form>
        </div>
        <div class="span2  col-xs-6 col-sm-1 col-md-1" style="margin-left: 100px;width: 200px;">
            <div style="text-align: center;margin-bottom: 20px">小工具</div>
            <ul class="nav nav-pills nav-stacked" style="text-align: center;
                height: 270px;overflow: auto;">
                <li class=""><a href="/domainInfo" target="_blank">域名信息查询</a></li>
                <li class=""><a href="/jsonFormart" target="_blank">json格式化</a></li>
                <li class=""><a href="/regular" target="_blank">正则表达式测试工具</a></li>
                <li class=""><a href="/freemarker" target="_blank">freemarker解析语法测试工具</a></li>
            </ul>
        </div>
    </div>
    <div style="width:90%;margin: 0 auto;" >
        <form id="loading" class="form-horizontal" role="form" style="display:none" >
            <div class="form-group">
                <div class="loading">玩儿命加载中...</div>
            </div>

        </form>

        <form id="table-result-div" role="form" onsubmit="return false;">

            <div class="form-group" id="result-table" >

                <table id="tb_departments" ></table>

            </div>

        </form>



    </div>
</div>
<script src="js/operate/operate.js"></script>

</body>
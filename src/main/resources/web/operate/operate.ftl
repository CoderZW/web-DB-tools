<#include "/macros/page_macro.ftl">
<@webpage pageCss="css/operater.css" title="操作">
    <#include "operate_header.ftl">
<div class="data-form" style="margin-top: 18px;">
    <span class="topBar">Hi ${user.account}&nbsp;, 上次登录ip：${user.lastLoginIp} , 上次登录时间：${user
    .lastLoginTime?string("yyyy-MM-dd HH:mm:ss zzzz")} , <#if user.level == 9>  <a
            href="/userManager" >用户管理 </a> | </#if><a href="/update_password" >设置中心</a> | <a
            href="/logout" >退出</a> | <a href="/history" target="_blank" >历史记录</a> | <a
            href="http://wiki.d.xiaonei.com/pages/viewpage.action?pageId=37043965" target="_blank">VPN使用wiki说明</a>
    </span>
</div>
    <#if user.updatePasswordinterval?exists &&  (60-user.updatePasswordinterval) &lt; 10>
    <div class="tipRowup">
        <span class="rowup">
            您的密码还有<span style="color: red;">${60-user.updatePasswordinterval}天到期</span>,请尽快<a href="/update_password"
                                                                                          style="color: #01AAED">修改密码</a>,否则无法继续使用该平台工具!
        </span>
    </div>
    </#if>
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

                        <select class="form-control" id="dataSchema" onchange="changeSchema()">
                            <#list schemaList as schema>
                                <option value="${schema}">${schema}</option>
                            </#list>
                        </select>
                    </div>

                    <div>
                        <input id="defaultSchema" type="hidden" value='${user.defaultSchema}' />
                    </div>

                </div>

                <div class="form-group dataExcSqlDiv">
                <#-- <label for="name" class="col-sm-2 control-label">SQL</label> -->
                    <div class="col-xs-10" style="left: 120px">
                            <textarea class="form-control sql-textarea" rows="2" cols="10"
                                      id="dataExcSql"  ></textarea>
                        <script type="text/javascript" defer=true>
                            var editor1;
                            $.ajax({
                                type: "GET",
                                url: "getTables",
                                data: {
                                    "env": $("input[name='env']:checked").val(),
                                    "schema": $("#dataSchema").val()
                                },
                                success: function (msg) {
                                    var result = JSON.parse(msg);
                                    editor1 = CodeMirror.fromTextArea(document.getElementById("dataExcSql"), {
                                        lineNumbers: true,
                                        lineWrapping: true,
                                        mode: "text/x-mysql",
                                        matchBrackets: true,
                                        styleActiveLine: true,
                                        smartIndent: true,
                                        hintOptions: {
                                            tables: result.data
                                        }
                                    });
                                    editor1.setSize('100%','180px');
                                    editor1.on("keyup", function (cm, event) {
                                        //所有的字母和'$','{','.'在键按下之后都将触发自动完成
                                        if (!cm.state.completionActive &&
                                                ((event.keyCode >= 65 && event.keyCode <= 90 ) || event.keyCode == 52 || event.keyCode == 219 || event.keyCode == 190)) {
                                            CodeMirror.commands.autocomplete(cm, null, {completeSingle: false});
                                        }
                                    });
                                    var url = location.search;
                                    if (url.indexOf("?") != -1) {
                                        $.ajax({
                                                   type: "GET",
                                                   url: "getSqlDetail",
                                                   data:{
                                                       "sqlId": url.substr(3)
                                                   },
                                                   success: function (msg) {
                                                       var result = JSON.parse(msg);
                                                       editor1.setValue(result.data);
                                                   },
                                                   error:function (error) {
                                                       layer.msg("server error");
                                                   }

                                               })

                                    }
                                },
                                error: function (error) {
                                    layer.msg("server error");
                                }
                            })

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
                                matchBrackets: true,
                                styleActiveLine: true,
                                smartIndent: true
                            });
                            editor2.setSize('100%','180px');
                            editor2.on("keyup", function (cm, event) {
                                //所有的字母和'$','{','.'在键按下之后都将触发自动完成
                                if (!cm.state.completionActive &&
                                        ((event.keyCode >= 65 && event.keyCode <= 90 ) || event.keyCode == 52 || event.keyCode == 219 || event.keyCode == 190)) {
                                    CodeMirror.commands.autocomplete(cm, null, {completeSingle: false});
                                }
                            });
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

                <#include "/tips/not_empty.ftl">

                <#include "/tips/switch_prd.ftl">

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10 dataExcSqlDiv" style="margin-left: 120px !important;">
                        <div class="ExcSqlBtn">
                            <button  class="btn btn-default" data-loading-text="Loading..." id="excSubmit">执行</button>
                            <button  class="btn btn-default" id="excExplainSubmit">执行计划</button>
                            <button  class="btn btn-default" id="favoriteSubmit">收藏该SQL</button>
                            <input type="file" name="upload" id="upload" accept="text/plain, .sql"
                                   style="margin-top: 10px;"/>
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
                <li class=""><a href="/upload" target="_blank">上传文件至服务器</a></li>
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

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    网络问题
                </h4>
            </div>
            <div class="modal-body">
                <p>当前所处外网环境，无法访问该服务！您可以尝试：</p>
                <p>1、内网环境，关掉翻墙！</p>
                <p>2、外网环境，链接VPN绑定当前域名host：10.4.36.107！</p>
                <p><a href="http://wiki.d.xiaonei.com/pages/viewpage.action?pageId=37043965" target="_blank">VPN使用wiki说明</a></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="myModalName" tabindex="-1" role="dialog" aria-labelledby="myModalNameLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalNameLabel">
                    请输入收藏名
                </h4>
            </div>
            <div class="modal-body">
                <input class="form-control" rows="5" id="addSqlName"></input>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="submitFavoriteSql">
                    提交
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="myModalUserName" tabindex="-1" role="dialog"
     aria-labelledby="myModalUserNameLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalUserNameLabel">
                    请输入分享人用户名
                </h4>
            </div>
            <div class="modal-body">
                <input class="form-control" rows="5" id="ShareUserName"></input>
                <ul class="list-box"></ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
                <button type="button" class="btn btn-primary" id="shareUserFavoriteSql">
                    确认
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
    <#include "operate_footer.ftl">
</@webpage>

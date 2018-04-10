<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户管理</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-editable.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/bootstrap-table.min.css">
    <link type="text/css" rel="stylesheet" href="css/operater.css">
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-editable.js"></script>
    <script type="text/javascript" src="js/bootstrap-table.js"></script>
    <script type="text/javascript" src="js/bootstrap-table-editable.js"></script>
    <script type="text/javascript" src="js/layer.js"></script>
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
    <input id="env" type="hidden" value='${env}' />
</div>

<div class="data-form">
    <span class="topBar">Hi ${user.account}&nbsp;, 上次登录ip：${user.lastLoginIp} , 上次登录时间：${user.lastLoginTime?string("yyyy-MM-dd HH:mm:ss zzzz")} ,  <a href="/operate"> 返回</a> </span>
</div>

<div class="panel-body">
    <form id="formSearch" class="form-horizontal">
        <div class="form-group" style="margin-top:10px">
            <label class="control-label col-sm-1" for="search_username">查询用户名</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="search_username">
            </div>
        </div>
    </form>
</div>

<div id="toolbar" class="btn-group">
    <button id="btn_add" type="button" class="btn btn-default">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
    </button>
<#--<button id="btn_edit" type="button" class="btn btn-default">-->
<#--<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改-->
<#--</button>-->
    <button id="btn_delete" type="button" class="btn btn-default">
        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
    </button>
</div>

<form id="table-result-div" role="form" onsubmit="return false;">
    <div class="form-group" id="result-table">
        <table id="tb_departments" ></table>
    </div>

</form>

<div class="modal fade" id="myModalCreateUser" tabindex="-1" role="dialog"
     aria-labelledby="myModalCreateUserLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalCreateUserLabel">
                    新建用户
                </h4>
            </div>
            <div class="modal-body-user">
                <div class="form-group">
                    <label for="username" class="col-sm-5 control-label">账号：</label>
                    <div class="col-sm-5">
                        <input name="username" id="username" class="col-sm-10"/>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                    <label for="password" class="col-sm-5  control-label">密码：</label>
                    <div class="col-sm-5">
                        <input name="password" type="password" id="password" class="col-sm-10"/><br>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                    <label for="password" class="col-sm-5  control-label">确认密码：</label>
                    <div class="col-sm-5">
                        <input name="confirmPassword" type="password" id="confirm_password"
                               class="col-sm-10" /><br>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                <#list databaseEnvList as databaseEnv>
                    <lable for="level" class="col-sm-5 control-label">${databaseEnv}权限级别：</lable>
                    <div class="col-sm-5">
                        <select class="col-sm-10" id="${databaseEnv}">
                            <option value="0">只读</option>
                            <option value="1">DDL读写</option>
                            <option value="2">DML表结构变更</option>
                        </select>
                    </div>
                </#list>
                </div>
                <br/>
                <div class="from-group">
                    <label for="name" class="col-sm-5 control-label">选择默认数据库</label>
                    <div class="col-sm-5">
                        <select class="col-sm-10" id="defaultSchema" >
                        <#list schemaList as schema>
                            <option value="${schema}">${schema}</option>
                        </#list>
                        </select>
                    </div>
                </div>
                <br/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
                <button type="button" class="btn btn-primary" id="createUserSubmit">确认
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript" defer=true>

    function getColumns(){
        var columns =  [
            {
                checkbox: true
            },
            {
                field: 'account',
                title: '用户名',
                editable: {
                    type: 'text',
                    title: '用户名',
                    validate: function (v) {
                        if (!v) return '用户名不能为空';
                    }
                }
            }];
        var envStr=$("#env").val();
        if(envStr == "test") {
            columns.push({field: 'level',
                             title: '测试库权限',
                             editable: {
                                 type: 'select',
                                 title: '测试库权限',
                                 source: [{ value: "0", text:
                                         "只读" }, { value: "1",
                                     text: "DDL读写" }, { value: "2",
                                     text: "DML表结构变更" },
                                     {
                                         value:"9",
                                         text: "高级管理员"
                                     }]
                             }});
        } else {
            columns.push({
                             field: 'level',
                             title: '预发布权限',
                             editable: {
                                 type: 'select',
                                 title: '预发布权限',
                                 source: [{ value: "0", text:
                                         "只读" }, { value: "1",
                                     text: "DDL读写" }, { value: "2",
                                     text: "DML表结构变更" },
                                     {
                                         value:"9",
                                         text: "高级管理员"
                                     }]
                             }
                         },
                         {
                             field: 'online_level',
                             title: '线上权限',
                             editable: {
                                 type: 'select',
                                 title: '权限',
                                 source: [{ value: "0", text:
                                         "只读" }, { value: "1",
                                     text: "DDL读写" }, { value: "2",
                                     text: "DML表结构变更" },
                                     {
                                         value:"9",
                                         text: "高级管理员"
                                     }]
                             }
                         })

        }
        columns.push( {
                          field: 'default_schema',
                          title: '默认schema',
                          editable: {
                              type: 'select',
                              title: '默认schema',
                              source: [
                                  {
                                      value: "sitebuilt",
                                      text: "sitebuilt"
                                  },
                                  {
                                      value: "sitebuilt_preview",
                                      text: "sitebuilt_preview"
                                  },
                                  {
                                      value: "chime",
                                      text: "chime"
                                  },
                                  {
                                      value: "listing",
                                      text: "listing"
                                  }]
                          }
                      },
                      {
                          field: 'status',
                          title: '用户状态',
                          editable: {
                              type: 'select',
                              title: '用户状态',
                              source: [
                                  {
                                      value: "0",
                                      text: "禁用"
                                  },
                                  {
                                      value: "1",
                                      text: "启用"
                                  }]
                          }
                      });
        return columns;

    }


    $(function () {
        var oTable = new TableInit();
        oTable.Init();
    });

    var TableInit = function () {
        var oTableInit = new Object();
        oTableInit.Init = function () {
            $('#tb_departments').bootstrapTable({
                                                    url: "/getUserInfo", //请求后台的URL（*）
                                                    method: 'get', //请求方式（*）
                                                    toolbar: '#toolbar', //工具按钮用哪个容器
                                                    striped: true, //是否显示行间隔色
                                                    cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                                                    pagination: true, //是否显示分页（*）
                                                    sortable: false, //是否启用排序
                                                    sortOrder: "asc", //排序方式
                                                    queryParams: oTableInit.queryParams,//传递参数（*）
                                                    sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
                                                    dataField: "rows",
                                                    responseHandler: responseHandler,
                                                    pageNumber: 1, //初始化加载第一页，默认第一页
                                                    pageSize: 10, //每页的记录行数（*）
                                                    pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                                                    search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                                                    strictSearch: true,
                                                    showColumns: true, //是否显示所有的列
                                                    showRefresh: true, //是否显示刷新按钮
                                                    minimumCountColumns: 2, //最少允许的列数
                                                    clickToSelect: true, //是否启用点击选中行
                                                    uniqueId: "id", //每一行的唯一标识，一般为主键列
                                                    showToggle: true, //是否显示详细视图和列表视图的切换按钮
                                                    cardView: false, //是否显示详细视图
                                                    detailView: false, //是否显示父子表
                                                    columns: getColumns(),
                                                    onEditableSave: function (field, row, oldValue, $el) {
                                                        $.ajax({
                                                                   type: "get",
                                                                   url: "/update/UserInfo",
                                                                   data: row,
                                                                   dataType: 'JSON',
                                                                   success: function (data, status) {
                                                                       if (status == "success") {
                                                                           layer.msg('更新用户成功');
                                                                       }
                                                                   },
                                                                   error: function () {
                                                                       layer.msg('编辑失败');
                                                                   },
                                                                   complete: function () {

                                                                   }

                                                               });
                                                    }

                                                });
        };

        oTableInit.queryParams = function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
                limit: params.limit, //页面大小
                offset: params.offset, //页码
            };
            return temp;
        };
        return oTableInit;
    };

    function responseHandler(result){
        //如果没有错误则返回数据，渲染表格
        return {
            total : result.total, //总页数,前面的key必须为"total"
            rows : result.rows //行数据，前面的key要与之前设置的dataField的值一致.
        };
    };

    function refresh() {
        $('#tb_departments').bootstrapTable('refresh', {url: "getUserInfo"});
    }


    $("#btn_edit").click(function () {
        var i = 0;
        var id;
        $("input[name='btSelectItem']:checked").each(function () {
            i++;
            id = $(this).parents("tr").attr("data-uniqueid");
        })
        if (i > 1) {
            alert("编辑只支持单一操作")
        } else {
            if (i != 0) {
                alert("获取选中的id为" + id);
            } else {
                alert("请选中要编辑的数据");
            }

        }

    });

    $("#btn_delete").click(function () {
            var idList = "";
            $("input[name='btSelectItem']:checked").each(function () {
               idList += $(this).parents("tr").attr("data-uniqueid") + ",";
            });
            console.log(idList);
            if(idList.length <= 0) {
                alert("请选中要删除的数据");
            } else if (confirm("确认要删除吗？")) {
                $.ajax({
                           type: "GET",
                           url: "deleteUser",
                           data: {
                               "idList" : idList
                           },
                           success:function (msg) {
                               if(msg.indexOf('page-id="databse-login-page"')>-1){
                                   window.location.reload();
                                   return false;
                               }
                               var result = eval('(' + msg + ')');
                               if (msg.indexOf('执行成功')>-1) {
                                   layer.msg("删除成功！")
                                   $('#tb_departments').bootstrapTable('refresh', {url: "getUserInfo", query: {key: $
                                   ("#search_username").val()}});
                               } else {
                                   layer.msg(result.data[0].error);
                               }
                           },
                           error:function () {
                               layer.msg("删除失败！")
                           }
                       })
            }
    });

    $("#btn_add").click(function () {
//        window.location.href = "/create_user";
        $('#myModalCreateUser').modal('show');
    });

    $('#search_username').bind('input propertychange', function() {
        $('#tb_departments').bootstrapTable('refresh', {url: "getUserInfo", query: {key: $
        ("#search_username").val()}});
    });

    $("#createUserSubmit").on("click", function () {
        var level;
        var onlineLevel;
        if ($("#prd").length > 0) {
            onlineLevel = $("#prd").val();
            level = $("#stage").val();
        } else {
            level = $("#test").val();
        }
        if (!$("#username").val() || !$("#password").val() || !$("#confirm_password").val()) {
            layer.msg("输入框不能为空");
            return false;
        } else {
            var password = $("#password").val();
            var confirmPassword = $("#confirm_password").val();
            if($("#username").val().length<4 || $("#username").val().length>20){
                layer.msg("用户名必须大于等于4位,小于等于20位！");
            }else if(password.length<6 || confirmPassword.length<6 || password.length>30 || confirmPassword.length >30){
                layer.msg("密码长度必须大于等于6位,小于等于30位！");
            }else   if(password !== confirmPassword){
                layer.msg("密码和确认密码不一致！");
            }else{
                $.ajax({
                           type: "POST",
                           url: "createUserSubmit",
                           data: {
                               "account": $("#username").val(),
                               "password": $("#password").val(),
                               "confirmPassword": $("#confirm_password").val(),
                               "level": level,
                               "onlineLevel": onlineLevel,
                               "defaultSchema": $("#defaultSchema").val()
                           },
                           success: function (msg) {
                               if(msg.indexOf('page-id="databse-login-page"')>-1){
                                   window.location.reload();
                                   return false;
                               }
                               var result = eval('(' + msg + ')');
                               if (msg.indexOf('执行成功')>-1) {
                                   layer.msg("创建成功！")
                                   $('#myModalCreateUser').modal('hide');
                               } else {
                                   layer.msg(result.data[0].error);
                               }
                           },
                           error: function () {
                               layer.msg("server error");
                           }
                       })
            }
        }
        return false;
    });

</script>

</body>
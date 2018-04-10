<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/html">
<head>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/layer.js"></script>
    <title>人人DB工具</title>
    <link type="text/css" rel="stylesheet"
          href="css/operater.css">
</head>

<body>
<header>

    <section class="header-container">
        <div class="logo">

            <embed src="https://static.chimeroi.com/home/images/logo/logo2.svg" onload="this.style.opacity = '1'"
                   style="opacity: 1;">
                <img class="blackChime" style="display: none" src="//static.chimeroi.com/crm/images/logo/chime2.svg"
                     alt="">
                <a href="" class="logo"></a>
        </div>

    </section>
</header>
<div class="data-form">
    <span class="topBar">Hi ${user.account}&nbsp;, 上次登录ip：${user.lastLoginIp} , 上次登录时间：${user.lastLoginTime?string("yyyy-MM-dd HH:mm:ss zzzz")} , <a
            href="/logout">退出</a></span>
</div>

<div>
    <input id="defaultSchemaStr" type="hidden" value='${user.defaultSchema}' />
</div>

<div class="data-form container-fluid">
    <div class="row">
        <div class="col-xs-12 col-sm-9 col-md-10" style="width: 100%">
            <div class="form-horizontal" role="form">
                <div class="form-group">
                    <label for="username" class="col-sm-5 control-label">账号：</label>
                    <div class="col-sm-5">
                        <input name="username" id="username" class="col-sm-4" value="${user.account}" disabled/>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                    <label for="password" class="col-sm-5 control-label">原始密码：</label>
                    <div class="col-sm-5">
                        <input name="password" type="password" id="password" class="col-sm-4"/><br>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                    <label for="password" class="col-sm-5 control-label">新密码：</label>
                    <div class="col-sm-5">
                        <input name="newPassword" type="password" id="new_password" class="col-sm-4"
                               placeholder="天王盖地虎"/><br>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                    <label for="password" class="col-sm-5 control-label">确认密码：</label>
                    <div class="col-sm-5">
                        <input name="confirmPassword" type="password" id="confirm_password" class="col-sm-4"
                               placeholder="宝塔镇河妖"/><br>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                    <label for="password" class="col-sm-5 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <button id="edit-password-submit" type="submit" class="btn btn-default">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="btn btn-default" name="" onclick="javascript:history.back(-1);">返回上一页</button>
                </div>
                <div class="form-group" style="text-align: center;">如您忘记原始密码，请发送email至：<a>feifei.lei@renren-inc.com</a>
                    或QQ：<a>463367527</a> 帮您重置密码！
                </div>
                <#if isInitPw == true>

                    <div class="form-group" style="font-size: 20px;color: red;text-align: center;">您的密码为初始密码，请修改后使用!</div>
                </#if>
            <#if user.updatePasswordinterval?exists && user.updatePasswordinterval &gt; 60>
                <div class="form-group" style="font-size: 20px;color: red;text-align: center;">
                    您的密码已到期,请修改密码,否则无法继续使用该平台工具!
                </div>
            </#if>
                <div class="form-group">
                    <label for="name" class="col-sm-5 control-label">修改默认数据库</label>
                    <div class="col-sm-5">
                        <select class="col-sm-4" id="defaultSchema" onchange="changeSchema()">
                        <#list schemaList as schema>
                            <option value="${schema}">${schema}</option>
                        </#list>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div hidden="true" page-id="databse-login-page"></div>
<script type="text/ecmascript" src="js/md5.js"></script>
<script type="text/javascript" src="//static.chimeroi.com/lib/jquery-1.11.3.min.js"></script>
<script type="text/javascript">

    window.onload = function(){
        var defaultSchemaStr=$("#defaultSchemaStr").val();
        document.getElementById('defaultSchema').value= defaultSchemaStr;
    };


    $("#edit-password-submit").on("click", function () {
        if (!$("#password").val() || !$("#new_password").val() || !$("#confirm_password").val()) {
            layer.msg("输入框不能为空");
            return false;
        } else {
            var newPassword = $("#new_password").val();
            var confirmPassword = $("#confirm_password").val();
            if (newPassword.length < 6 || confirmPassword.length < 6 || newPassword.length > 30 || confirmPassword.length > 30) {
                layer.msg("密码长度必须大于等于6位,小于等于30");
            } else if (newPassword !== confirmPassword) {
                layer.msg("新密码和确认密码不一致！");
            } else if (newPassword == $("#password").val()) {
                layer.msg("新密码不能和原始密码相同！");
            } else if (!CheckPassWord(newPassword)) {
                layer.msg("密码必须包含字母和数字！");
            } else {
                $.ajax({
                    type: "POST",
                    url: "update_password_submit",
                    data: {
                        "password": $("#password").val(),
                        "newPassword": $("#new_password").val(),
                        "confirmPassword": $("#confirm_password").val()
                    },
                    success: function (msg) {
                        if (msg.indexOf('page-id="databse-login-page"') > -1) {
                            window.location.reload();
                            return false;
                        }
                        var result = eval('(' + msg + ')');
                        if (msg.indexOf('执行成功，影响行数：1') > -1) {
                            layer.confirm('密码修改成功！\n点击确定重新登录!', {
                                btn: ['确定'] //按钮
                            }, function () {
                                window.location.href = "logout";
                            });
                        } else {
                            layer.msg(JSON.stringify(result.data));
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

    function CheckPassWord(password) {//密码必须包含数字和字母
        var reg = new RegExp(/^(?![^a-zA-Z]+$)(?!\D+$)/);
        if (reg.test(password)) {
            return true;
        } else {
            return false;
        }
    }

    function changeSchema() {
        $.ajax({
                   type: "get",
                   url: "update/defaultSchema",
                   data: {
                       "defaultSchema": $("#defaultSchema").val()
                   },
                   success: function (msg) {
                       if (msg.indexOf('page-id="databse-login-page"') > -1) {
                           window.location.reload();
                           return false;
                       }
                       var result = eval('(' + msg + ')');
                       if (msg.indexOf('执行成功，影响行数：1') > -1) {
                           layer.msg("更改默认schema成功");
                       } else {
                           layer.msg(JSON.stringify(result.data));
                       }
                   },
                   error:function () {
                       layer.msg("server error");
                   }

            })
    }
</script>

</body>


</html>

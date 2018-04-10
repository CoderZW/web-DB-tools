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

            <embed src="https://static.chimeroi.com/home/images/logo/logo2.svg" onload="this.style.opacity = '1'" style="opacity: 1;">
                <img class="blackChime" style="display: none" src="//static.chimeroi.com/crm/images/logo/chime2.svg" alt="">
                <a href="" class="logo"></a>
        </div>

    </section>
</header>
<div class="data-form">
    <span class="topBar">Hi ${user.account}&nbsp;, 上次登录ip：${user.lastLoginIp} , 上次登录时间：${user.lastLoginTime?string("yyyy-MM-dd HH:mm:ss zzzz")} , <a href="/logout" >退出</a></span>
</div>
<div class="data-form container-fluid">
    <div class="row">
        <div class="col-xs-12 col-sm-9 col-md-10" style="width: 100%">
            <div class="form-horizontal" role="form" >
                <div class="form-group">
                    <label for="username" class="col-sm-5 control-label">账号：</label>
                    <div class="col-sm-5">
                        <input name="username" id="username" class="col-sm-4"/>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                    <label for="password" class="col-sm-5  control-label">密码：</label>
                    <div class="col-sm-5">
                        <input name="password" type="password" id="password" class="col-sm-4"/><br>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                    <label for="password" class="col-sm-5  control-label">确认密码：</label>
                    <div class="col-sm-5">
                        <input name="confirmPassword" type="password" id="confirm_password" class="col-sm-4" /><br>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                <#list databaseEnvList as databaseEnv>
                    <lable for="level" class="col-sm-5 control-label">${databaseEnv}权限级别：</lable>
                    <div class="col-sm-5">
                        <select class="col-sm-4" id="${databaseEnv}">
                            <option value="0">只读</option>
                            <option value="1">数据读写</option>
                            <option value="2">DML表结构变更</option>
                        </select>
                    </div>
                </#list>
                </div>
                <br/>
                <div class="form-group">
                    <label for="password" class="col-sm-5 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <button id="edit-password-submit" type="submit" class="btn btn-default">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="btn btn-default" name="" onclick="javascript:history.back(-1);" >返回上一页</button>
                </div>

            </div>
        </div>
    </div>
</div>
<div hidden="true" page-id="databse-login-page"></div>
<script type="text/ecmascript" src="js/md5.js"></script>
<script type="text/javascript" src="//static.chimeroi.com/lib/jquery-1.11.3.min.js"></script>
<script type="text/javascript">

    $("#edit-password-submit").on("click", function () {
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
                        "onlineLevel": onlineLevel
                    },
                    success: function (msg) {
                        if(msg.indexOf('page-id="databse-login-page"')>-1){
                            window.location.reload();
                            return false;
                        }
                        var result = eval('(' + msg + ')');
                        if (msg.indexOf('执行成功')>-1) {
                            layer.msg("创建成功！")
                            window.location.href = "operate";
                        } else {
                            layer.msg(SON.stringify(result.data));
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


</html>

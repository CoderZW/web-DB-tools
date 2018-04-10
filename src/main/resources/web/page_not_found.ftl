<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/html">
<head>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>人人DB工具</title>
    <link type="text/css" rel="stylesheet"
          href="css/operater.css">
    <link type="text/css" rel="stylesheet"
          href="css/notFound.css">
</head>

<body style="height: 100%;padding: 0;text-align: center">
<header>

    <section class="header-container">
        <div class="logo">

            <embed src="https://static.chimeroi.com/home/images/logo/logo2.svg" onload="this.style.opacity = '1'" style="opacity: 1;">
                <img class="blackChime" style="display: none" src="//static.chimeroi.com/crm/images/logo/chime2.svg" alt="">
                <a href="" class="logo"></a>
        </div>

    </section>
</header>
<#if user?exists>
<div class="data-form" style="margin-top: 75px;">
    <span class="topBar">Hi ${user.account}&nbsp;, 上次登录ip：${user.lastLoginIp} , 上次登录时间：${user.lastLoginTime?string("yyyy-MM-dd HH:mm:ss zzzz")} , <#if user.level == 9> <a href="/create_user" >添加用户</a> | </#if><a href="/update_password" >修改密码</a> | <a href="/logout" >退出</a> | <a href="/history" >历史记录</a></span>
</div>
</#if>
<HR>
<div class="container-fluid" <#if !(user?exists)>style="margin-top: 75px;"</#if>>
    <div class="row">
        <div class="col-xs-12 col-sm-9 col-md-10" style="width: 100%">
            <div class="form-horizontal" role="form" >
                <div class="form-group">
                    <div style="margin: 0 auto;text-align: center;margin-top: 80px;">
                        <img src="//static.chimeroi.com/site/images/template/404-text.png">
                        <p>Oops, the page you're looking for cannot be found.</p>
                        <a class="notfound-1" href="/">Back to Home</a>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<div hidden="true" page-id="databse-login-page"></div>
<script type="text/ecmascript" src="js/md5.js"></script>
<script type="text/javascript" src="//static.chimeroi.com/lib/jquery-1.11.3.min.js"></script>

</body>


</html>

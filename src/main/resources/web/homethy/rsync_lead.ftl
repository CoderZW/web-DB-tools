<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/html">
<head>
    <link rel="stylesheet" href="css/style.css" media="all" />
    <link rel="stylesheet" href="js/layui/css/layui.css" media="all" />
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="js/layer.js"></script>
    <title>lead rsync from gf</title>
</head>

<body >
<!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<div class="layui-container">
    <#if env?exists && env == 'prd'>
        <blockquote class="layui-elem-quote layui-text" style="color:red;">
            当前为线上环境，请谨慎操作！
        </blockquote>
    </#if>

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>GF Build</legend>
    </fieldset>
    <form class="layui-form" action="">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 130px;">Chime Crm Account</label>
                <div class="layui-input-inline">
                    <input type="text" name="crmAccount" lay-verify="required" autocomplete="off" class="layui-input" id="crmAccount">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 130px;">GF Domain</label>
            <div class="layui-input-inline">
                <input type="text" name="gfdomain" lay-verify="pass" autocomplete="off" class="layui-input" id="gfdomain">
            </div>
            <div class="layui-form-mid layui-word-aux">请填写gf站的域名，不包含http前缀以及uri、/等</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 130px;"></label>
            <button class="layui-btn layui-btn-normal" id="buildsite-submit">建站</button>
            <button class="layui-btn layui-btn-normal" id="rsync-submit">同步full lead</button>
            <button class="layui-btn layui-btn-normal" id="rsync-partial-submit">同步partial lead</button>
        </div>
    </form>

    <input type="hidden" value="${env!''}" id="env">

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>GF Status Query</legend>
    </fieldset>
    <form class="layui-form" action="">

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 130px;">GF Domain</label>
            <div class="layui-input-inline">
                <input type="text" name="gfdomain-status" lay-verify="pass" autocomplete="off" class="layui-input" id="gfdomain-status">
            </div>
            <div class="layui-form-mid layui-word-aux">请填写gf站的域名，不包含http前缀以及uri、/等</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 130px;"></label>
            <button class="layui-btn layui-btn-normal" id="query-lead-status">查询</button>
            <button class="layui-btn layui-btn-normal" id="ns-query-submit">NS信息查询</button>
        </div>

        <div id="loading" class="form-horizontal" role="form" style="display:none" >
            <div class="form-group">
                <div class="loading">玩儿命加载中...</div>
            </div>

        </div>
        <div class="layui-form-item" id="rsync-result">

        </div>

    </form>
</div>
<script type="text/javascript" src="js/layui/layui.js"></script>
<script type="text/javascript">

    function popResult(domain,type,reason,total){
        layui.use('layer', function(){
            var layer = layui.layer;
            var env = $('#env').val();

            layer.open({
                type: 2,
                title: 'lead rsync detail!',
                shadeClose: true,
                shade: false,
                maxmin: true, //开启最大化最小化按钮
                area: ['893px', '600px'],
                content: '/gfDomainLeadCheck?domain='+domain+'&type='+type+'&reason='+reason+'&total='+total+'&env='+env
            });
        });
    }

    function rsyncLead(type){
        var buttonId="#rsync-submit";
        if("partial" == type){
            buttonId = "#rsync-partial-submit";
        }
        if (!$("#crmAccount").val() || !$("#gfdomain").val()) {
            layer.msg("输入框不能为空！");
            return false;
        } else {
            $().addClass("layui-btn-disabled");
            $(buttonId).removeClass("layui-btn-normal");
            var reg = /\s+/g;
            var s = $("#gfdomain").val().replace(reg, "");
            var redomain = /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/;
            var url = redomain.exec(s);
            if (url == null || url == '') {
                layer.msg("输入框的域名不正确！");
                $(buttonId).removeClass("layui-btn-disabled");
                $(buttonId).addClass("layui-btn-normal");
                return false;
            }
            try {
                $.ajax({
                    type: "POST",
                    url: "lead/rsync",
                    data: {
                        "crmAccount": $("#crmAccount").val(),
                        "gfdomain": s,
                        "type":type,
                        "env":$('#env').val()
                    },
                    success: function (msg) {
                        layer.msg(msg);
                        console.log(msg);
                        $(buttonId).removeClass("layui-btn-disabled");
                        $("#rsync-submit").addClass("layui-btn-normal");
                    },
                    error: function () {
                        layer.msg("server error");
                        $(buttonId).removeClass("layui-btn-disabled");
                        $(buttonId).addClass("layui-btn-normal");
                    }
                })
            } catch (e) {
                console.log(JSON.stringify(e));
                $(buttonId).removeClass("layui-btn-disabled");
                $(buttonId).addClass("layui-btn-normal");
            }
        }
    }

    $("#rsync-submit").on("click", function () {
        rsyncLead("full");

        return false;
    });

    $("#rsync-partial-submit").on("click", function () {
        rsyncLead("partial");

        return false;
    });

    $("#buildsite-submit").on("click", function () {

        if (!$("#crmAccount").val() || !$("#gfdomain").val()) {
            layer.msg("输入框不能为空！");
            return false;
        } else {
            $("#buildsite-submit").addClass("layui-btn-disabled");
            $("#buildsite-submit").removeClass("layui-btn-normal");
            var reg = /\s+/g;
            var s = $("#gfdomain").val().replace(reg, "");
            var redomain = /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/;
            var url = redomain.exec(s);
            if (url == null || url == '') {
                layer.msg("输入框的域名不正确！");
                $("#buildsite-submit").removeClass("layui-btn-disabled");
                $("#buildsite-submit").addClass("layui-btn-normal");
                return false;
            }
            try {
                $.ajax({
                    type: "POST",
                    url: "lead/buildSite",
                    data: {
                        "crmAccount": $("#crmAccount").val(),
                        "gfdomain": s,
                        "env":$('#env').val()
                    },
                    success: function (msg) {
                        layer.msg(msg);
                        console.log(msg);
                        $("#buildsite-submit").removeClass("layui-btn-disabled");
                        $("#buildsite-submit").addClass("layui-btn-normal");
                    },
                    error: function () {
                        layer.msg("server error");
                        $("#buildsite-submit").removeClass("layui-btn-disabled");
                        $("#buildsite-submit").addClass("layui-btn-normal");
                    }
                })
            } catch (e) {
                console.log(JSON.stringify(e));
                $("#buildsite-submit").removeClass("layui-btn-disabled");
                $("#buildsite-submit").addClass("layui-btn-normal");
            }
        }
        return false;
    });

    $("#query-lead-status").on("click", function () {

        $("#query-lead-status").addClass("layui-btn-disabled");
        $("#query-lead-status").removeClass("layui-btn-normal");
        if ($("#gfdomain-status").val()) {
            var reg = /\s+/g;
            var s = $("#gfdomain-status").val().replace(reg, "");
            var redomain = /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/;
            var url = redomain.exec(s);
            if (url == null || url == '') {
                layer.msg("输入框的域名不正确！");
                $("#query-lead-status").removeClass("layui-btn-disabled");
                $("#query-lead-status").addClass("layui-btn-normal");
                return false;
            }
        }else {
            s = "";
        }

        $('#loading').show();
        $('#rsync-result').hide();
        try {
            $.ajax({
                type: "POST",
                url: "lead/rsyncCheck",
                data: {
                    "gfdomain": s,
                    "env":$('#env').val()
                },
                success: function (msg) {
                    document.getElementById('rsync-result').innerHTML=msg;
                    $('#loading').hide();
                    $('#rsync-result').fadeIn();
                    $("#query-lead-status").removeClass("layui-btn-disabled");
                    $("#query-lead-status").addClass("layui-btn-normal");
                },
                error: function () {
                    layer.msg("server error");
                    $("#query-lead-status").removeClass("layui-btn-disabled");
                    $("#query-lead-status").addClass("layui-btn-normal");
                }
            })
        } catch (e) {
            console.log(JSON.stringify(e));
            $("#query-lead-status").removeClass("layui-btn-disabled");
            $("#query-lead-status").addClass("layui-btn-normal");
        }
        return false;
    });




    $("#ns-query-submit").on("click", function () {

        if ($("#gfdomain-status").val()) {
            var reg = /\s+/g;
            var s = $("#gfdomain-status").val().replace(reg, "");
            var redomain = /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/;
            var url = redomain.exec(s);
            if (url == null || url == '') {
                layer.msg("输入框的域名不正确！");
                $("#query-lead-status").removeClass("layui-btn-disabled");
                $("#query-lead-status").addClass("layui-btn-normal");
                return false;
            }
        }else {
            s = "";
        }
        $('#loading').show();
        $('#rsync-result').hide();
        try {
            $.ajax({
                type: "POST",
                url: "domainInfo/getDomainInfoForgf",
                data: {
                    "domain": s
                },
                success: function (msg) {
                    document.getElementById('rsync-result').innerHTML=msg;
                    $('#loading').hide();
                    $('#rsync-result').fadeIn();
                    $("#ns-query-submit").removeClass("layui-btn-disabled");
                    $("#ns-query-submit").addClass("layui-btn-normal");
                },
                error: function () {
                    layer.msg("server error");
                    $("#ns-query-submit").removeClass("layui-btn-disabled");
                    $("#ns-query-submit").addClass("layui-btn-normal");
                }
            })
        } catch (e) {
            console.log(JSON.stringify(e));
            $("#ns-query-submit").removeClass("layui-btn-disabled");
            $("#ns-query-submit").addClass("layui-btn-normal");
        }
        return false;
    });
</script>

</body>


</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/html">
<head>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/layer.js"></script>
    <title>new lead alert setting</title>
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
<div class="data-form container-fluid">
    <div class="row">
        <div class="col-xs-12 col-sm-9 col-md-10" style="width: 100%;margin-top: 5%">
            <div class="form-horizontal" role="form" >
                <div class="form-group">
                    <label for="username" class="col-sm-offset-2 col-sm-1 control-label">输入域名：</label>
                    <input name="domainName" id="domainName" class="col-sm-3"/>
                    <button id="edit-password-submit" type="submit" class="btn btn-default" style="margin-left: 20px;">提交</button>
                </div>
                <br/>
                <div class="form-group" id="myAlert">
                    <div class="col-sm-offset-2 col-sm-6 alert alert-warning" style="margin-left: 18%">
                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                        <p>域名输入全，如：域名为www.homeznow.com，请勿输入homeznow.com。</p>
                        <p>请去掉前缀https、http，及后缀/、uri、参数</p>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<div hidden="true" page-id="databse-login-page"></div>
<script type="text/javascript" src="//static.chimeroi.com/lib/jquery-1.11.3.min.js"></script>
<script type="text/javascript">

    $("#edit-password-submit").on("click", function () {
        if (!$("#domainName").val()) {
            layer.msg("输入框不能为空");
            return false;
        } else {
            try{
                $('#edit-password-submit').text('玩命儿添加中 ～～ ☆*:.｡. o(≧▽≦)o .｡.:*☆');
                $('#edit-password-submit').attr("disabled",true);
                $.ajax({
                    type: "POST",
                    url: "newLeadAlert/setLeadAlert",
                    data: {
                        "domain": $("#domainName").val()
                    },
                    success: function (msg) {
                        $('#edit-password-submit').text('提交');
                        $('#edit-password-submit').attr("disabled",false);
                        layer.msg(JSON.stringify(msg))
                    },
                    error: function () {
                        $('#edit-password-submit').text('提交');
                        $('#edit-password-submit').attr("disabled",false);
                        layer.msg("server error");
                    }
                })
            }catch (e){
                console.log(JSON.stringify(e))
            }

        }
        return false;
    });
</script>

</body>


</html>

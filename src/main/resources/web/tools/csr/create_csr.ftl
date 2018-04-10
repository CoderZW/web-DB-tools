<#include "/macros/page_macro.ftl">
<@webpage pageCss="css/operater.css" title="create csr">
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
                    <label for="username" class="col-sm-offset-1 col-sm-4 control-label">Domain Name<i class="red">*</i>：</label>
                    <input name="commonName" id="commonName" class="col-sm-3" placeholder="去掉http://、http://、uri、后缀/、参数"/>
                </div>
                <div class="form-group">
                    <label for="username" class="col-sm-offset-1 col-sm-4 control-label">Country Name (2 letter code) [AU]<i class="red">*</i>：</label>
                    <input name="countryName" id="countryName" class="col-sm-3" placeholder="US"/>
                </div>
                <div class="form-group">
                    <label for="username" class="col-sm-offset-1 col-sm-4 control-label">State or Province Name (full name) [Some-State]<i class="red">*</i>：</label>
                    <input name="provinceName" id="provinceName" class="col-sm-3"/>
                </div>
                <div class="form-group">
                    <label for="username" class="col-sm-offset-1 col-sm-4 control-label">Locality Name (eg, city)<i class="red">*</i>：</label>
                    <input name="localityName" id="localityName" class="col-sm-3"/>
                </div>
                <div class="form-group">
                    <label for="username" class="col-sm-offset-1 col-sm-4 control-label">Organization Name (eg, company)<i class="red">*</i>：</label>
                    <input name="organizationName" id="organizationName" class="col-sm-3"/>
                </div>
                <div class="form-group">
                    <label for="username" class="col-sm-offset-1 col-sm-4 control-label">Organizational Unit Name (eg, section)<i class="red"> </i>：</label>
                    <input name="organizadionUnitName" id="organizadionUnitName" class="col-sm-3"/>
                </div>
                <div class="form-group">
                    <label for="username" class="col-sm-offset-1 col-sm-4 control-label">Email Address<i class="red">*</i>：</label>
                    <input name="email" id="email" class="col-sm-3"/>
                </div>
                <br/>

                <div class="form-group">
                    <label for="password" class="col-sm-5 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <button id="edit-password-submit" type="submit" class="btn btn-default">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <button id="copyResult" class="btn3 btn btn-default" data-clipboard-action="copy" data-clipboard-target="#dataExcResult" style="display: none;">复制</button>
                </div>


                <form id="excResultText" class="form-horizontal" role="form" style=""
                      enctype="application/x-www-form-urlencoded" >

                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="//static.chimeroi.com/lib/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/clipboard.js/1.7.1/clipboard.min.js"></script>

<script type="text/javascript">

    $("#edit-password-submit").on("click", function () {
        if (!$("#commonName").val() || !$("#countryName").val() || !$("#provinceName").val() || !$("#localityName").val() || !$("#organizationName").val() || !$("#organizadionUnitName").val() || !$("#email").val()) {
            alert("输入框不能为空");
            return false;
        } else {
            var expr = /^(?=^.{3,255}$)[a-z0-9][-a-z0-9]{0,62}(\.[a-z0-9][-a-z0-9]{0,62})+$/g;

            if(!expr.test($("#commonName").val())){
                alert("domain name不符合规范");
                return false;
            }
            if($("#countryName").val().trim().length>2){
                alert("Country Name不能大于2个字符");
                return false;
            }
            try{
                $('#edit-password-submit').text('☆*:.｡. o(≧▽≦)o .｡.:*☆');
                $('#edit-password-submit').attr("disabled",true);
                $.ajax({
                    type: "POST",
                    url: "csrView/createCsr",
                    data: {
                        "commonName": $("#commonName").val().trim(),
                        "countryName": $("#countryName").val().trim(),
                        "provinceName": $("#provinceName").val().trim(),
                        "localityName": $("#localityName").val().trim(),
                        "organizationName": $("#organizationName").val().trim(),
                        "organizadionUnitName": $("#organizadionUnitName").val().trim(),
                        "email": $("#email").val().trim()
                    },
                    success: function (msg) {
                        $('#edit-password-submit').text('提交');
                        $('#edit-password-submit').attr("disabled",false);
                        $("#copyResult").show();
                        $('#excResultText').html(msg);
                    },
                    error: function () {
                        $('#edit-password-submit').text('提交');
                        $('#edit-password-submit').attr("disabled",false);
                        alert("server error");
                    }
                })
            }catch (e){
                console.log(JSON.stringify(e))
            }

        }
        return false;
    });

    $(document).ready(function(){
        var clipboard3 = new Clipboard('.btn3');
        clipboard3.on('success', function(e) {
            console.log(e);
            alert("复制成功！")
        });
        clipboard3.on('error', function(e) {
            console.log(e);
            alert("复制失败！请手动复制")
        });
    })
</script>
</@webpage>

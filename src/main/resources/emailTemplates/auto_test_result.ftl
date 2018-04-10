<html>
<style type="text/css">
    td {
        text-align: center;
    }
</style>
<body>
<div>
    <table border="1" frame="box">
        <tr>
            <td colspan="2">检验结果</td>
        </tr>
        <tr>
            <td>校验时间</td>
            <td>${time?string("yyyy-MM-dd HH:mm:ss")}</td>
        </tr>
        <tr>
            <td>校验状态</td>
            <td>
            <#if status==0>
                校验中
            <#elseif status==1>
                校验成功
            <#elseif status==2>
                检验失败
            </#if>
            </td>
        </tr>
        <tr>
            <td>校验环境</td>
            <td>
            <#if environment==1>
                线上
            <#elseif environment==2>
                预发布
            <#elseif environment==3>
                测试
            </#if>
            </td>
        </tr>
        <tr>
            <td>校验类型</td>
            <td>
            <#if type==0>
                自动校验
            <#elseif type==1>
                手动校验
            </#if>
            </td>
        </tr>
        <tr>
            <td>校验域名总数</td>
            <td>${total}</td>
        </tr>
        <tr>
            <td>校验域名成功数量</td>
            <td>${total-failed}</td>
        </tr>
        <tr>
            <td>校验域名失败数量</td>
            <td>${failed}</td>
        </tr>
        <tr>
            <td>程序运行主机</td>
            <td>${host}</td>
        </tr>
        <tr>
            <td>程序运行端口</td>
            <td>${port}</td>
        </tr>
        <tr>
            <td colspan="2">
                <a href="${url}">查看详情</a>
            </td>
        </tr>
    <#if host=="172.31.7.50">
        <tr>
            <td colspan="2">
                提示:请确保已在hosts文件中绑定<br> 54.153.101.30:autotest.chime.me
            </td>
        </tr>
    </#if>
    </table>

</div>
</body>
</html>
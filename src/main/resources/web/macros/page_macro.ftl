<#macro webpage title='' keywords=title description=keywords type="base" csstype="base" jstype="site" pageCss="" pageJs="" headerJs="" async="false"  canonicalUrl="" multiSearch="true" ownerHeaderJs = "" isFixTopNav="false">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#include "/base/common.ftl">
    <!--title start-->
    <#if title?exists&&title?has_content><title>${title}</title></#if>
    <!--title end-->
    <!--css start-->
    <#if pageCss?exists&&pageCss?has_content><link href="${pageCss}" rel="stylesheet" type="text/css"/></#if>
    <!--css end-->
    <!--js start-->
    <#if pageJs?exists&&pageJs?has_content><script type="text/javascript" src="${pageJs}" charset="utf-8"></script></#if>
    <!--js end-->

</head>
<body>
<!--content start-->
    <#nested>
<!--content end-->
</body>
</html>
</#macro>

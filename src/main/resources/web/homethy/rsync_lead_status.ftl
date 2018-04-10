<#if domain?exists>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>建站状态</legend>
</fieldset>

<table class="layui-table" lay-even="" lay-skin="row">
    <colgroup>
        <col width="20%">
        <col width="10%">
        <col width="20%">
        <col width="20%">
        <col width="30%">
    </colgroup>
    <thead>
    <tr>
        <th>domain</th>
        <th>status</th>
        <th>crm account</th>
        <th>chime site domain</th>
        <th>note</th>
    </tr>
    </thead>
    <tbody>

    <tr>
        <td>${buildSite.domain}</td>
        <#if buildSite.buildSiteCompleted == 1><td style="background-color: #6ff36f;">Completed</td><#elseif buildSite.buildSiteCompleted == -1><td style="background-color: #adaaaa;">No Result</td><#elseif buildSite.buildSiteCompleted == 2><td style="background-color: yellow;color: red;">Build Fail</td><#else><td style="background-color: #f38b4ed6;">Building</td></#if>
        <td>${buildSite.crmAccount!''}</td>
        <td>${buildSite.chimeDomain!''}</td>
        <td>${buildSite.siteBuildNotes!''}</td>
    </tr>
    </tbody>
</table>
</#if>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>Full Lead同步状态</legend>
</fieldset>
<table class="layui-table" lay-even="" lay-skin="row">
    <colgroup>
        <col width="20%">
        <col width="20%">
        <col width="20%">
        <col width="20%">
        <col width="20%">
    </colgroup>
    <thead>
    <tr>
        <th>domain</th>
        <th>status</th>
        <th>full lead success</th>
        <th>full lead fail</th>
        <th>full lead email repeat</th>
    </tr>
    </thead>
    <tbody>

    <#list leadRestultList as lead>
    <tr>
        <td>${lead.domain}</td>
        <#if lead.isComplete == 1><td style="background-color: #6ff36f;">Completed</td><#elseif lead.isComplete == -1><td style="background-color: #adaaaa;">No Result</td><#else><td style="background-color: #f38b4ed6;">Rsyncing</td></#if>
        <td>${lead.fullLeadSuccess}</td>
        <td style="background-color: yellow;color: red;"><a href="javascript:popResult('${lead.domain}','full','fail','${lead.fullLeadFail}')">${lead.fullLeadFail}</a></td>
        <td style="background-color: yellow;"><a href="javascript:popResult('${lead.domain}','full','exist','${lead.fullLeadEmailExists}')">${lead.fullLeadEmailExists}</a></td>
    </tr>
    </#list>
    </tbody>
</table>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>Partial Lead同步状态</legend>
</fieldset>
<table class="layui-table" lay-even="" lay-skin="row">
    <colgroup>
        <col width="20%">
        <col width="20%">
        <col width="20%">
        <col width="20%">
        <col width="20%">
    </colgroup>
    <thead>
    <tr>
        <th>domain</th>
        <th>status</th>
        <th>partial lead success</th>
        <th>partial lead fail</th>
        <th>partial lead email repeat</th>
    </tr>
    </thead>
    <tbody>

    <#list partialResultList as lead>
    <tr>
        <td>${lead.domain}</td>
        <#if lead.isComplete == 1><td style="background-color: #6ff36f;">Completed</td><#elseif lead.isComplete == -1><td style="background-color: #adaaaa;">No Result</td><#else><td style="background-color: #f38b4ed6;">Rsyncing</td></#if>
        <td>${lead.partialLeadSuccess}</td>
        <td style="background-color: yellow;color: red;"><a href="javascript:popResult('${lead.domain}','partial','fail','${lead.partialLeadFail}')">${lead.partialLeadFail}</a></td>
        <td style="background-color: yellow;"><a href="javascript:popResult('${lead.domain}','partial','exist','${lead.partialLeadEmailExists}')">${lead.partialLeadEmailExists}</a></td>
    </tr>
    </#list>
    </tbody>
</table>
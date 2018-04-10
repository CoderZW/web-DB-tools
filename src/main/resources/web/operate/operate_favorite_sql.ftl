<ul class="nav sidebar-nav" id="favorite_sql_ul">
    <li class="sidebar-brand">
        <a href="#">
            藏经阁
        </a>
    </li>
<#if favoriteSqlList?exists && favoriteSqlList?size &gt; 0>
    <#list favoriteSqlList as favoriteSql>
        <li class="favoriteSqlli fa fa-fw fa-home"><a title="${favoriteSql.name}"
                                                      data-container="body" data-toggle="popover" data-placement="top"
                                                      data-content="${favoriteSql.name}" class="favoriteSql" data-sql='${favoriteSql.favoriteSqlDetail}' data-id="${favoriteSql.id}">${favoriteSql.name}</a>
            <a class="editFavoriteSql" data-sql='${favoriteSql.favoriteSqlDetail}' data-id="${favoriteSql.id}" data-name="${favoriteSql.name}"><i class="glyphicon glyphicon-edit"></i></a>
            <a class="deleteFavoriteSql"  data-id="${favoriteSql.id}"><i class="glyphicon glyphicon-trash"></i></a>
            <a class="shareFavoriteSql"  data-id="${favoriteSql.id}"><i class="glyphicon glyphicon-share"></i></a>
        </li>
    </#list>
<#else>
    <li style="margin-top:20px;color:gray;text-align: center">
        ----------------<br>
        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;秒&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;秒&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;秒&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;气&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
        |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
        ----------------<br>
    </li>
</#if>
</ul>
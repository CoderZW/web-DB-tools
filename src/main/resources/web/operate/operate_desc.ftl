<form id="operateDescription" class="form-horizontal" role="form" style="" xmlns="http://www.w3.org/1999/html">
    <div class="form-group">
        <label for="name" class="col-sm-2 control-label">说明</label>
        <div class="col-sm-7">
            <p>意见或建议发送邮件至：feifei.lei@renren-inc.com</p>
            <p>导入文件请选择txt文件或sql文件</p>
            <p>单条SQL执行table形式返回，多条SQL依然已JSON形式返回展示</p>
            <p>默认查询未加limit最大查询500条</p>
            <p>支持多数据不同表关联查询，表名前加上数据库名schema</p>
            <p>支持多条sql执行，使用<span class="color_red">英文;接回车</span>分隔</p>
            <p>单个条件结果请写在一行内，以下<pan class="color_red">标红</pan>为<pan class="color_red">错误</pan>写法，如:</p>
            <p><pan>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;select * from table_name where</pan></p>
            <p><pan class="color_red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;account='fei</pan></p>
            <p><pan class="color_red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;fei'</pan></p>
            <p><pan>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;and id=1</pan></p>
            <p><pan class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;and json_1='{</pan></p>
            <p><pan class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"data":[</pan></p>
            <p><pan class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;],</pan></p>
            <p><pan class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"status":{</pan></p>
            <p><pan class="color_red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"msg":"suc</pan></p>
            <p><pan class="color_red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cess",</pan></p>
            <p><pan class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"code":0</pan></p>
            <p><pan class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}</pan></p>
            <p><pan class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}'</pan></p>
            <p><pan>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;and email=</pan></p>
            <p><pan>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'feifei@qq.com'</pan></p>
        </div>
    </div>

</form>
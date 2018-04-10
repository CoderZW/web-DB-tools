<#include "/macros/page_macro.ftl">
<@webpage pageCss="css/operater.css" title="upload" >
<header>

    <section class="header-container">
        <div class="logo">

            <embed src="https://static.chimeroi.com/home/images/logo/logo2.svg" onload="this.style.opacity = '1'"
                   style="opacity: 1;">
                <img class="blackChime" style="display: none" src="//static.chimeroi.com/crm/images/logo/chime2.svg"
                     alt="">
                <a href="" class="logo"></a>
        </div>

    </section>
</header>
<link rel="stylesheet" href="css/webuploader.css">
<div class="data-form container-fluid">
    <div class="row">
        <div class="col-xs-12 col-sm-9 col-md-10" style="width: 100%;margin-top: 5%">
            <div id="uploader" class="wu-example" style="text-align: center;">
                <!--用来存放文件信息-->
                <div id="thelist" class="uploader-list"></div>
                <div class="btns">
                    <div id="picker">选择文件</div>
                    <button id="ctlBtn" class="btn btn-default" style="margin-top: 20px;">开始上传</button>
                </div>
                <p>可同时上传多个文件，总文件大小不能超过1G</p>
                <#if env?exists && env == 'test'>
                    <p>文件存放位置：10.4.36.107机器，/data/web/upload/${user.account}</p>
                    <p>ssh:10.4.36.107</p>
                <#else>
                    <p>site预发布2机器，/home/ec2-user/upload/${user.account}<p>
                    <p>107或者116机器上输入：fast2，fast2即可登录site预发布2机器<p>
                </#if>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="js/webuploader.min.js"></script>
<script type="text/javascript" src="js/layer.js"></script>
<script>
    var $btn = $('#ctlBtn');
    var $thelist = $('#thelist');
    var chunkSize = 8 * 1024 * 1024;

    var chunks = {};//分片总数量，用来上传成功以后校验分片文件总数
    WebUploader.Uploader.register({
        'before-send': 'beforeSendFile'
    }, {
        beforeSendFile: function (block) {
            var me = this,
                    owner = this.owner,
                    deferred = WebUploader.Deferred();
            chunks[block.file.name] = block.chunks;
            owner.md5File(block.blob);
            deferred.resolve();
            return deferred.promise();
        }
    });

    // 实例化
    var uploader = WebUploader.create({
        pick: {
            id: '#picker',
            label: '点击选择文件'
        },
        formData: {
            uid: 0,
            md5: '',
            chunkSize: chunkSize
        },
        //dnd: '#dndArea',
        //paste: '#uploader',
        swf: 'js/Uploader.swf',
        chunked: true,
        chunkSize: chunkSize, // 字节 1M分块
        threads: 3,
        server: 'upload/fileUpload',
        auto: false,

        // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
        disableGlobalDnd: true,
        fileNumLimit: 1024,
        fileSizeLimit: 1024 * 1024 * 1024,    // 200 M
        fileSingleSizeLimit: 1024 * 1024 * 1024    // 50 M
    });

    // 当有文件被添加进队列的时候
    uploader.on('fileQueued', function (file) {
        console.log("fileQueued");
        $thelist.append('<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">等待上传...</p>' +
                '</div>');
    });

    //当某个文件的分块在发送前触发，主要用来询问是否要添加附带参数，大文件在开起分片上传的前提下此事件可能会触发多次。
    uploader.onUploadBeforeSend = function (obj, data) {
        console.log("onUploadBeforeSend");
        var file = obj.file;
        data.md5 = file.md5 || '';
        data.uid = file.uid;
    };
    // 上传中
    uploader.on('uploadProgress', function (file, percentage) {
        getProgressBar(file, percentage, "FILE", "上传进度");
    });

    // 文件上传成功,合并文件。
    uploader.on('uploadSuccess', function (file, block) {
        $.post("upload/mergeFile", {chunks: chunks[file.name], name: file.name},
                function (data) {
                    if(data.indexOf('page-id="databse-login-page"')>-1){
                        window.location.reload();
                        return false;
                    }
                    var result = JSON.parse(data);
                    var text = '已上传';
                    if(result.status.code != 0){
                        text = result.data;
                    }
                    $('#' + file.id).find('p.state').text(text);
                });
    });

    uploader.on('uploadError', function (file) {
        $('#' + file.id).find('p.state').text('上传出错');
    });
    uploader.on('uploadComplete', function (file) {
        // 隐藏进度条
        // fadeOutProgress(file, 'MD5');
        // fadeOutProgress(file, 'FILE');
    });
    // 文件上传
    $btn.on('click', function () {
        console.log("上传...");
        uploader.upload();
        console.log("上传成功");
    });

    /**
     *  生成进度条封装方法
     * @param file 文件
     * @param percentage 进度值
     * @param id_Prefix id前缀
     * @param titleName 标题名
     */
    function getProgressBar(file, percentage, id_Prefix, titleName) {
        var $li = $('#' + file.id), $percent = $li.find('#' + id_Prefix + '-progress-bar');
        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<div id="' + id_Prefix + '-progress" class="progress progress-striped active">' +
                    '<div id="' + id_Prefix + '-progress-bar" class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>'
            ).appendTo($li).find('#' + id_Prefix + '-progress-bar');
        }
        var progressPercentage = percentage * 100 + '%';
        $percent.css('width', progressPercentage);
        $percent.html(titleName + ':' + progressPercentage);
    }

    uploader.on("error", function (type) {
        if (type == "Q_EXCEED_SIZE_LIMIT") {
            layer.msg("文件大小不能超过1G");
        }else {
            layer.msg("上传出错！请检查后重新上传！错误代码"+type);
        }
    });

    /**
     * 隐藏进度条
     * @param file 文件对象
     * @param id_Prefix id前缀
     */
    function fadeOutProgress(file, id_Prefix) {
        $('#' + file.id).find('#' + id_Prefix + '-progress').fadeOut();
    }
</script>
</@webpage>

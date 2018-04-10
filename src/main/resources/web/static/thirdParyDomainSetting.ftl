<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/html">
<head>
    <link rel="stylesheet" href="/css/style.css" media="all" />
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all" />
    <script type="text/javascript" src="/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/js/layer.js"></script>
    <title>lead rsync from gf</title>
    <style type="text/css">
        .absolute-center{
            /*width: 70%;*/
            height: 80%;
            margin-top: 0;
            margin-bottom: 0;
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            margin: auto;
            cursor: pointer;
        }

    </style>
</head>

<body >
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<div class="layui-container">

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>Third party domain setting for Chime</legend>
    </fieldset>



    <div class="layui-tab layui-tab-brief" lay-filter="demo">
        <ul class="layui-tab-title">
            <li class="layui-this">Godaddy</li>
            <li>Google</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">

                <div class="layui-carousel" id="godaddy_setting_guidance">
                    <div carousel-item>
                        <div >
                            <div class="layui-colla-content layui-show">
                                <p class="" >1、Login your godaddy account.</p>
                                <p class="" >2、Click 'DNS' button on the right of your domain.</p>
                            </div>
                            <div id="layer-photos-godaddy1" class="layer-photos-alert">
                                <img class="absolute-center" src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763686085416949.png">
                            </div>
                        </div>
                        <div >
                            <div class="layui-colla-content layui-show">
                                <p class="" >3、Then you will see default name server records.</p>
                                <p class="" >4、If there are no records on this page, please send us your godaddy account credentials.</p>
                            </div>
                            <div  id="layer-photos-godaddy2" class="layer-photos-alert">
                                <img class="absolute-center" src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763686083168986.png" >
                            </div>
                        </div>
                        <div >
                            <div class="layui-colla-content layui-show">
                                <p class="" >5、Base on step 4, please update the A record as shown below.</p>
                            </div>
                            <div id="layer-photos-godaddy3" class="layer-photos-alert">
                                <img class="absolute-center" src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763686081825481.png">
                            </div>
                        </div>


                        <div >
                            <div class="layui-colla-content layui-show">
                                <p class="" >6、If there is only one A record, please add a new one as shown below.</p>
                            </div>
                            <div id="layer-photos-godaddy4" class="layer-photos-alert">
                                <img class="absolute-center" src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763685819025386.png">
                            </div>
                        </div>
                        <div >
                            <div class="layui-colla-content layui-show">
                                <p class="" >7、Then add a new cname record as shown below:</p>
                                <p class="" >&nbsp;&nbsp;&nbsp;Host='www' &nbsp;&nbsp;&nbsp;Point to='@' &nbsp;&nbsp;&nbsp;TTL='1 Hour' </p>
                            </div>
                            <div  id="layer-photos-godaddy5" class="layer-photos-alert">
                                <img class="absolute-center" src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763686081465835.png" >
                            </div>
                        </div>
                        <div >
                            <div class="layui-colla-content layui-show">
                                <p class="" >8、Make sure you only have 2 A records with name='@' .</p>
                            </div>
                            <div id="layer-photos-godaddy6" class="layer-photos-alert">
                                <img class="absolute-center" src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763686083751969.png">
                            </div>
                        </div>
                        <div >
                            <div class="layui-colla-content layui-show">
                                <p class="" >9、Please remove any forwarding settings.</p>
                            </div>
                            <div id="layer-photos-godaddy7" class="layer-photos-alert">
                                <img class="absolute-center" src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763686431482966.png">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="layui-carousel" id="google_setting_guidance">
                    <div carousel-item>
                        <div >
                            <div id="layer-photos-google1" class="layer-photos-alert">
                                <img class="absolute-center" src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763441957535449.png">
                            </div>
                            <div class="layui-colla-content layui-show">
                                <p class="" >1、The domain list page of domains.google.com!</p>
                                <p class="" >2、Click on the DNS button!</p>
                            </div>
                        </div>
                        <div >
                            <div  id="layer-photos-google2" class="layer-photos-alert">
                                <img class="absolute-center" layer-src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763441960694895.png" src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763441960694895.png" >
                            </div>
                            <div class="layui-colla-content layui-show">
                                <p class="" >3、If your name server is google default name server as shown below, please go to step 5. </p>
                                <p class="" >4、If your name server is already custom name server. Please send your google account credentials to us for next steps.</p>
                            </div>
                        </div>
                        <div >
                            <div class="layui-colla-content layui-show">
                                <p class="" >5、Go to ​the​ bottom of the page where you can see '​Custom resource records', and set up 3 records as below.</p>
                                <p class="" >6、Then you are all set!</p>
                            </div>
                            <div id="layer-photos-google3" class="layer-photos-alert">
                                <img class="absolute-center" src="https://cdn.chime.me/image/fs01/agnentinfo/20180322/23/original_6763443885727239.png">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="/js/layui/layui.js"></script>

<script>

    layui.use('element', function(){
        var element = layui.element;
        element.on('tab(demo)', function(data){
//            layer.msg('切换了：'+ this.innerHTML);
//            console.log(data);
        });
    });

    layui.use('carousel', function(){
        var carousel = layui.carousel;
        //建造实例
        carousel.render({
            elem: '#godaddy_setting_guidance'
            ,width: '100%' //设置容器宽度
            ,height: '800px'
            ,arrow: 'always' //始终显示箭头
            ,autoplay: false
            ,arrow: 'hover'
//            ,full: true
            //,anim: 'updown' //切换动画方式
        });

        carousel.render({
            elem: '#google_setting_guidance'
            ,width: '100%' //设置容器宽度
            ,height: '800px'
            ,arrow: 'always' //始终显示箭头
            ,autoplay: false
            ,arrow: 'hover'
            //,anim: 'updown' //切换动画方式
        });
    });



    $(document).ready(function () {
        var photos = $('.layer-photos-alert');
        photos.each( function ( ) {
//            console.log($(this));
            var id = $(this).attr("id");
            id='#'+id;
//            console.log(id);
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.photos({
                    photos: id
                    ,anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
                });
            });
        })
    });

</script>


</body>


</html>

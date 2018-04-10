layui.config({
    base : "js/"
}).use(['form','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;
    //video背景
    // $(window).resize(function(){
    // 	if($(".video-player").width() > $(window).width()){
    // 		$(".video-player").css({"height":$(window).height(),"width":"auto"});
    // 	}else{
    // 		$(".video-player").css({"width":$(window).width(),"height":"auto"});
    // 	}
    // }).resize();

    // //登录按钮事件
    // form.on("submit(login)",function(data){
    // 	// window.location.href = "../../index.html";
    //    if (!$("#username").val() || !$("#password").val()) {
    //        layer.msg("用户名或密码不能为空");
    //        return false;
    //    } else {
    //        $.ajax({
    //            type: "POST",
    //            url: "login",
    //            data: {
    //                "username": $("#username").val(),
    //                "password": $("#password").val()
    //            },
    //            success: function (msg) {
    //                var result = eval('(' + msg + ')');
    //                if (result.status.code == "0") {
    //                    window.location.href = "login";
    //                } else {
    //                    layer.msg("用户名或密码错误！");
    //                }
    //            },
    //            error: function () {
    //                layer.msg("登录错误");
    //            }
    //        })
    //    }
    // 	return false;
    // })

    function loginSub() {
        if (!$("#username").val() || !$("#password").val()) {
            layer.msg("用户名或密码不能为空");
            return false;
        } else {
            $.ajax({
                type: "POST",
                url: "login",
                data: {
                    "username": $("#username").val(),
                    "password": $("#password").val()
                },
                success: function (msg) {
                    var result = eval('(' + msg + ')');
                    if (result.status.code == "0") {
                        window.location.href = "login";
                    } else {
                        layer.msg("用户名或密码错误！");
                    }
                },
                error: function () {
                    layer.msg("登录错误");
                }
            })
        }
        return false;
    }
    $("#login").on("click", function () {
        loginSub();
    });

    $(document).keyup(function(event){
        if(event.keyCode ==13){
            loginSub();
        }
    });
});
// $("#login").on("click", function () {
//     if (!$("#username").val() || !$("#password").val()) {
//         layer.msg("用户名或密码不能为空");
//         return false;
//     } else {
//         $.ajax({
//             type: "POST",
//             url: "login",
//             data: {
//                 "username": $("#username").val(),
//                 "password": $("#password").val()
//             },
//             success: function (msg) {
//                 var result = eval('(' + msg + ')');
//                 if (result.status.code == "0") {
//                     window.location.href = "login";
//                 } else {
//                     layer.msg("用户名或密码错误！");
//                 }
//             },
//             error: function () {
//                 layer.msg("登录错误");
//             }
//         })
//     }
//     return false;
// });

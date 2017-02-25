/**
 * Created by tina on 2/20/17.
 */
function login() {
    if (checkLoginForm()) {
        showUploadModal("请稍等");
        $.ajax({
            data:{
                email: $("input[name=inputEmail]").val(),
                password: $("input[name=inputPassword]").val()
            },
            type:"post",
            dataType: 'json',
            url:"/loginUser",
            error:function(data){
                hideUploadModal();
                showHint("出错,请重试", "确定");
            },
            success:function(data){
                if(data.result == "fail") {
                    hideUploadModal();
                    showHint("账号密码错误,请重试", "确定");
                } else if (data.result == "success"){
                    window.location.href = "./"
                }
            }
        });
    }
}

function checkLoginForm() {
    if ($("input[name=inputEmail]").val() == ""){
        showHint("未填写邮箱", "确定");
        $("input[name=inputEmail]").focus();
        return false;
    }
    if(!$("input[name=inputEmail]").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
        showHint("邮箱格式不正确", "确定");
        $("input[name=inputEmail]").focus();
        return false;
    }

    if ($("input[name=inputPassword]").val() == ""){
        showHint("未填写密码", "确定");
        $("input[name=inputPassword]").focus();
        return false;
    }
    return true;
}

function signup() {
    if (checkSignupForm()) {
        showUploadModal("请稍等");
        $.ajax({
            data:"email="+$("input[name=email]").val(),
            type:"post",
            dataType: 'json',
            url:"/checkEmailUsed",
            error:function(data){
                hideUploadModal();
                showHint("出错,请重试", "确定");
            },
            success:function(data){
                if(data.used) {
                    hideUploadModal();
                    showHint("邮箱已被注册,请使用其他邮箱", "确定");
                } else {
                    $("#signupForm").submit();
                }
            }
        });
    }
}

function checkSignupForm() {
    if ($("input[name=email]").val() == ""){
        showHint("未填写邮箱", "确定");
        $("input[name=email]").focus();
        return false;
    }
    if(!$("input[name=email]").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
        showHint("邮箱格式不正确", "确定");
        $("input[name=email]").focus();
        return false;
    }
    if ($("input[name=name]").val() == ""){
        showHint("未填写用户名", "确定");
        $("input[name=name]").focus();
        return false;
    }
    if ($("input[name=password]").val() == ""){
        showHint("未填写密码", "确定");
        $("input[name=password]").focus();
        return false;
    }
    if ($("input[name=password]").val() != $("#verifyPdInput").val()){
        showHint("两次密码不相同", "确定");
        return false;
    }
    return true;
}
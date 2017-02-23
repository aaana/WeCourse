/**
 * Created by tina on 2/20/17.
 */
function login() {
    if (checkLoginForm()) {
        $('#uploadModal').modal('show');
        $.ajax({
            data:{
                email: $("input[name=inputEmail]").val(),
                password: $("input[name=inputPassword]").val()
            },
            type:"post",
            dataType: 'json',
            url:"/loginUser",
            error:function(data){
                $('#uploadModal').modal('hide');
                showHint("出错,请重试");
            },
            success:function(data){
                if(data.result == "fail") {
                    $('#uploadModal').modal('hide');
                    showHint("账号密码错误,请重试");
                } else if (data.result == "success"){
                    window.location.href = "./"
                }
            }
        });
    }
}

function checkLoginForm() {
    if ($("input[name=inputEmail]").val() == ""){
        showHint("未填写邮箱");
        $("input[name=inputEmail]").focus();
        return false;
    }
    if(!$("input[name=inputEmail]").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
        showHint("邮箱格式不正确");
        $("input[name=inputEmail]").focus();
        return false;
    }

    if ($("input[name=inputPassword]").val() == ""){
        showHint("未填写密码");
        $("input[name=inputPassword]").focus();
        return false;
    }
    return true;
}

function signup() {
    if (checkSignupForm()) {
        $('#uploadModal').modal('show');
        $.ajax({
            data:"email="+$("input[name=email]").val(),
            type:"post",
            dataType: 'json',
            url:"/checkEmailUsed",
            error:function(data){
                $('#uploadModal').modal('hide');
                showHint("出错,请重试");
            },
            success:function(data){
                if(data.used) {
                    $('#uploadModal').modal('hide');
                    showHint("邮箱已被注册,请使用其他邮箱");
                } else {
                    $("#signupForm").submit();
                }
            }
        });
    }
}

function checkSignupForm() {
    if ($("input[name=email]").val() == ""){
        showHint("未填写邮箱");
        $("input[name=email]").focus();
        return false;
    }
    if(!$("input[name=email]").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
        showHint("邮箱格式不正确");
        $("input[name=email]").focus();
        return false;
    }
    if ($("input[name=name]").val() == ""){
        showHint("未填写用户名");
        $("input[name=name]").focus();
        return false;
    }
    if ($("input[name=password]").val() == ""){
        showHint("未填写密码");
        $("input[name=password]").focus();
        return false;
    }
    if ($("input[name=password]").val() != $("#verifyPdInput").val()){
        showHint("两次密码不相同");
        return false;
    }
    return true;
}
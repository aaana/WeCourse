function log_2_sign() {
	$('.form-login').hide();
	$('.form-signup').slideDown();
	//只清空密码栏
	$('.form-login #inputPassword').val("");
}
function sign_2_log() {
	$('.form-signup').hide();
	$('.form-login').slideDown();
	resetSignup();
}
function resetSignup() {
	$('.form-signup input[type="radio"]').eq(0).click();
	$('.form-signup .school-form').val(0);
	$('.form-signup #inputUserId').val("");
	$('.form-signup #inputPassword').val("");
	$('.form-signup .userVerify').attr('src', "");
}

$.fn.labelInit = function() {
	var separator = "|";

	return this.each(function(){
		var $object = $(this);
		if($object.is(":radio") === false)
			return this;
		$object.css({display : "none"});
		var labels = $object.attr("data-labelauty");
		$object.removeAttr("data-labelauty");
		var messages = labels.split(separator);
		var input_id = $object.attr("id");
		$object.after(create(input_id, messages, true));
	});
}
function create(input_id, messages, haveText) {
	var block;
	var unchecked_message;
	var checked_message;

	if(messages == null)
		unchecked_message = checked_message = "";
	else
	{
		unchecked_message = messages[0];
		if( messages[1] == null )
			checked_message = unchecked_message;
		else
			checked_message = messages[1];
	}

	if(haveText == true)
	{
		block = '<label for="' + input_id + '">' +
					'<span class="label-unchecked-image"></span>' +
					'<span class="label-unchecked">' + unchecked_message + '</span>' +
					'<span class="label-checked-image"></span>' +
					'<span class="label-checked">' + checked_message + '</span>' +
				'</label>';
	}
	else
	{
		block = '<label for="' + input_id + '">' +
					'<span class="label-unchecked-image"></span>' +
					'<span class="label-checked-image"></span>' +
				'</label>';
	}
	return block;
};

function schoolListInit() {
	var schoolList = schoolStr.split("|");
	var block = '';
	for (school in schoolList) {
		block = block + '<option value="' + school + '">' + schoolList[school] + '</option>';
	}
	$('.school-form').append(block);
}
function signup_check() {
	if ($('.form-signup #inputUserId').val() !== "") {
		//$('.form-signup .userVerify').attr('src', 'resource/img/loading.gif');
		var json = {
            userType: $('.userType input[type="radio"]:checked').val(), //0-学生 1-教师
            school: $('.school-form').val(), //学校序号
            id: $('.form-signup #inputUserId').val()
        }
        $.ajax({
            type:"post",
            url:"registercheck",
            data:json,
            dataType:"json",
            success:function(data){
                if(data.success == 'true') {
                	$('.form-signup .userVerify').attr('src', 'resource/img/input-yes.png');
                }
                else {
                	$('.form-signup .userVerify').attr('src', 'resource/img/input-no.png');
                }
            },
            error:function(){
                $('.form-signup .userVerify').attr('src', 'resource/img/input-no.png');
            }
        });
	}
}
function title_change() {
	var old = $(this).val();
	if (old == 0) {
		$('.form-signup ul:nth-child(4) label').text("学号");
	} else {
		$('.form-signup ul:nth-child(4) label').text("工号");
	}
	signup_check();
}

var schoolStr = "同济大学|交通大学|复旦大学";
function init() {
	$('.form-signup .school-form').change(signup_check);
	$('.form-signup #inputUserId').change(signup_check);
	$('.userType input').change(title_change);
	//$('#login_button').click(login);
	$('#signup_button').click(signup);

}

function signup() {
	var json = {
		userType: $('.userType input[type="radio"]:checked').val(), //0-学生 1-教师
		school: $('.school-form').val(), //学校序号
		id: $('.form-signup #inputUserId').val(),
		pd: $('.form-signup #inputPassword').val()
	};
	$.ajax({
		type:"post",
		url:"register",
		data:json,
		dataType:"json",
		success:function(data){
			if(data.success == 'true') {
				
			}
			else {
				alert("invalid user");
			}
		},
		error:function(){
			alert("sign up error");
		}
	});
}
/*
function login() {
	var json = {
		id: $('.form-login #inputUserId').val(),
		pd: $('.form-login #inputPassword').val()
	};
	$.ajax({
		type:"post",
		url:"login",
		data:json,
		dataType:"json",
		success:function(data){
			if(data.success == 'true') {
				//
			}
			else {
				//
			}
		},
		error:function(){
			alert("login error");
		}
	});

}*/
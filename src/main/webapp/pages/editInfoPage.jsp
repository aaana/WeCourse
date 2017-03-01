<%--
  Created by IntelliJ IDEA.
  User: tina
  Date: 2/18/17
  Time: 1:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>编辑个人信息</title>
    <link rel="stylesheet" type="text/css" href="resource/Bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="resource/css/weike.css">
    <link rel="stylesheet" type="text/css" href="resource/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="resource/css/upload.css">
    <link rel="stylesheet" type="text/css" href="resource/css/loading.css">
    <script type="text/javascript" src="resource/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="resource/Bootstrap/js/bootstrap.js"></script>

</head>
<body>
<jsp:include page="template/navbar.jsp" />
<jsp:include page="template/formhelper.jsp" />

<div class="container body-content">
    <div class="content">
        <div class="col-sm-12">
            <h2 class="col-sm-offset-2 col-sm-9">修改个人信息</h2>
        </div>
        <form class="form-horizontal" action="updateUserInfo" enctype="multipart/form-data" method="post">
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="name" id="name" value="${user.name}" />
                    <input type="hidden" id="oldName" value="${user.name}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="email" class="col-sm-2 control-label">邮箱</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="email" id="email" value="${user.email}" />
                    <input type="hidden" id="oldEmail" value="${user.email}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">头像</label>
                <div class="col-sm-9">
                    <img id="avatarPreview" width="100px" src="../resource/img/${user.avatar}" />
                    <button id="avatarBtn" type="button" class="btn btn-primary" onclick="avatarInput.click();event.target.blur();">更换头像</button>
                    <input id="avatarInput" style="display:none" type="file" name="avatar" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" />
                    <input type="hidden" id="oldAvatar" value="${user.avatar}">
                </div>
            </div>
            <div class="form-group">
                <label for="school" class="col-sm-2 control-label">学校</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="school" id="school" value="${user.school}">
                </div>
            </div>

            <div class="form-group">
                <label for="introduction" class="col-sm-2 control-label">介绍</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="introduction" id="introduction" value="${user.introduction}">
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-9">
                    <button type="button" data-toggle="modal" data-target="#cancelModal" class="btn btn-default">取消</button>
                    <button type="button" onclick="submitForm()" class="btn btn-default">确定</button>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>

<script>
    $(document).ready(function() {

        $("#avatarInput").change(function () {
            if ($("#avatarInput").val() == "") {
                $("#avatarPreview").attr("src", "../resource/img/" + $("#oldAvatar").val());
            } else{
                imagePreview($("#avatarInput")[0], $("#avatarPreview")[0]);
            }
        });

    });

    function showHint(string) {
        $("#reviewModal .modal-body").html(string);
        $('#reviewModal').modal('show');
    }

    function submitForm() {
        if (checkRequired()) {
            $("form").submit();
            $('#uploadModal').modal('show');
        }
    }

    function checkRequired() {
        if ($("#name").val() == ""){
            showHint("未填写用户名,使用原来的用户名");
            $("#name").val($("#oldName").val());
            return false;
        }
        if ($("#email").val() == ""){
            showHint("未填写邮箱,使用原来的邮箱");
            $("#email").val($("#oldEmail").val());
            return false;
        }
        if(!$("#email").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
            showHint("邮箱格式不正确");
            $("#email").focus();
            return false;
        }
        return true;
    }

    function imagePreview(input, img){
        var files = input.files;
        for (var i = 0; i < files.length; i++) {
            var file = files[i];
            img.style.width = "100px";
            var reader = new FileReader();
            reader.onload = (function(aImg) {
                return function(e) {
                    aImg.src = e.target.result;
                };
            })(img);
            reader.readAsDataURL(file);
        }
    }
</script>

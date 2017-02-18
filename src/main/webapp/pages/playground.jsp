<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
Created by IntelliJ IDEA.
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>WeiKeHome</title>
    <link rel="stylesheet" type="text/css" href="resource/Bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="resource/pbl/css/default.css" />
    <link rel="stylesheet" type="text/css" href="resource/css/weike.css">
    <link rel="stylesheet" type="text/css" href="resource/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="resource/css/pblItem.css">
    <link rel="stylesheet" type="text/css" href="resource/css/sidebar.css">
    <link rel="stylesheet" type="text/css" href="resource/css/loading.css">

    <script src="resource/pbl/js/modernizr-custom.js"></script>

    <script type="text/javascript" src="resource/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="resource/Bootstrap/js/bootstrap.min.js"></script>
    <script src="resource/pbl/js/imagesloaded.pkgd.min.js"></script>
    <script src="resource/pbl/js/masonry.pkgd.min.js"></script>
    <script src="resource/pbl/js/classie.js"></script>
    <script src="resource/pbl/js/main.js"></script>

    <script src="resource/js/pblItem.js"></script>
    <script>
        function showHint(string) {
            $("#reviewModal .modal-body").html(string);
            $('#reviewModal').modal('show');
        }

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
                return false;
            }
            if ($("input[name=inputPassword]").val() == ""){
                showHint("未填写密码");
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
                return false;
            }
            if ($("input[name=name]").val() == ""){
                showHint("未填写用户名");
                return false;
            }
            if ($("input[name=password]").val() == ""){
                showHint("未填写密码");
                return false;
            }
            if ($("input[name=password]").val() != $("#verifyPdInput").val()){
                showHint("两次密码不相同");
                return false;
            }
            return true;
        }

    </script>
</head>
<body>
    <jsp:include page="template/navbar.jsp" />

    <div class="container body-content">
        <div class="col-xs-9 hotWeike_c content">
            <form class="playgroundSearchDiv" action="" method="POST">
                <div class="input-group">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 标题 <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li><a href="#">标题</a></li>
                            <li><a href="#">作者</a></li>
                            <li><a href="#">内容</a></li>
                        </ul>
                    </div>
                    <input type="text" class="form-control" placeholder="搜索">
                    <span class="input-group-btn">
                        <button class="btn" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                    </span>
                </div>
            </form>
            <h5>当前搜索：</h5>
            <div class="js">
                <div class="grid">
                    <c:forEach var="weikeCell" items="${weikeCells}">
                        <br/>
                        <div class="grid__item" data-size='1280x1280'>
                            <div class="thumbnail weikeCell">
                                <a href="uploadfiles/${weikeCell.file_url}" class="img-wrap">
                                    <img src="uploadfiles/${weikeCell.thumbnail_url}">
                                </a>
                                <div class="weikeCellDes">
                                    <h3>${weikeCell.title}</h3>
                                    <div class="weikeCellVote">
                                        <h5>${weikeCell.subject}</h5>
                                        <span></span>
                                        <span><span class="glyphicon glyphicon-heart"></span>${weikeCell.star_num}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="description description--grid">
                                <div class="weikeCellDetail">
                                    <h4><em>${weikeCell.user_name}</em></h4>
                                    <h3>${weikeCell.title}</h3>
                                    <h4>${weikeCell.description}</h4>
                                    <div>
                                        <a><span class="glyphicon glyphicon-heart"></span>${weikeCell.star_num}</a>
                                    </div>
                                </div>
                                <div class="weikeCellComment">
                                    <div class="input-group input-group-sm">
                                        <input type="text" class="form-control" placeholder="我来评论"><span class="input-group-btn">
                                        <button class="btn btn-default" type="button">评论</button>
                                    </span>
                                    </div>
                                </div>
                                <div class="weikeCellShowComment">
                                    <a>查看评论</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="preview">
                    <button class="action action--close"><h1>×</h1></button>
                    <div class="description description--preview"></div>
                </div>
            </div>
        </div>

        <div class="col-xs-3">
            <c:choose>
                <c:when test="${user != null}">
                    <jsp:include page="template/loggedPlaygoundSidbar.jsp" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="template/unloggedPlaygroundSidebar.jsp" />
                </c:otherwise>
            </c:choose>

        </div>
    </div>

    <jsp:include page="template/footer.jsp" />
</body>
</html>


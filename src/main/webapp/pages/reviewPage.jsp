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
    <link rel="stylesheet" type="text/css" href="resource/css/sidebar.css">
    <link rel="stylesheet" type="text/css" href="resource/css/myMessage.css">
    <link rel="stylesheet" type="text/css" href="resource/css/loading.css">
    <link rel="stylesheet" type="text/css" href="resource/css/displayModal.css">

    <link href="resource/video-js/video-js.css" rel="stylesheet" type="text/css">
    <script src="resource/video-js/video.js"></script>

    <script src="resource/pbl/js/modernizr-custom.js"></script>
    <script type="text/javascript" src="resource/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="resource/Bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="resource/js/weikeOpHelper.js"></script>
    <script type="text/javascript" src="resource/js/noticeOpHelper.js"></script>
    <script>
        videojs.options.flash.swf = "/resource/video-js/video-js.swf";
        $(document).ready(function () {
            showNotices();
            $('#displayModal').on('shown.bs.modal', function (event) {
                if (!$("body").hasClass("modal-open")) {
                    $("body").addClass("modal-open");
                }
            })
        })
    </script>

</head>
<body>
    <jsp:include page="template/navbar.jsp" />
    <input type="hidden" id="pageNum" value="${pageNum}" />
    <input type="hidden" id="unread" value="${unread}" />

    <div class="container body-content">
        <div class="col-xs-9 hotWeike_c content">
            <div class="checkboxList">
                <h4>筛选</h4>
                <div class="checkbox">
                    <label class="checkbox-inline">
                        <input type="checkbox" id="unreadCheckBox" > 未读
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="favoriteCheckBox"> 收藏
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="followCheckBox"> 关注
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="commentCheckBox"> 微课评论
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="replyCheckBox"> 评论回复
                    </label>
                    <button class="btn btn-default" onclick="reloadNotices()">确定</button>
                </div>
            </div>
            <div class="personalPageContentItem messageList">
            </div>
        </div>
        <div class="col-xs-3">
            <div id="sidebar" data-spy="affix">
                <jsp:include page="template/loggedPlaygoundSidbar.jsp" />
            </div>
        </div>
    </div>

    <jsp:include page="template/displayModal.jsp" />
    <jsp:include page="template/formhelper.jsp" />
    <jsp:include page="template/footer.jsp" />
</body>
</html>



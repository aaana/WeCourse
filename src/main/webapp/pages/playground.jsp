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
    <link rel="stylesheet" type="text/css" href="resource/css/displayModal.css">

    <script src="resource/pbl/js/modernizr-custom.js"></script>

    <script type="text/javascript" src="resource/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="resource/Bootstrap/js/bootstrap.min.js"></script>
    <script src="resource/pbl/js/imagesloaded.pkgd.min.js"></script>
    <script src="resource/pbl/js/masonry.pkgd.min.js"></script>
    <script src="resource/pbl/js/classie.js"></script>
    <script src="resource/pbl/js/main1.js"></script>

    <link href="resource/video-js/video-js.css" rel="stylesheet" type="text/css">
    <script src="resource/video-js/video.js"></script>

    <script src="resource/js/pblItem.js"></script>
    <script src="resource/js/weikeOpHelper.js"></script>
    <script src="resource/js/searchHelper.js"></script>
    <script src="resource/js/authHelper.js"></script>

    <script>
        videojs.options.flash.swf = "/resource/video-js/video-js.swf";
        $(document).ready(function () {
            $('#displayModal').on('hide.bs.modal', function (event) {
                if ($("#displayModal video").length != 0) {
                    videojs($("#displayModal video")[0]).pause();
                    videojs($("#displayModal video")[0]).dispose();
                }
            });
            $('#displayModal').on('hidden.bs.modal', function (event) {
                if ($("body").css("overflow") == "hidden") {
                    $("body").css("overflow", "");
                }
                if ($("html").css("overflow") == "hidden") {
                    $("html").css("overflow", "");
                }
            });
            $("#uploadModal").on('hidden.bs.modal', function (event) {
                if($("#displayModal").hasClass("in")) {
                    $("body").addClass("modal-open");
                }
            });
            $("#reviewModal").on('hidden.bs.modal', function (event) {
                if($("#displayModal").hasClass("in")) {
                    $("body").addClass("modal-open");
                }
            });
        });
    </script>
</head>
<body>
    <jsp:include page="template/navbar.jsp" />

    <div class="container body-content">
        <div class="col-xs-9 hotWeike_c content">
            <form class="playgroundSearchDiv" action="" method="POST">
                <div class="input-group">
                    <div class="input-group-btn">
                        <button type="button" search_type="1" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span>内容</span><span style="margin-left: 4px" class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a onclick="changeSearchField(1)">内容</a></li>
                            <li><a onclick="changeSearchField(2)">作者</a></li>
                            <li><a onclick="changeSearchField(3)">课程</a></li>
                        </ul>
                    </div>
                    <input id="searchInput" type="text" class="form-control" placeholder="搜索" />
                    <span class="input-group-btn">
                        <button class="btn" type="button" onclick="search()"><span class="glyphicon glyphicon-search"></span></button>
                    </span>
                </div>
            </form>
            <h5>当前搜索：<span id="searchingString"></span></h5>
            <div class="js">
                <div class="grid">
                    <c:forEach var="weikeCell" items="${weikeCells}">
                        <div class="grid__item" data-size='${weikeCell.thumbnail_size}'>
                            <div class="thumbnail weikeCell" weikeid="${weikeCell.id}" onclick="showDisplayModalInPG(${weikeCell.id})">
                                <img src="uploadfiles/${weikeCell.thumbnail_url}">
                                <div class="weikeCellDes">
                                    <h3>${weikeCell.title}</h3>
                                    <div class="weikeCellVote">
                                        <h5>${weikeCell.subject}</h5>
                                        <span></span>
                                        <c:choose>
                                            <c:when test="${weikeCell.starred}">
                                                <span><span class="glyphicon glyphicon-heart"></span> <span>${weikeCell.star_num}</span></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span><span class="glyphicon glyphicon-heart-empty"></span> <span>${weikeCell.star_num}</span></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                                <input type="hidden" class="weikeTitle" value="${weikeCell.title}" />
                                <input type="hidden" class="weikeAuthorName" value="${weikeCell.user_name}" />
                                <input type="hidden" class="weikeAuthorAvatar" value="${weikeCell.user_avatar}" />
                                <input type="hidden" class="weikeAuthorId" value="${weikeCell.user_id}" />
                                <input type="hidden" class="weikeSubject" value="${weikeCell.subject}" />
                                <input type="hidden" class="weikePostDate" value="${weikeCell.post_date_string}" />
                                <input type="hidden" class="weikeDescription" value="${weikeCell.description}" />
                                <input type="hidden" class="weikeThumbnailUrl" value="${weikeCell.thumbnail_url}" />
                                <input type="hidden" class="weikeAttachmentUrl" value="${weikeCell.attachment==null?"":weikeCell.attachment}">
                                <input type="hidden" class="weikeFileUrl" value="${weikeCell.file_url}" />
                                <input type="hidden" class="weikeFileType" value="${weikeCell.file_type}" />
                                <input type="hidden" class="weikeCommentNum" value="${weikeCell.comment_num}" />
                                <input type="hidden" class="weikeViewNum" value="${weikeCell.view_num}" />
                                <input type="hidden" class="weikeStarNum" value="${weikeCell.star_num}" />
                                <input type="hidden" class="weikeStarred" value="${weikeCell.starred}" />

                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div style="text-align: center">
                    <button class="btn btn-default" onclick="getMoreWeikeCell(this)" start_id="${weikeCells.size()}">加载更多</button>
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
    <jsp:include page="template/displayModal.jsp" />
    <jsp:include page="template/formhelper.jsp" />
    <jsp:include page="template/footer.jsp" />
</body>
</html>


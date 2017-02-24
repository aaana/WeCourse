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
    <link rel="stylesheet" type="text/css" href="resource/css/pblItem1.css">
    <link rel="stylesheet" type="text/css" href="resource/css/sidebar.css">
    <link rel="stylesheet" type="text/css" href="resource/css/loading.css">
    <link rel="stylesheet" type="text/css" href="resource/css/displayModal1.css">

    <script src="resource/pbl/js/modernizr-custom.js"></script>

    <script type="text/javascript" src="resource/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="resource/Bootstrap/js/bootstrap.min.js"></script>
    <script src="resource/pbl/js/imagesloaded.pkgd.min.js"></script>
    <script src="resource/pbl/js/masonry.pkgd.min.js"></script>
    <script src="resource/pbl/js/classie.js"></script>
    <script src="resource/pbl/js/main.js"></script>

    <link href="resource/video-js/video-js.css" rel="stylesheet" type="text/css">
    <script src="resource/video-js/video.js"></script>

    <script src="resource/js/pblItem.js"></script>
    <script src="resource/js/weikeOpHelper1.js"></script>
    <script src="resource/js/authHelper.js"></script>

    <script>
        function showDisplayModal(weikeId) {
            $("#displayModal .personalPageContentItemComment").remove();

            var query = '.weikeCell[weikeid=' + weikeId +'] ';
            var weikeTitle = $(query + '.weikeTitle').val();
            var weikeAuthorName = $(query + '.weikeAuthorName').val();
            var weikeAuthorAvatar = $(query + '.weikeAuthorAvatar').val();
            var weikeAuthorId = $(query + '.weikeAuthorId').val();
            var weikeSubject = $(query + '.weikeSubject').val();
            var weikePostDate = $(query + '.weikePostDate').val();
            var weikeDescription = $(query + '.weikeDescription').val();
            var weikeThumbnailUrl = $(query + '.weikeThumbnailUrl').val();
            var weikeFileUrl = $(query + '.weikeFileUrl').val();
            var weikeFileType = $(query + '.weikeFileType').val();
            var weikeCommentNum = $(query + '.weikeCommentNum').val();
            var weikeViewNum = $(query + '.weikeViewNum').val();
            var weikeStarNum = $(query + '.weikeStarNum').val();
            var weikeStarred = $(query + '.weikeStarred').val();

            $("#displayModal .weikeDetail").attr("weike_id", weikeId);
            $("#displayModal #titleInDisplayModal").text(weikeTitle);
            $("#displayModal #userNameInDisplayModal").text(weikeAuthorName);
            $("#displayModal .media").attr("user_id", weikeAuthorId);
            $("#displayModal #userAvatarInDisplayModal")[0].src = "resource/img/" + weikeAuthorAvatar;
            $("#displayModal #subjectInDisplayModal").text(weikeSubject);
            $("#displayModal #postDateInDisplayModal").text(weikePostDate);
            $("#displayModal #descriptionInDisplayModal").text(weikeDescription);

            if (weikeFileType == "0") {
                $("#displayModal .thumbnail").html('<img id="picInDisplayModal" src="uploadfiles/' + weikeFileUrl + '">');
            } else if (weikeFileType == "1") {
                $("#displayModal .thumbnail").html(
                        '<video id="videoInDisplayModal" class="video-js vjs-default-skin vjs-big-play-centered" ' +
                        'controls preload="none" width="100%" height="600px" ' +
                        'poster="uploadfiles/' + weikeThumbnailUrl + '">'+
                        '<source type="video/mp4" src="uploadfiles/' + weikeFileUrl + '"/> </video>');
                videojs("videoInDisplayModal", {}, function(){});
            }

            $("#displayModal #commentNumInDisplayModal").text(weikeCommentNum);
            $("#displayModal #viewNumInDisplayModal").text(weikeViewNum);
            $("#displayModal #starNumInDisplayModal").text(weikeStarNum);
            if (weikeStarred == "true") {
                $("#displayModal .glyphicon.glyphicon-heart-empty").removeClass("glyphicon-heart-empty").addClass("glyphicon-heart");
            } else {
                $("#displayModal .glyphicon.glyphicon-heart").removeClass("glyphicon-heart").addClass("glyphicon-heart-empty");
            }

            $('#displayModal').modal('show');
            doWatch(weikeId);
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
                        <div class="grid__item" data-size='${weikeCell.thumbnail_size}'>
                            <div class="thumbnail weikeCell" weikeid="${weikeCell.id}" onclick="showDisplayModal(${weikeCell.id})">
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
                                <input type="hidden" class="weikePostDate" value="${weikeCell.post_date}" />
                                <input type="hidden" class="weikeDescription" value="${weikeCell.description}" />
                                <input type="hidden" class="weikeThumbnailUrl" value="${weikeCell.thumbnail_url}" />
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
    <jsp:include page="template/displayModal.jsp" />
    <jsp:include page="template/formhelper.jsp" />
    <jsp:include page="template/footer.jsp" />
</body>
</html>


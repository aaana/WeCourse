<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tina
  Date: 2/13/17
  Time: 9:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WeiKeHome</title>
    <link rel="stylesheet" type="text/css" href="../resource/Bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../resource/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="../resource/css/personalPage.css">
    <link rel="stylesheet" type="text/css" href="../resource/css/loading.css">
    <script type="text/javascript" src="../resource/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../resource/Bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../resource/js/weikeOpHelper.js"></script>

    <link href="../resource/video-js/video-js.css" rel="stylesheet" type="text/css">
    <script src="../resource/video-js/video.js"></script>

    <script>
        videojs.options.flash.swf = "resource/video-js/video-js.swf";

        function changePage2AllWeike() {
            changePage("allWeike");
        }
        function changePage2FavoriteWeike() {
            changePage("favoriteWeike");
        }
        function changePage2FollowUser() {
            changePage("followUser");
        }
        function changePage(idString) {
            $("#allWeike").hide();
            $("#favoriteWeike").hide();
            $("#followUser").hide();
            $("#" + idString).show();


        }
    </script>

</head>
<body>
    <jsp:include page="template/navbar.jsp" />

    <div class="container body-content">
        <div class="personalPageHead">
            <div class="img img-responsive img-circle" style="background-image: url('../resource/img/${visiting.avatar}');background-position: center;background-size: cover"></div>
            <div user_id="${visiting.id}">
                <span>${visiting.name}</span>
                <c:choose>
                    <c:when test="${user != null && user.id == visiting.id}">

                    </c:when>
                    <c:when test="${visiting.hasfollowed}">
                        <button type="button" class="btn btn-default" onclick="doFollow(this)">已关注</button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-primary" onclick="doFollow(this)">关注</button>
                    </c:otherwise>
                </c:choose>
            </div>            
            
        </div>

        <div>
            <div class="personalPageSidebar col-md-4">
                <jsp:include page="template/loggedPersonalPageSidebar.jsp" />
            </div>

            <div class="personalPageMain col-md-8">
                <ol class="breadcrumb personalPageNav" data-spy="affix" data-offset-top="230">
                    <li class="active"><a onclick="changePage2AllWeike()">微课(${visiting.weike_num})</a></li>
                    <li><a onclick="changePage2FavoriteWeike()">赞过的微课(${visiting.favorite_num})</a></li>
                    <li><a onclick="changePage2FollowUser()">关注的人(${visiting.following_num})</a></li>
                    <span></span>
                    <c:choose>
                        <c:when test="${user != null && user.id == visiting.id}">
                            <button class="btn btn-primary">+<span>发布微课</span></button>
                        </c:when>
                    </c:choose>

                </ol>
                <div id="allWeike" class="personalPageContent">
                    <c:forEach var="weikeCell" items="${weikeCells}">
                        <div class="personalPageContentItem" weike_id="${weikeCell.id}">
                            <input type="hidden" class="fileType" value="${weikeCell.file_type}">
                            <div class="media">
                                <div class="media-left">
                                    <img src="../resource/img/${weikeCell.user_avatar}" class=" img-circle">
                                </div>
                                <div class="media-body">
                                    <p><b>${weikeCell.user_name}</b></p>
                                    <p>${weikeCell.post_date} ${weikeCell.subject}</p>
                                    <h3>${weikeCell.title}</h3>
                                    <p>${weikeCell.description}</p>
                                        <div class="thumbnail" >
                                            <%--<img src="../uploadfiles/${weikeCell.thumbnail_url}">--%>
                                            <c:choose>
                                                <c:when test="${weikeCell.file_type == 0}">
                                                    <a href="../uploadfiles/${weikeCell.file_url}"><img  src="../uploadfiles/${weikeCell.file_url}" /></a>
                                                </c:when>
                                                <c:when test="${weikeCell.file_type == 1}">
                                                    <video id="example_video_1" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="none"
                                                           poster="../uploadfiles/${weikeCell.thumbnail_url}"
                                                           data-setup="{}" width="100%" height="400px">
                                                        <source src="../uploadfiles/${weikeCell.file_url}" type='video/mp4' />
                                                    </video>
                                                </c:when>
                                                <c:otherwise>
                                                    未匹配类型
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    <c:choose>
                                        <c:when test = "${weikeCell.attachment!=null}">
                                            <a href="../uploadfiles/${weikeCell.attachment}" target="_blank">查看附件</a>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="personalPageContentItemBotmBar">
                                <a onclick="showCommentDiv(this)"><span class="glyphicon glyphicon-comment"></span> <span>${weikeCell.comment_num}</span></a>
                                <span>|</span>
                                <c:choose>
                                    <c:when test="${weikeCell.starred == 'true'}">
                                        <a onclick="doFavoriteFromPP(this)"><span class="glyphicon glyphicon-heart"></span> <span>${weikeCell.star_num}</span></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a onclick="doFavoriteFromPP(this)"><span class="glyphicon glyphicon-heart-empty"></span> <span>${weikeCell.star_num}</span></a>
                                    </c:otherwise>
                                </c:choose>
                                <span>|</span>
                                <a onclick="return false"><span class="glyphicon glyphicon glyphicon-eye-open"></span> <span>${weikeCell.view_num}</span></a>
                            </div>
                        </div>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${weikeCells.size() == 0}">
                            <div class="personalPageContentItem" style="text-align: center;padding-bottom: 16px;">
                                用户未发布微课
                            </div>
                        </c:when>
                    </c:choose>
                </div>

                <div id="favoriteWeike" class="personalPageContent">
                    <c:forEach var="weikeCell" items="${favorites}">
                        <div class="personalPageContentItem" weike_id="${weikeCell.id}">
                            <input type="hidden" class="fileType" value="${weikeCell.file_type}">
                            <div class="media">
                                <div class="media-left">
                                    <img src="../resource/img/${weikeCell.user_avatar}" class=" img-circle">
                                </div>
                                <div class="media-body">
                                    <p><b>${weikeCell.user_name}</b></p>
                                    <p>${weikeCell.post_date} ${weikeCell.subject}</p>
                                    <h3>${weikeCell.title}</h3>
                                    <p>${weikeCell.description}</p>
                                    <div class="thumbnail" >
                                        <c:choose>
                                            <c:when test="${weikeCell.file_type == 0}">
                                                <img src="../uploadfiles/${weikeCell.file_url}" />
                                            </c:when>
                                            <c:when test="${weikeCell.file_type == 1}">
                                                <video id="example_video_1" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="none"
                                                       poster="../uploadfiles/${weikeCell.thumbnail_url}"
                                                       data-setup="{}" width="100%" height="400px">
                                                    <source src="../uploadfiles/${weikeCell.file_url}" type='video/mp4' />
                                                </video>
                                            </c:when>
                                            <c:otherwise>
                                                未匹配类型
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <c:choose>
                                    <c:when test = "${weikeCell.attachment!=null}">
                                        <a href="../uploadfiles/${weikeCell.attachment}" target="_blank">查看附件</a>
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="personalPageContentItemBotmBar">
                                <a onclick="showCommentDiv(this)"><span class="glyphicon glyphicon-comment"></span> <span>${weikeCell.comment_num}</span></a>
                                <span>|</span>
                                <c:choose>
                                    <c:when test="${weikeCell.starred == 'true'}">
                                        <a onclick="doFavoriteFromPP(this)"><span class="glyphicon glyphicon-heart"></span> <span>${weikeCell.star_num}</span></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a onclick="doFavoriteFromPP(this)"><span class="glyphicon glyphicon-heart-empty"></span> <span>${weikeCell.star_num}</span></a>
                                    </c:otherwise>
                                </c:choose>
                                <span>|</span>
                                <a onclick="return false"><span class="glyphicon glyphicon glyphicon-eye-open"></span> <span>${weikeCell.view_num}</span></a>
                            </div>
                        </div>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${favorites.size() == 0}">
                            <div class="personalPageContentItem" style="text-align: center;padding-bottom: 16px;">
                                用户未收藏微课
                            </div>
                        </c:when>
                    </c:choose>
                </div>

                <div id="followUser" class="personalPageContent">
                    <c:choose>
                        <c:when test="${followings.size() == 0}">
                            <div class="personalPageContentItem" style="text-align: center;padding-bottom: 16px;">
                                用户未关注其他用户
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="personalPageContentItem followList">
                                <c:forEach var="follow" items="${followings}">
                                    <div class="media">
                                        <div class="media-left">
                                            <img src="../resource/img/${follow.avatar}" class=" img-circle">
                                        </div>
                                        <div class="media-body">
                                            <div>
                                                <div>
                                                    <span><b>${follow.name}</b></span>
                                                    <span>邮箱：${follow.email}</span>
                                                </div>
                                                <div></div>
                                                <c:choose>
                                                    <c:when test="${user != null && user.id == follow.id}">

                                                    </c:when>
                                                    <c:when test="${follow.hasfollowed}">
                                                        <div user_id="${follow.id}">
                                                            <button type="button" class="btn btn-default" onclick="doFollow(this)">已关注</button>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div user_id="${follow.id}">
                                                            <<button type="button" class="btn btn-primary" onclick="doFollow(this)">关注</button>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="template/formhelper.jsp" />
    <jsp:include page="template/footer.jsp" />
</body>
</html>

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
    <link rel="stylesheet" type="text/css" href="../resource/css/personalPage1.css">
    <script type="text/javascript" src="../resource/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../resource/Bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../resource/js/comment.js"></script>

    <link href="../resource/video-js/video-js.css" rel="stylesheet" type="text/css">
    <script src="../resource/video-js/video.js"></script>

    <script>
        $(document).ready(function () {
            function follow(t) {
                $(t).parent().attr('id');

                $(t).text('已关注');
            }

        });

        videojs.options.flash.swf = "resource/video-js/video-js.swf";

        $(window).scroll(function(){
            var min_height = 300;
            var s = $(window).scrollTop();
            //The gotoTop fidein when users scroll to a certain position
            if( s > min_height){
                $("#gotoTop").fadeIn(200);
            }else{
                $("#gotoTop").fadeOut(200);
            };
        });

    </script>

</head>
<body>
    <jsp:include page="template/navbar.jsp" />

    <div class="container body-content">
        <div class="personalPageHead">
            <div class="img img-responsive img-circle" style="background-image: url('../resource/img/${user.avatar}');background-position: center;background-size: cover"></div>
            <div id="${visiting.id}">
                <span>${visiting.name}</span>
                <button class="btn btn-primary" onclick="follow(this)">关注</button>
            </div>            
            
        </div>

        <div>
            <div class="personalPageSidebar col-md-4">
                <jsp:include page="template/loggedPersonalPageSidebar.jsp" />
            </div>

            <div class="personalPageMain col-md-8">
                <ol class="breadcrumb personalPageNav" data-spy="affix" data-offset-top="230">
                    <li class="active">微课</li>
                    <li><a href="#">赞过的微课</a></li>
                    <li><a href="#">关注的人</a></li>
                    <span></span>
                    <button class="btn btn-primary">+<span>发布微课</span></button>
                </ol>
                <div class="personalPageContent">
                    <c:forEach var="weikeCell" items="${weikeCells}">
                        <div class="personalPageContentItem" weike_id="${weikeCell.id}">
                            <input type="hidden" class="fileType" value="${weikeCell.file_type}">
                            <div class="media">
                                <div class="media-left">
                                    <img src="http://img0.pconline.com.cn/pconline/1408/11/5254676_1407293-2_thumb.jpg" class=" img-circle">
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
                                <a onclick="showCommentDiv(this)">评论 <span>${weikeCell.comment_num}</span></a>
                                <span>|</span>
                                <a onclick="likeWeike(this)">点赞 <span>${weikeCell.star_num}</span></a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <a id="gotoTop" onclick="gotoTop()"><span class="glyphicon glyphicon-chevron-up"></span></a>

    <jsp:include page="template/footer.jsp" />
</body>
<script>
    function gotoTop() {
        $('html,body').animate({scrollTop:0},400);
    }
</script>
</html>

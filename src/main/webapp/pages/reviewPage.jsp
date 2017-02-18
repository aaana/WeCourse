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
    <script src="resource/pbl/js/modernizr-custom.js"></script>
    <script type="text/javascript" src="resource/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="resource/Bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="resource/js/comment.js"></script>
    <script>
        var follow =  function(t) {
            $(t).parent().attr('id');

            $(t).text('已关注');
        }
    </script>

</head>
<body>
    <jsp:include page="template/navbar.jsp" />

    <div class="container body-content">
        <div class="col-xs-9 hotWeike_c content">
            <div class="personalPageContentItem messageList">
                <div class="media">
                    <div class="media-left">
                        <img src="http://img0.pconline.com.cn/pconline/1408/11/5254676_1407293-2_thumb.jpg" class=" img-circle">
                    </div>
                    <div class="media-body">
                        <div>
                            <div>
                                <p><b>用户名</b> <small>2016-06-20</small></p>
                                <p>他关注了你</p>
                            </div>
                            <div></div>
                            <div>
                                <button class="btn btn-primary">他的主页</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="media">
                    <div class="media-left">
                        <img src="http://img0.pconline.com.cn/pconline/1408/11/5254676_1407293-2_thumb.jpg" class=" img-circle">
                    </div>
                    <div class="media-body">
                        <div>
                            <div>
                                <span><b>用户名</b> <small>2016-06-20</small></span>
                                <p>他回复了你的评论：<span>英语写作小技巧</span></p>
                            </div>
                            <div></div>
                            <div>
                                <button class="btn btn-primary">查看详情</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="media">
                    <div class="media-left">
                        <img src="http://img0.pconline.com.cn/pconline/1408/11/5254676_1407293-2_thumb.jpg" class=" img-circle">
                    </div>
                    <div class="media-body">
                        <div>
                            <div>
                                <p><b>用户名</b> <small>2016-06-20</small></p>
                                <p>他评论了你的微课： <span>英语写作小技巧</span></p>
                            </div>
                            <div></div>
                            <div>
                                <button class="btn">已读</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="media">
                    <div class="media-left">
                        <img src="http://img0.pconline.com.cn/pconline/1408/11/5254676_1407293-2_thumb.jpg" class=" img-circle">
                    </div>
                    <div class="media-body">
                        <div>
                            <div>
                                <p><b>用户名</b> <small>2016-06-20</small></p>
                                <p>他收藏了你的微课： <span>英语写作小技巧</span></p>
                            </div>
                            <div></div>
                            <div>
                                <button class="btn">已读</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <button class="modalBtn" type="button" data-toggle="modal" data-target="#myModal"></button>
        </div>
        <div class="col-xs-3">
            <div id="sidebar" data-spy="affix">
                <jsp:include page="template/loggedPlaygoundSidbar.jsp" />
            </div>
        </div>
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">微课详情</h4>
                    </div>
                    <div class="modal-body">
                        <div class="weikeDetail" id="100">
                            <div>
                                <div>
                                    <h3>英语写作小技巧</h3>
                                    <p>2016-06-16 英语</p>
                                </div>
                                <div class="media">
                                    <div class="media-body">
                                        <span><b>用户名</b></span>
                                    </div>
                                    <div class="media-right">
                                        <img src="http://img0.pconline.com.cn/pconline/1408/11/5254676_1407293-2_thumb.jpg" class=" img-circle">
                                    </div>
                                </div>
                            </div>
                            <p>英语写作有很多小技巧，有些技巧能使你的文章更有条理，有些能使文章更生动英语写作有很多小技巧，
                                有些技巧能使你的文章更有条理，有些能使文章更生动英语写作有很多小技巧，
                                有些技巧能使你的文章更有条理，有些能使文章更生动</p>
                            <div class="thumbnail">
                                <img src="resource/img/8.jpg">
                            </div>
                            <div class="modalWeikeItemBotmBar">
                                <a onclick="showCommentDiv(this)">评论 <span>1</span></a>
                                <span>|</span>
                                <a onclick="likeWeike(this)">点赞 <span>2</span></a>
                            </div>
                        </div>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>

    <jsp:include page="template/footer.jsp" />

</body>
</html>



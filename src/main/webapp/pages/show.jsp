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
    <title>Title</title>
    <link href="resource/video-js/video-js.css" rel="stylesheet" type="text/css">
    <script src="resource/video-js/video.js"></script>
    <script>
        videojs.options.flash.swf = "resource/video-js/video-js.swf";
    </script>
</head>
<body>
<c:forEach var="file" items="${files}">
    <c:out value="${file.id}"/><br/>
    <c:choose>
        <c:when test="${file.type == 0}">
            <img src="uploadfiles/${file.url}" />
        </c:when>
        <c:when test="${file.type == 1}">
            <video id="example_video_1" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="none"
                   poster="http://video-js.zencoder.com/oceans-clip.png"
                   data-setup="{}">
                <source src="uploadfiles/${file.url}" type='video/mp4' />
            </video>
        </c:when>
        <c:otherwise>
            未匹配类型
        </c:otherwise>
    </c:choose>
    <br/>
</c:forEach>
</body>
</html>



<%--
  Created by IntelliJ IDEA.
  User: tina
  Date: 2/17/17
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="sidebar" data-spy="affix">
    <div class="sidebarUserInfo">
        <div>
            <a href="/user/${user.id}">
                <div style="width: 80px; height: 80px; background-image: url('../resource/img/${user.avatar}');background-position: center;background-size: cover" class="img-responsive img-circle">
                </div>
            </a>
        </div>
        <a href="#"><span><c:out value="${user.name}"/></span></a>
        <div>
            <a href="#"><span>${user.following_num}</span><span>关注</span></a>
            <a href="#"><span>${user.favorite_num}</span><span>收藏</span></a>
            <a href="#"><span>${user.weike_num}</span><span>微课</span></a>
        </div>
    </div>
    <div class="sidebarPublishBtn">
        <button onclick="window.location.href='./upload'">发布微课</button>
    </div>
</div>

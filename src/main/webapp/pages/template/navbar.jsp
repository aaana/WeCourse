<%--
  Created by IntelliJ IDEA.
  User: tina
  Date: 2/17/17
  Time: 3:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <c:choose>
                <c:when test="${user != null}">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </c:when>
            </c:choose>
            <a class="navbar-brand" href="/">微课</a>
        </div>
        <div class="navbar-collapse collapse">

            <c:choose>
                <c:when test="${user != null}">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                消息
                                <c:choose>
                                    <c:when test="${messageNum[0] > 0}">
                                        <span class="navBarMessageCountAll"></span>
                                    </c:when>
                                </c:choose>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="/reminder?page_num=1">
                                        <span>有人收藏我的微课</span>
                                        <c:choose>
                                            <c:when test="${messageNum[1] > 0}">
                                                <span class="navBarMessageCount">${messageNum[1]}</span>
                                            </c:when>
                                        </c:choose>
                                    </a>
                                </li>
                                <li>
                                    <a href="/reminder?page_num=2">
                                        <span>有人关注我</span>
                                        <c:choose>
                                            <c:when test="${messageNum[2] > 0}">
                                                <span class="navBarMessageCount">${messageNum[2]}</span>
                                            </c:when>
                                        </c:choose>
                                    </a>
                                </li>
                                <li>
                                    <a href="/reminder?page_num=3">
                                        <span>有人评论我的微课</span>
                                        <c:choose>
                                            <c:when test="${messageNum[3] > 0}">
                                                <span class="navBarMessageCount">${messageNum[3]}</span>
                                            </c:when>
                                        </c:choose>
                                    </a>
                                </li>
                                <li>
                                    <a href="/reminder?page_num=4">
                                        <span>有人回复我</span>
                                        <c:choose>
                                            <c:when test="${messageNum[4] > 0}">
                                                <span class="navBarMessageCount">${messageNum[4]}</span>
                                            </c:when>
                                        </c:choose>
                                    </a>
                                </li>
                                <li role="separator" class="divider"></li>
                                <li>
                                    <a href="/reminder?page_num=0">
                                        <span>查看全部消息</span>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" href="Manage/Index">${user.name}</a>
                            <ul class="dropdown-menu">
                                <li><a href="/user/${user.id}" style="text-align: center; text-align: -webkit-center;">
                                    <div style="width: 80px; height: 80px; background-image: url('../resource/img/${user.avatar}');background-position: center;background-size: cover" class="img-responsive img-circle">
                                    </div>
                                </a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="/user/${user.id}?page_num=2">我的关注</a></li>
                                <li><a href="/user/${user.id}?page_num=3">我的收藏</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="/logout">注销</a></li>
                            </ul>
                        </li>
                    </ul>

                </c:when>
            </c:choose>
        </div>
    </div>
</div>
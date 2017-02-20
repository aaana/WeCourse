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
            <ul class="nav navbar-nav">
                <li><a href="#">热门</a></li>
                <li><a href="#">广场</a></li>
            </ul>

            <c:choose>
                <c:when test="${user != null}">

                    <ul class="nav navbar-nav navbar-right">

                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                消息<span class="navBarMessageCountAll"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#">
                                        有人关注我<span class="navBarMessageCount">1</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        有人评论我<span class="navBarMessageCount">3</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        有人回复我
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" href="Manage/Index">用户名</a>
                            <ul class="dropdown-menu">
                                <li><a href="#">个人主页</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#">我的关注</a></li>
                                <li><a href="#">我的收藏</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="javascript:document.getElementById('logoutForm').submit()">注销</a></li>
                            </ul>
                        </li>
                    </ul>

                </c:when>
            </c:choose>
        </div>
    </div>
</div>
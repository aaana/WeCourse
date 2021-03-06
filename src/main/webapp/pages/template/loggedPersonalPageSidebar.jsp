<%--
  Created by IntelliJ IDEA.
  User: tina
  Date: 2/17/17
  Time: 4:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="personalPageInfo" style="position: relative">
    <span>个人信息</span>
    <c:choose>
        <c:when test="${user != null && user.id == visiting.id}">
            <a href="../editUserInfo" style="position: absolute;right: 12px; top:10px;"><span class="glyphicon glyphicon-edit"></span></a>
        </c:when>
    </c:choose>
    <div style="border-top: none">
        <span>学校</span>
        <span>${visiting.school != "" ? visiting.school : "未填写"}</span>
    </div>
    <div>
        <span>邮箱</span>
        <span>${visiting.email}</span>
    </div>
    <div>
        <span>描述</span>
        <span>${visiting.introduction != "" ? visiting.introduction : "未填写"}</span>
    </div>
</div>
<div class="personalPageHotWeike" style="display: none">
    <span>热门微课</span>
</div>

<div class="personalPageCommonFollow" style="display: none" data-spy="affix" data-offset-top="668">
    <span>共同关注</span>
    <div>
    </div>
</div>


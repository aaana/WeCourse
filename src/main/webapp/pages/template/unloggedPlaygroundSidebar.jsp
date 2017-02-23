<%--
  Created by IntelliJ IDEA.
  User: tina
  Date: 2/17/17
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="sidebar" data-spy="affix">
    <div class="loginDiv">
        <h4 class="form-login-heading">账户登陆</h4>
        <form id="loginForm">
            <input type="email" class="form-control" name="inputEmail" placeholder="邮箱">
            <input type="password" name="inputPassword" class="form-control" placeholder="密码">

            <button type="button" id="login_button" onclick="login()">登陆</button>
            <p>没有账号?<a data-toggle="modal" data-target="#signupModal">先去注册</a></p>
        </form>
    </div>
</div>

<!-- 注册窗口 -->
<div class="modal fade" id="signupModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">账户注册</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="signupForm" action="signupUser"  method="post">
                    <div class="form-group">
                        <label for="emailInput" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-9">
                            <input type="email" class="form-control" name="email" id="emailInput" placeholder="邮箱账号">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="nameInput" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="name" id="nameInput" placeholder="用户名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">身份</label>
                        <div class="col-sm-9">
                            <div class="radio" id="typeRadio">
                                <label class="radio-inline">
                                    <input type="radio" name="usertype" id="inlineRadio1" value="0" checked> 教师
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="usertype" id="inlineRadio2" value="1"> 学生
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="passwordInput" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-9">
                            <input type="password" class="form-control" name="password" id="passwordInput" placeholder="密码">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="verifyPdInput" class="col-sm-2 control-label">确认密码</label>
                        <div class="col-sm-9">
                            <input type="password" class="form-control" id="verifyPdInput" placeholder="确认密码">
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="signup()">注册</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

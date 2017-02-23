<%--
  Created by IntelliJ IDEA.
  User: tina
  Date: 2/19/17
  Time: 6:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="displayModal" tabindex="-1" role="dialog" aria-labelledby="displayModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title" id="titleInDisplayModal"></h3>
            </div>
            <div class="modal-body">
                <div class="weikeDetail" weike_id="0">
                    <div>
                        <div>
                            <p><span id="postDateInDisplayModal"></span> <span id="subjectInDisplayModal"></span></p>
                            <p id="descriptionInDisplayModal"></p>
                        </div>
                        <div class="media" user_id="0" onclick="gotoPersonalPage(this)">
                            <div class="media-body">
                                <span><b id="userNameInDisplayModal"></b></span>
                            </div>
                            <div class="media-right">
                                <img id="userAvatarInDisplayModal" class=" img-circle">
                            </div>
                        </div>
                    </div>
                    <div class="thumbnail"></div>
                    <div class="modalWeikeItemBotmBar">
                        <a onclick="showCommentDiv(this)"><span class="glyphicon glyphicon-comment"></span> <span id="commentNumInDisplayModal"></span></a>
                        <span>|</span>
                        <a onclick="doFavorite(this)"><span class="glyphicon glyphicon-heart-empty"></span> <span id="starNumInDisplayModal"></span></a>
                        <span>|</span>
                        <a onclick="return false"><span class="glyphicon glyphicon glyphicon-eye-open"></span> <span id="viewNumInDisplayModal"></span></a>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
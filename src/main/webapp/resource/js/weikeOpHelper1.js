/**
 * Created by tina on 2/17/17.
 */

// comment
var showCommentDiv = function (t) {
    if($(t).parent().next().size() != 0) {
        return false;
    }
    var weikeId = $(t).parent().parent().attr('weike_id');
    $(t).parent().parent().append(
        '<div class="personalPageContentItemComment" weike_id="' + weikeId + '">' +
        '<div></div>' +
        '<div class="weikeCellComment input-group">' +
        '<input type="text" class="form-control" placeholder="我来评论">' +
        '<span class="input-group-btn">' +
        '<button class="btn btn-default" type="button" onclick="makeComment2weike(this)">评论</button>' +
        '</span>' +
        '</div>' +
        '<ul class="media-list weikeCellCommentList">' +
        '</ul>' +
        '<a onclick="hideCommentListDiv(this)"><span class="glyphicon glyphicon-chevron-up"></span></a>' +
        '</div>');
    var commentListDivNode = $(t).parent().next().children('.weikeCellCommentList');
    var commentList = [];
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/comment",
        error:function(data){
            $('#uploadModal').modal('hide');
            showHint("出错,请重试");
        },
        success:function(data){
            $('#uploadModal').modal('hide');
            if(data.comments != null) {
                commentList = transCommentFormat(data.comments);
                initCommentDiv(commentList, commentListDivNode);
            }
        }
    });
};
var initCommentDiv = function (commentList, parentNode) {
    for (var index in commentList) {
        parentNode.append(initCommentTemplate(commentList[index]));

        if (commentList[index].commentList.length > 0) {
            initCommentDiv(commentList[index].commentList, parentNode.children('li[comment_id=' + commentList[index].id + ']:last-child').children('.media-body'));
        }
    }
};
var initCommentTemplate = function (comment) {
    return '<li class="media" comment_id="' + comment.id + '">' +
        '<div class="media-left">' +
        '<a href="/user/' + comment.user.id + '">' +
        '<img class="media-object" src="' + comment.user.imgSrc + '">' +
        '</a>' +
        '</div>' +
        '<div class="media-body">' +
        '<h5 class="media-heading">' + comment.user.name + ' <small>' + comment.time + '</small></h5>' +
        '<h6>' + comment.content + '</h6>' +
        '<div class="weikeCellCommentReply">' +
        '<a onclick="showCommentInput(this)">回复</a>' +
        '</div>' +
        '</div>' +
        '</li>'
};
var hideCommentListDiv = function (t) {
    $(t).parent().remove();
};
var makeComment2weike = function (t) {
    $('#uploadModal').modal('show');
    var weikeId = $(t).parent().parent().parent().attr('weike_id');
    var parentId = -1;
    var content = $(t).parent().prev().val();
    $.ajax({
        data:{
            weikeId: weikeId,
            parentId: parentId,
            content: content
        },
        type:"post",
        dataType: 'json',
        url:"/makeComment",
        error:function(data){
            $('#uploadModal').modal('hide');
            showHint("出错,请重试");
        },
        success:function(data){
            $('#uploadModal').modal('hide');
            if(data.isLogged && data.commentCell != null) {
                var comment = {
                    'id': data.commentCell.id,
                    'user': {
                        'id': data.commentCell.publisher_id,
                        'name': data.commentCell.publisher_name,
                        'imgSrc': '/resource/img/' + data.commentCell.publisher_avatar
                    },
                    'time': transTimeStamp2String(data.commentCell.publish_time),
                    'content': data.commentCell.content,
                    'commentList': []
                };
                $(t).parent().prev().val('');
                $(t).parents('.weikeCellComment').next().append(initCommentTemplate(comment));
                var commentNum = $(t).closest('.personalPageContentItemComment').prev().children("a:first-child").children("span:last-child").text();
                $(t).closest('.personalPageContentItemComment').prev().children("a:first-child").children("span:last-child").text(parseInt(commentNum) + 1);
            } else if(!data.isLogged){
                showHint("请先登录");
            } else {
                showHint("出错,请重试");
            }
        }
    });
};
var showCommentInput = function (t) {
    if($(t).parent().next().hasClass("weikeCellComment")) {
        return false;
    }
    $(t).parent().after(
        '<div class="weikeCellComment input-group">' +
        '<input type="text" class="form-control" placeholder="我来评论">' +
        '<span class="input-group-btn">' +
        '<button class="btn btn-default" type="button" onclick="makeComment2comment(this)">评论</button>' +
        '<button class="btn btn-default" type="button" onclick="hideComment2comment(this)">取消</button>' +
        '</span>' +
        '</div>');
};
var makeComment2comment = function (t) {
    $('#uploadModal').modal('show');
    var weikeId = $(t).closest(".personalPageContentItemComment").attr("weike_id");
    var parentId = $($(t).parents('.media')[0]).attr('comment_id');
    var content = $(t).parent().prev().val();
    $.ajax({
        data:{
            weikeId: weikeId,
            parentId: parentId,
            content: content
        },
        type:"post",
        dataType: 'json',
        url:"/makeComment",
        error:function(data){
            $('#uploadModal').modal('hide');
            showHint("出错,请重试");
        },
        success:function(data){
            $('#uploadModal').modal('hide');
            if(data.isLogged && data.commentCell != null) {
                var comment = {
                    'id': data.commentCell.id,
                    'user': {
                        'id': data.commentCell.publisher_id,
                        'name': data.commentCell.publisher_name,
                        'imgSrc': '/resource/img/' + data.commentCell.publisher_avatar
                    },
                    'time': transTimeStamp2String(data.commentCell.publish_time),
                    'content': data.commentCell.content,
                    'commentList': []
                };
                $(t).parent().prev().val('');
                $($(t).parents('.media-body')[0]).append(initCommentTemplate(comment));
                
                var commentNum = $(t).closest('.personalPageContentItemComment').prev().children("a:first-child").children("span:last-child").text();
                $(t).closest('.personalPageContentItemComment').prev().children("a:first-child").children("span:last-child").text(parseInt(commentNum) + 1);

                hideComment2comment(t);

            } else if(!data.isLogged){
                showHint("请先登录");
            } else {
                showHint("出错,请重试");
            }
        }
    });
};
var hideComment2comment = function (t) {
    $(t).parents('.weikeCellComment').remove();
};
var transCommentFormat = function (comments) {
    var commentsTemp = [];
    var commentList = [];
    for (i in comments) {
        comment = {
            'id': comments[i].id,
            'user': {
                'id': comments[i].publisher_id,
                'name': comments[i].publisher_name,
                'imgSrc': '/resource/img/' + comments[i].publisher_avatar
            },
            'time': transTimeStamp2String(comments[i].publish_time),
            'content': comments[i].content,
            'parentId': comments[i].parent_id,
            'commentList': []
        };
        if (comments[i].parent_id == -1) {
            commentList.push(comment);
        } else {
            commentsTemp.push(comment);
        }
    }

    while (commentsTemp.length != 0) {
        var temp = [];
        for (var i = 0; i < commentsTemp.length; i++) {
            var result = placeCommentRightPlace(commentsTemp[i], commentList);
            if (!result.success) {
                temp.push(commentsTemp[i]);
            } else {
                commentList = result.commentList;
            }
        }
        commentsTemp = temp;
    }
    return commentList;
};
var placeCommentRightPlace = function (comment, list) {
    for (i in list) {
        if (list[i].id == comment.parentId) {
            list[i].commentList.push(comment);
            return {
                success: true,
                commentList: list
            };
        }
        if (list[i].commentList.length != 0) {
            var result = placeCommentRightPlace(comment, list[i].commentList);
            if (result.success) {
                list[i].commentList = result.commentList;
                return {
                    success: true,
                    commentList: list
                };
            }
        }
    }
    return {
        success: false,
        commentList: list
    };
};

// favorite
var doFavorite = function (t) {
    if ($(t).children("span:first-child").hasClass("glyphicon-heart-empty")) {
        favorite(t);
    } else if ($(t).children("span:first-child").hasClass("glyphicon-heart")) {
        unfavorite(t)
    }
};
var favorite = function (t) {
    var weikeId = $(t).parent().parent().attr('weike_id');
    
    $('#uploadModal').modal('show');
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/favorite",
        error:function(data){
            $('#uploadModal').modal('hide');
            showHint("出错,请重试");
        },
        success:function(data){
            $('#uploadModal').modal('hide');
            if(data.isLogged && data.result) {
                $(t).children('span:last-child').text(parseInt($(t).children('span:last-child').text()) + 1);
                $(t).children('span:first-child').removeClass('glyphicon-heart-empty').addClass('glyphicon-heart');

                $(".weikeCell[weikeid = "+ weikeId+"] .glyphicon.glyphicon-heart-empty").removeClass('glyphicon-heart-empty').addClass('glyphicon-heart');
                $(".weikeCell[weikeid = "+ weikeId+"] .glyphicon.glyphicon-heart").next("span").text(parseInt($(t).children('span:last-child').text()));
                $(".weikeCell[weikeid = "+ weikeId+"] .weikeStarNum").val(parseInt($(t).children('span:last-child').text()));
                $(".weikeCell[weikeid = "+ weikeId+"] .weikeStarred").val("true");
            } else if(!data.isLogged){
                showHint("请先登录");
            } else {
                showHint("出错,请重试")
            }
        }
    });
};
var unfavorite = function (t) {
    var weikeId = $(t).parent().parent().attr('weike_id');

    $('#uploadModal').modal('show');
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/unfavorite",
        error:function(data){
            $('#uploadModal').modal('hide');
            showHint("出错,请重试");
        },
        success:function(data){
            $('#uploadModal').modal('hide');
            if(data.isLogged && data.result) {
                $(t).children('span:last-child').text(parseInt($(t).children('span:last-child').text()) - 1);
                $(t).children('span:first-child').removeClass('glyphicon-heart').addClass('glyphicon-heart-empty');

                $(".weikeCell[weikeid = "+ weikeId+"] .glyphicon.glyphicon-heart").removeClass('glyphicon-heart').addClass('glyphicon-heart-empty');
                $(".weikeCell[weikeid = "+ weikeId+"] .glyphicon.glyphicon-heart-empty").next("span").text(parseInt($(t).children('span:last-child').text()));
                $(".weikeCell[weikeid = "+ weikeId+"] .weikeStarNum").val(parseInt($(t).children('span:last-child').text()));
                $(".weikeCell[weikeid = "+ weikeId+"] .weikeStarred").val("false");
            } else if(!data.isLogged){
                showHint("请先登录");
            } else {
                showHint("出错,请重试")
            }
        }
    });

};

var doFavoriteFromPP = function (t) {
    if ($(t).children("span:first-child").hasClass("glyphicon-heart-empty")) {
        favoriteFromPP(t);
    } else if ($(t).children("span:first-child").hasClass("glyphicon-heart")) {
        unfavoriteFromPP(t)
    }
};
var favoriteFromPP = function (t) {
    var weikeId = $(t).parent().parent().attr('weike_id');

    $('#uploadModal').modal('show');
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/favorite",
        error:function(data){
            $('#uploadModal').modal('hide');
            showHint("出错,请重试");
        },
        success:function(data){
            $('#uploadModal').modal('hide');
            if(data.isLogged && data.result) {
                $(t).children('span:last-child').text(parseInt($(t).children('span:last-child').text()) + 1);
                $(t).children('span:first-child').removeClass('glyphicon-heart-empty').addClass('glyphicon-heart');

                } else if(!data.isLogged){
                showHint("请先登录");
            } else {
                showHint("出错,请重试")
            }
        }
    });

};
var unfavoriteFromPP = function (t) {
    var weikeId = $(t).parent().parent().attr('weike_id');

    $('#uploadModal').modal('show');
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/unfavorite",
        error:function(data){
            $('#uploadModal').modal('hide');
            showHint("出错,请重试");
        },
        success:function(data){
            $('#uploadModal').modal('hide');
            if(data.isLogged && data.result) {
                $(t).children('span:last-child').text(parseInt($(t).children('span:last-child').text()) - 1);
                $(t).children('span:first-child').removeClass('glyphicon-heart').addClass('glyphicon-heart-empty');

            } else if(!data.isLogged){
                showHint("请先登录");
            } else {
                showHint("出错,请重试")
            }
        }
    });

};


// follow
var doFollow = function (t) {
    if ($(t).hasClass("btn-primary")) {
        follow(t);
    } else if ($(t).hasClass("btn-default")) {
        unfollow(t)
    }
};
var follow = function (t) {
    var user_id = $(t).parent().attr('user_id');

    $('#uploadModal').modal('show');
    $.ajax({
        data:{
            user_id: user_id
        },
        type:"post",
        dataType: 'json',
        url:"/follow",
        error:function(data){
            $('#uploadModal').modal('hide');
            showHint("出错,请重试");
        },
        success:function(data){
            $('#uploadModal').modal('hide');
            if(data.isLogged && data.result) {
                $(t).removeClass("btn-primary").addClass("btn-default");
                $(t).text("已关注");
            } else if(!data.isLogged){
                showHint("请先登录");
            } else {
                showHint("出错,请重试")
            }
        }
    });
};
var unfollow = function (t) {
    var user_id = $(t).parent().attr('user_id');

    $('#uploadModal').modal('show');
    $.ajax({
        data:{
            user_id: user_id
        },
        type:"post",
        dataType: 'json',
        url:"/unfollow",
        error:function(data){
            $('#uploadModal').modal('hide');
            showHint("出错,请重试");
        },
        success:function(data){
            $('#uploadModal').modal('hide');
            if(data.isLogged && data.result) {
                $(t).removeClass("btn-default").addClass("btn-primary");
                $(t).text("关注");
            } else if(!data.isLogged){
                showHint("请先登录");
            } else {
                showHint("出错,请重试")
            }
        }
    });
};


// others
var gotoPersonalPage = function (t) {
    var userId = $(t).attr('user_id');
    window.location.href = '/user/' + userId;
};
var doWatch = function (weikeId) {
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/watchWeike"
    });
    
};

function showHint(string) {
    $("#reviewModal .modal-body").html(string);
    $('#reviewModal').modal('show');
}
function transTimeStamp2String (time){
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1;
    var date = datetime.getDate();
    var hour = datetime.getHours();
    var minute = datetime.getMinutes();
    var second = datetime.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
};
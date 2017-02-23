/**
 * Created by tina on 2/17/17.
 */

// comment
var showCommentDiv = function (t) {

    var weikeId = $(t).parent().parent().attr('weike_id');
    // get weike Comment list

    // for example
    // required info
    var commentList = [];
    // var commentList = [
    //     {
    //         'id': 101,
    //         'user': {
    //             'name': '用户名1',
    //             'imgSrc': 'resource/img/8.jpg'
    //         },
    //         'time': '2016-6-17 13:50',
    //         'content': '评论内容评论内容评论内容评论内容评论内容评论内容评论内容',
    //         'commentList': [
    //             {
    //                 'id': 102,
    //                 'user': {
    //                     'name': '用户名2',
    //                     'imgSrc': 'resource/img/8.jpg'
    //                 },
    //                 'time': '2016-6-17 13:50',
    //                 'content': '评论内容评论内容评论内容评论内容评论内容评论内容评论内容',
    //                 'commentList': [
    //                     {
    //                         'id': 103,
    //                         'user': {
    //                             'name': '用户名3',
    //                             'imgSrc': 'resource/img/8.jpg'
    //                         },
    //                         'time': '2016-6-17 13:50',
    //                         'content': '评论内容评论内容评论内容评论内容评论内容评论内容评论内容',
    //                         'commentList': []
    //                     }
    //                 ]
    //             }
    //         ]
    //     },
    //     {
    //         'id': 102,
    //         'user': {
    //             'name': '用户名2',
    //             'imgSrc': 'resource/img/8.jpg'
    //         },
    //         'time': '2016-6-17 13:50',
    //         'content': '评论内容评论内容评论内容评论内容评论内容评论内容评论内容',
    //         'commentList': []
    //     },
    //     {
    //         'id': 103,
    //         'user': {
    //             'name': '用户名3',
    //             'imgSrc': 'resource/img/8.jpg'
    //         },
    //         'time': '2016-6-17 13:50',
    //         'content': '评论内容评论内容评论内容评论内容评论内容评论内容评论内容',
    //         'commentList': []
    //     }
    //
    // ];

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
        '</div>');
    var commentListDivNode = $(t).parent().next().children('.weikeCellCommentList');
    initCommentDiv(commentList, commentListDivNode);
    commentListDivNode.append('<a onclick="hideCommentListDiv(this)"><span class="glyphicon glyphicon-chevron-up"></span></a>');
};
var initCommentDiv = function (commentList, parentNode) {
    for (var index in commentList) {
        parentNode.append(initCommentTemplate(commentList[index]));

        if (commentList[index].commentList.length > 0) {
            initCommentDiv(commentList[index].commentList, parentNode.children('#' + commentList[index].id + ':last-child').children('.media-body'));
        }
        console.log(index + ' ' + commentList[index].user.name + ' ' + commentList[index].commentList.length);
    }
};
var initCommentTemplate = function (comment) {
    return '<li class="media" id="' + comment.id + '">' +
        '<div class="media-left">' +
        '<a href="#">' +
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
    $(t).parent().parent().remove();
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
            if(data.isLogged && data.comment != null) {
                var comment = {
                    'id': data.comment.id,
                    'user': {
                        'name': data.comment.publisher_name,
                        'imgSrc': '/resource/img/' + data.comment.publisher_avatar
                    },
                    'time': data.comment.publish_time,
                    'content': data.comment.content,
                    'commentList': []
                };
                $(t).parent().prev().val('');
                $(t).parents('.weikeCellComment').next().children('a').before(initCommentTemplate(comment));
                
            } else if(!data.isLogged){
                showHint("请先登录");
            } else {
                showHint("出错,请重试");
            }
        }
    });
};
var showCommentInput = function (t) {
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
    var commentId = $($(t).parents('.media')[0]).attr('id');
    var content = $(t).parent().prev().val();


    // post comment
    // if success, return the new comment
    var time = new Date();
    var data = {
        'comment': {
            'id': 103,
            'user': {
                'name': '用户名3',
                'imgSrc': 'resource/img/8.jpg'
            },
            'time': time.getYear() + '-' + time.getMonth() + '-' + time.getDay() + ' ' + time.getHours() + ':' + time.getMinutes(),
            'content': content,
            'commentList': []
        }
    }

    var comment = data.comment;
    $(t).parent().prev().val('');
    $($(t).parents('.media-body')[0]).append(initCommentTemplate(comment));
    hideComment2comment(t);

};
var hideComment2comment = function (t) {
    $(t).parents('.weikeCellComment').remove();

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
    window.location.href = '/user/' + userId
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
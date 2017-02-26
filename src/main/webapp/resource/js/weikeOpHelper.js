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
            showHint("出错,请重试", "确定");
        },
        success:function(data){
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
    showUploadModal("请稍等");
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
            hideUploadModal();
            showHint("出错,请重试", "确定");
        },
        success:function(data){
            hideUploadModal();
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
                showHint("请先登录", "确定");
            } else {
                showHint("出错,请重试", "确定");
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
    showUploadModal("请稍等");
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
            hideUploadModal()
            showHint("出错,请重试", "确定");
        },
        success:function(data){
            hideUploadModal();
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
                showHint("请先登录", "确定");
            } else {
                showHint("出错,请重试", "确定");
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

    showUploadModal("请稍等");
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/favorite",
        error:function(data){
            hideUploadModal();
            showHint("出错,请重试", "确定");
        },
        success:function(data){
            hideUploadModal();
            if(data.isLogged && data.result) {
                $(t).children('span:last-child').text(parseInt($(t).children('span:last-child').text()) + 1);
                $(t).children('span:first-child').removeClass('glyphicon-heart-empty').addClass('glyphicon-heart');

                $(".weikeCell[weikeid = "+ weikeId+"] .glyphicon.glyphicon-heart-empty").removeClass('glyphicon-heart-empty').addClass('glyphicon-heart');
                $(".weikeCell[weikeid = "+ weikeId+"] .glyphicon.glyphicon-heart").next("span").text(parseInt($(t).children('span:last-child').text()));
                $(".weikeCell[weikeid = "+ weikeId+"] .weikeStarNum").val(parseInt($(t).children('span:last-child').text()));
                $(".weikeCell[weikeid = "+ weikeId+"] .weikeStarred").val("true");
            } else if(!data.isLogged){
                showHint("请先登录", "确定");
            } else {
                showHint("出错,请重试", "确定");
            }
        }
    });
};
var unfavorite = function (t) {
    var weikeId = $(t).parent().parent().attr('weike_id');
    showUploadModal("请稍等");
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/unfavorite",
        error:function(data){
            hideUploadModal();
            showHint("出错,请重试", "确定");
        },
        success:function(data){
            hideUploadModal();
            if(data.isLogged && data.result) {
                $(t).children('span:last-child').text(parseInt($(t).children('span:last-child').text()) - 1);
                $(t).children('span:first-child').removeClass('glyphicon-heart').addClass('glyphicon-heart-empty');

                $(".weikeCell[weikeid = "+ weikeId+"] .glyphicon.glyphicon-heart").removeClass('glyphicon-heart').addClass('glyphicon-heart-empty');
                $(".weikeCell[weikeid = "+ weikeId+"] .glyphicon.glyphicon-heart-empty").next("span").text(parseInt($(t).children('span:last-child').text()));
                $(".weikeCell[weikeid = "+ weikeId+"] .weikeStarNum").val(parseInt($(t).children('span:last-child').text()));
                $(".weikeCell[weikeid = "+ weikeId+"] .weikeStarred").val("false");
            } else if(!data.isLogged){
                showHint("请先登录", "确定");
            } else {
                showHint("出错,请重试", "确定");
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

    showUploadModal("请稍等");
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/favorite",
        error:function(data){
            hideUploadModal();
            showHint("出错,请重试", "确定");
        },
        success:function(data){
            hideUploadModal();
            if(data.isLogged && data.result) {
                $(t).children('span:last-child').text(parseInt($(t).children('span:last-child').text()) + 1);
                $(t).children('span:first-child').removeClass('glyphicon-heart-empty').addClass('glyphicon-heart');

                } else if(!data.isLogged){
                showHint("请先登录", "确定");
            } else {
                showHint("出错,请重试", "确定")
            }
        }
    });

};
var unfavoriteFromPP = function (t) {
    var weikeId = $(t).parent().parent().attr('weike_id');

    showUploadModal("请稍等");
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/unfavorite",
        error:function(data){
            hideUploadModal();
            showHint("出错,请重试", "确定");
        },
        success:function(data){
            hideUploadModal();
            if(data.isLogged && data.result) {
                $(t).children('span:last-child').text(parseInt($(t).children('span:last-child').text()) - 1);
                $(t).children('span:first-child').removeClass('glyphicon-heart').addClass('glyphicon-heart-empty');

            } else if(!data.isLogged){
                showHint("请先登录", "确定");
            } else {
                showHint("出错,请重试", "确定");
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

    showUploadModal("请稍等");
    $.ajax({
        data:{
            user_id: user_id
        },
        type:"post",
        dataType: 'json',
        url:"/follow",
        error:function(data){
            hideUploadModal();
            showHint("出错,请重试", "确定");
        },
        success:function(data){
            hideUploadModal();
            if(data.isLogged && data.result) {
                $(t).removeClass("btn-primary").addClass("btn-default");
                $(t).text("已关注");
            } else if(!data.isLogged){
                showHint("请先登录", "确定");
            } else {
                showHint("出错,请重试", "确定");
            }
        }
    });
};
var unfollow = function (t) {
    var user_id = $(t).parent().attr('user_id');

    showUploadModal("请稍等");
    $.ajax({
        data:{
            user_id: user_id
        },
        type:"post",
        dataType: 'json',
        url:"/unfollow",
        error:function(data){
            hideUploadModal();
            showHint("出错,请重试", "确定");
        },
        success:function(data){
            hideUploadModal();
            if(data.isLogged && data.result) {
                $(t).removeClass("btn-default").addClass("btn-primary");
                $(t).text("关注");
            } else if(!data.isLogged){
                showHint("请先登录", "确定");
            } else {
                showHint("出错,请重试", "确定");
            }
        }
    });
};

// load more weike
var getMoreWeikeCell = function (t) {
    // start id
    var start_id = $(t).attr('start_id');

    showUploadModal("请稍等");
    $.ajax({
        data:{
            startId: start_id
        },
        type:"get",
        dataType: 'json',
        url:"/moreWeike",
        error:function(data){
            hideUploadModal();
            showHint("出错,请重试", "确定");
        },
        success:function(data){
            hideUploadModal();
            $(t).attr("start_id", parseInt(start_id) + data.weikeCells.length);
            if (!data.hasMoreWeike) {
                $(t).text("已显示全部结果");
                $(t).attr("disabled", "disabled");
            }
            var parentNode = $(".js .grid");
            weikeCells = data.weikeCells;
            for (var i in weikeCells) {
                parentNode.append(initWeikeTemplate(weikeCells[i]));
            }
            new GridFx(document.querySelector('.grid'));
        }
    });
};
var initWeikeTemplate = function(weikeCell) {
    attachment = weikeCell.attachment==null?"":weikeCell.attachment;
    star = "";
    if (weikeCell.starred) {
        star = '<span><span class="glyphicon glyphicon-heart"></span> <span>' + weikeCell.star_num + '</span></span>';
    } else {
        star = '<span><span class="glyphicon glyphicon-heart-empty"></span> <span>' + weikeCell.star_num + '</span></span>';
    }
    return '<div class="grid__item" data-size=' + weikeCell.thumbnail_size + '> ' +
        '<div class="thumbnail weikeCell" weikeid="' + weikeCell.id + '" onclick="showDisplayModalInPG(' + weikeCell.id + ')">' +
        '<img src="uploadfiles/' + weikeCell.thumbnail_url + '">' +
        '<div class="weikeCellDes">' +
        '<h3>' + weikeCell.title + '</h3>' +
        '<div class="weikeCellVote">' +
        '<h5>' + weikeCell.subject + '</h5>' +
        '<span></span>' +
        star +
        '</div>' +
        '</div>' +
        '<input type="hidden" class="weikeTitle" value="' + weikeCell.title  + '" />' +
        '<input type="hidden" class="weikeAuthorName" value="' + weikeCell.user_name + '" />' +
        '<input type="hidden" class="weikeAuthorAvatar" value="' + weikeCell.user_avatar + '" />' +
        '<input type="hidden" class="weikeAuthorId" value="' + weikeCell.user_id + '" />' +
        '<input type="hidden" class="weikeSubject" value="' + weikeCell.subject + '" />' +
        '<input type="hidden" class="weikePostDate" value="' + weikeCell.post_date + '" />' +
        '<input type="hidden" class="weikeDescription" value="' + weikeCell.description  + '" />' +
        '<input type="hidden" class="weikeThumbnailUrl" value="' + weikeCell.thumbnail_url  + '" />' +
        '<input type="hidden" class="weikeAttachmentUrl" value="' + attachment  + '">' +
        '<input type="hidden" class="weikeFileUrl" value="' + weikeCell.file_url  + '" />' +
        '<input type="hidden" class="weikeFileType" value="' + weikeCell.file_type  + '" />' +
        '<input type="hidden" class="weikeCommentNum" value="' + weikeCell.comment_num  + '" />' +
        '<input type="hidden" class="weikeViewNum" value="' + weikeCell.view_num  + '" />' +
        '<input type="hidden" class="weikeStarNum" value="' + weikeCell.star_num  + '" />' +
        '<input type="hidden" class="weikeStarred" value="' + weikeCell.starred  + '" />' +
        '</div>' +
        '</div>';
};

// load common followings
var getCommonFollowings = function (userId) {
    $.ajax({
        data:{
            userId: userId
        },
        type:"get",
        dataType: 'json',
        url:"/user/commonFollowings",
        error:function(data){
            console.log("加载共同关注失败");
        },
        success:function(data){
            if (data.showCF && data.commonFollowings != null && data.commonFollowings.length != 0) {
                var parentNode = $(".personalPageCommonFollow > div");
                var commonFollowings = data.commonFollowings;
                for (var i in commonFollowings) {
                    parentNode.append(initCommonFollowingsTemplate(commonFollowings[i]));
                }
                $(".personalPageCommonFollow").css("display", "");
            }
        }
    });
};
var initCommonFollowingsTemplate = function (follow) {
    return '<div onclick="gotoPersonalPage(this)" user_id="' + follow.id + '">' +
        '<img src="../resource/img/' + follow.avatar + '" class=" img-circle">' +
        '<span>' + follow.name + '</span>' +
        '</div>';
};

// load hot weikes
var getHotWeikes = function (userId) {
    $.ajax({
        data:{
            userId: userId
        },
        type:"get",
        dataType: 'json',
        url:"/user/hotWeikes",
        error:function(data){
            console.log("加载热门微课失败");
        },
        success:function(data){
            if (data.hotWeikes != null && data.hotWeikes.length != 0) {
                var parentNode = $(".personalPageHotWeike");
                var hotWeikes = data.hotWeikes;
                for (var i in hotWeikes) {
                    parentNode.append(initHotWeikeTemplate(hotWeikes[i]));
                }
                parentNode.css("display", "");
            }
        }
    });
};
var initHotWeikeTemplate = function (weikeCell) {
    var time = transTimeStamp2String(weikeCell.post_date);
    var star = weikeCell.starred ? '<span class="glyphicon glyphicon-heart">' : '<span class="glyphicon glyphicon-heart-empty">';
    return '<div weike_id="' + weikeCell.id+ '" onclick="showWeikeDetail(this, null, doAfterShowWeikeDetail)">' +
        '<p>' + weikeCell.title + '</p>' +
        '<p><span>' + time + ' ' + weikeCell.subject + '</span>' +
        star + '</span> <span>' + weikeCell.star_num + '</span>' +
        '<span class="glyphicon glyphicon glyphicon-eye-open"></span> <span>' + weikeCell.view_num + '</span></p>' +
        '</div>';
};
var doAfterShowWeikeDetail = function (t) {
    var view_numEle = $(t).children("p:last-child").children("span:last-child");
    view_numEle.text(parseInt(view_numEle.text()) + 1);
};

// show display modal
var showWeikeDetail = function (t, preFunction, afterFunction) {
    showUploadModal("请稍等");
    
    if (preFunction != null) {
        preFunction(t);
    }
    
    var weikeId = $(t).attr("weike_id");
    $.ajax({
        data:{
            weikeId: weikeId
        },
        type:"post",
        dataType: 'json',
        url:"/detailWeike",
        error:function(data){
            hideUploadModal();
            showHint("出错,请重试", "确定");
        },
        success:function(data){
            hideUploadModal();
            showDisplayModal(data.weikeCell);
            var commentListDivNode = $('#displayModal .weikeCellCommentList');
            commentList = transCommentFormat(data.commentCells);
            initCommentDiv(commentList, commentListDivNode);
            
            if(afterFunction != null) {
                afterFunction(t);
            }
        }
    });
};
var showDisplayModal = function (weikeCell) {
    $("#displayModal .personalPageContentItemComment").remove();
    
    $("#displayModal .weikeDetail").attr("weike_id", weikeCell.id);
    $("#displayModal #titleInDisplayModal").text(weikeCell.title);
    $("#displayModal #userNameInDisplayModal").text(weikeCell.user_name);
    $("#displayModal .media").attr("user_id", weikeCell.user_id);
    $("#displayModal #userAvatarInDisplayModal")[0].src = "/resource/img/" + weikeCell.user_avatar;
    $("#displayModal #subjectInDisplayModal").text(weikeCell.subject);
    $("#displayModal #postDateInDisplayModal").text(transTimeStamp2String(weikeCell.post_date));
    $("#displayModal #descriptionInDisplayModal").text(weikeCell.description);

    if (weikeCell.file_type == 0) {
        $("#displayModal .thumbnail").html('<img id="picInDisplayModal" src="/uploadfiles/' + weikeCell.file_url + '">');
    } else if (weikeCell.file_type == 1) {
        $("#displayModal .thumbnail").html(
            '<video id="videoInDisplayModal" class="video-js vjs-default-skin vjs-big-play-centered" ' +
            'controls preload="none" width="100%" height="600px" ' +
            'poster="/uploadfiles/' + weikeCell.thumbnail_url + '">'+
            '<source type="video/mp4" src="/uploadfiles/' + weikeCell.file_url + '"/> </video>');
        videojs("videoInDisplayModal", {}, function(){});
    }

    $("#displayModal #commentNumInDisplayModal").text(weikeCell.comment_num);
    $("#displayModal #viewNumInDisplayModal").text(weikeCell.view_num);
    $("#displayModal #starNumInDisplayModal").text(weikeCell.star_num);
    if (weikeCell.starred) {
        $("#displayModal .glyphicon.glyphicon-heart-empty").removeClass("glyphicon-heart-empty").addClass("glyphicon-heart");
    } else {
        $("#displayModal .glyphicon.glyphicon-heart").removeClass("glyphicon-heart").addClass("glyphicon-heart-empty");
    }

    $("#displayModal .weikeDetail").append(
        '<div class="personalPageContentItemComment" weike_id="' + weikeCell.id + '">' +
        '<div></div>' +
        '<div class="weikeCellComment input-group">' +
        '<input type="text" class="form-control" placeholder="我来评论">' +
        '<span class="input-group-btn">' +
        '<button class="btn btn-default" type="button" onclick="makeComment2weike(this)">评论</button>' +
        '</span>' +
        '</div>' +
        '<ul class="media-list weikeCellCommentList">' +
        '</ul>' +
        // '<a onclick="hideCommentListDiv(this)"><span class="glyphicon glyphicon-chevron-up"></span></a>' +
        '</div>');


    $('#displayModal').modal('show');
    doWatch(weikeCell.id);
};
function showDisplayModalInPG(weikeId) {
    $("#displayModal .personalPageContentItemComment").remove();

    var query = '.weikeCell[weikeid=' + weikeId +'] ';
    var weikeTitle = $(query + '.weikeTitle').val();
    var weikeAuthorName = $(query + '.weikeAuthorName').val();
    var weikeAuthorAvatar = $(query + '.weikeAuthorAvatar').val();
    var weikeAuthorId = $(query + '.weikeAuthorId').val();
    var weikeSubject = $(query + '.weikeSubject').val();
    var weikePostDate = $(query + '.weikePostDate').val();
    var weikeDescription = $(query + '.weikeDescription').val();
    var weikeThumbnailUrl = $(query + '.weikeThumbnailUrl').val();
    var weikeAttachmentUrl = $(query + '.weikeAttachmentUrl').val();
    var weikeFileUrl = $(query + '.weikeFileUrl').val();
    var weikeFileType = $(query + '.weikeFileType').val();
    var weikeCommentNum = $(query + '.weikeCommentNum').val();
    var weikeViewNum = $(query + '.weikeViewNum').val();
    var weikeStarNum = $(query + '.weikeStarNum').val();
    var weikeStarred = $(query + '.weikeStarred').val();

    $("#displayModal .weikeDetail").attr("weike_id", weikeId);
    $("#displayModal #titleInDisplayModal").text(weikeTitle);
    $("#displayModal #userNameInDisplayModal").text(weikeAuthorName);
    $("#displayModal .media").attr("user_id", weikeAuthorId);
    $("#displayModal #userAvatarInDisplayModal")[0].src = "resource/img/" + weikeAuthorAvatar;
    $("#displayModal #subjectInDisplayModal").text(weikeSubject);
    $("#displayModal #postDateInDisplayModal").text(weikePostDate);
    $("#displayModal #descriptionInDisplayModal").text(weikeDescription);
//            alert(weikeAttachmentUrl!="");
    if(weikeAttachmentUrl!=""){
        $("#displayModal #attachment").show();
        $("#displayModal #attachment").text("查看附件");
        $("#displayModal #attachment")[0].href="../uploadfiles/"+weikeAttachmentUrl;
    }else{
        $("#displayModal #attachment").hide();
    }


    if (weikeFileType == "0") {
        $("#displayModal .thumbnail").html('<img id="picInDisplayModal" src="uploadfiles/' + weikeFileUrl + '">');
    } else if (weikeFileType == "1") {
        $("#displayModal .thumbnail").html(
            '<video id="videoInDisplayModal" class="video-js vjs-default-skin vjs-big-play-centered" ' +
            'controls preload="none" width="100%" height="600px" ' +
            'poster="uploadfiles/' + weikeThumbnailUrl + '">'+
            '<source type="video/mp4" src="uploadfiles/' + weikeFileUrl + '"/> </video>');
        videojs("videoInDisplayModal", {}, function(){});
    }

    $("#displayModal #commentNumInDisplayModal").text(weikeCommentNum);
    $("#displayModal #viewNumInDisplayModal").text(weikeViewNum);
    $("#displayModal #starNumInDisplayModal").text(weikeStarNum);
    if (weikeStarred == "true") {
        $("#displayModal .glyphicon.glyphicon-heart-empty").removeClass("glyphicon-heart-empty").addClass("glyphicon-heart");
    } else {
        $("#displayModal .glyphicon.glyphicon-heart").removeClass("glyphicon-heart").addClass("glyphicon-heart-empty");
    }

    $('#displayModal').modal('show');
    doWatch(weikeId);
}

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

function showHint(string, string2) {
    if(string2 == "") {
        string2 = "继续填写";
    }
    $("#reviewModal .modal-body").html(string);
    $("#reviewModal #reviewModalBtn").text(string2);
    
    $('#reviewModal').modal('show');
}
function showUploadModal(string) {
    $('#uploadModal #uploadModalHint').text(string);
    $('#uploadModal').modal('show');
}
function hideUploadModal() {
    $('#uploadModal #uploadModalHint').text("正在上传");
    $('#uploadModal').modal('hide');
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
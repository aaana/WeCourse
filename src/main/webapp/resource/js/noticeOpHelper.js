/**
 * Created by tina on 2/24/17.
 */
var allNotices = [];
var displayingNotices = [];

var showNotices = function (t) {
    var pageNum = $("#pageNum").val();
    var unread = $("#unread").val();
    if (unread == "true") {
        $("#unreadCheckBox")[0].checked = true;
    }
    if (pageNum == "1") {
        $("#favoriteCheckBox")[0].checked = true;
    } else if (pageNum == "2") {
        $("#followCheckBox")[0].checked = true;
    } else if (pageNum == "3") {
        $("#commentCheckBox")[0].checked = true;
    } else if (pageNum == "4") {
        $("#replyCheckBox")[0].checked = true;
    }

    var parentNode = $(".personalPageContentItem.messageList");
    // var noticeCells = [];
    $.ajax({
        data:{},
        type:"post",
        dataType: 'json',
        url:"/notices",
        error:function(data){
            parentNode.append("<p>出错,请刷新</p>");
        },
        success:function(data){
            if(data.noticeCells != null) {
                allNotices = data.noticeCells;
                displayingNotices = loadNotices();
                initNoticeDiv(displayingNotices, parentNode);
            } else {
                parentNode.append("<p>没有消息</p>");
            }
        }
    });
};

var initNoticeTemplate = function (notice) {
    var btnType = notice.hasread ? 'btn-defalut':'btn-primary';
    if (notice.notice_type == 1) {
        return '<div class="media" notice_id="' + notice.id + '" hasread="' + notice.hasread + '">' +
            '<div class="media-left">' +
            '<img src="/resource/img/' + notice.sender_avatar + '" user_id=' + notice.sender_id + ' onclick="gotoPP(this)" class=" img-circle">' +
            '</div>' +
            '<div class="media-body">' +
            '<div>' +
            '<div>' +
            '<p><b>' + notice.sender_name + '</b> <small>' + transTimeStamp2String(notice.notice_time) + '</small></p>' +
            '<p>他收藏了你的微课： <span>' + notice.weike_title + '</span></p>' +
            '</div>' +
            '<div></div>' +
            '<div>' +
            '<button weike_id="' + notice.target_id + '" onclick="showWeikeDetail(this)" class="btn ' + btnType + '">查看微课</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
    } else if (notice.notice_type == 2) {
        return '<div class="media" notice_id="' + notice.id + '" hasread="' + notice.hasread + '">' +
            '<div class="media-left">' +
            '<img src="/resource/img/' + notice.sender_avatar + '" user_id=' + notice.sender_id + ' onclick="gotoPP(this)" class=" img-circle">' +
            '</div>' +
            '<div class="media-body">' +
            '<div>' +
            '<div>' +
            '<p><b>' + notice.sender_name + '</b> <small>' + transTimeStamp2String(notice.notice_time) + '</small></p>' +
            '<p>他关注了你</p>' +
            '</div>' +
            '<div></div>' +
            '<div>' +
            '<button user_id=' + notice.sender_id + ' onclick="gotoPP(this)" class="btn ' + btnType + '">他的主页</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
    } else if (notice.notice_type == 3) {
        return '<div class="media" notice_id="' + notice.id + '" hasread="' + notice.hasread + '">' +
            '<div class="media-left">' +
            '<img src="/resource/img/' + notice.sender_avatar + '" user_id=' + notice.sender_id + ' onclick="gotoPP(this)" class=" img-circle">' +
            '</div>' +
            '<div class="media-body">' +
            '<div>' +
            '<div>' +
            '<p><b>' + notice.sender_name + '</b> <small>' + transTimeStamp2String(notice.notice_time) + '</small></p>' +
            '<p>他对你的微课： <span>' + notice.weike_title + '</span></p>' +
            '<p>进行了评论： <span>' + notice.comment_content + '</span></p>' +
            '</div>' +
            '<div></div>' +
            '<div>' +
            '<button weike_id="' + notice.comment_weike_id + '" onclick="showWeikeDetail(this)" class="btn ' + btnType + '"">查看微课</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
    } else if (notice.notice_type == 4) {
        return '<div class="media" notice_id="' + notice.id + '" hasread="' + notice.hasread + '">' +
            '<div class="media-left">' +
            '<img src="/resource/img/' + notice.sender_avatar + '" user_id=' + notice.sender_id + ' onclick="gotoPP(this)" class=" img-circle">' +
            '</div>' +
            '<div class="media-body">' +
            '<div>' +
            '<div>' +
            '<p><b>' + notice.sender_name + '</b> <small>' + transTimeStamp2String(notice.notice_time) + '</small></p>' +
            '<p>他对你的评论： <span>' + notice.comment_target + '</span></p>' +
            '<p>进行了回复： <span>' + notice.comment_content + '</span></p>' +
            '</div>' +
            '<div></div>' +
            '<div>' +
            '<button weike_id="' + notice.comment_weike_id + '" onclick="showWeikeDetail(this)" class="btn ' + btnType + '"">查看微课</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
    }
};

var initNoticeDiv = function (noticeList, parentNode) {
    for (var index in noticeList) {
        parentNode.append(initNoticeTemplate(noticeList[index]));
    }
};

var readNotice = function (t) {
    var hasread = $(t).closest(".media").attr("hasread");
    if (hasread == "false") {
        var noticeId = $(t).closest(".media").attr("notice_id");
        $.ajax({
            data:{
                noticeId: noticeId
            },
            type:"post",
            dataType: 'json',
            url:"/watchNotice"
        });
        changeNoticeState(noticeId);
    }
};

var changeNoticeState = function(noticeId) {
    for (var i in allNotices) {
        if (allNotices[i].id == noticeId) {
            allNotices[i].hasread = true;
            break;
        }
    }
};

var gotoPP = function (t) {
    readNotice(t);
    gotoPersonalPage(t);
};

var showWeikeDetail = function (t) {
    showUploadModal("请稍等");

    readNotice(t);

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
        }
    });
};

var showDisplayModal = function (weikeCell) {
    $("#displayModal .weikeDetail").attr("weike_id", weikeCell.id);
    $("#displayModal #titleInDisplayModal").text(weikeCell.title);
    $("#displayModal #userNameInDisplayModal").text(weikeCell.user_name);
    $("#displayModal .media").attr("user_id", weikeCell.user_id);
    $("#displayModal #userAvatarInDisplayModal")[0].src = "resource/img/" + weikeCell.user_avatar;
    $("#displayModal #subjectInDisplayModal").text(weikeCell.subject);
    $("#displayModal #postDateInDisplayModal").text(transTimeStamp2String(weikeCell.post_date));
    $("#displayModal #descriptionInDisplayModal").text(weikeCell.description);

    if (weikeCell.file_type == 0) {
        $("#displayModal .thumbnail").html('<img id="picInDisplayModal" src="uploadfiles/' + weikeCell.file_url + '">');
    } else if (weikeCell.file_type == 1) {
        $("#displayModal .thumbnail").html(
            '<video id="videoInDisplayModal" class="video-js vjs-default-skin vjs-big-play-centered" ' +
            'controls preload="none" width="100%" height="600px" ' +
            'poster="uploadfiles/' + weikeCell.thumbnail_url + '">'+
            '<source type="video/mp4" src="uploadfiles/' + weikeCell.file_url + '"/> </video>');
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

function loadNotices() {
    var showUnread = $("#unreadCheckBox:checked").length == 0 ? false : true;
    var showFavorite = $("#favoriteCheckBox:checked").length == 0 ? false : true;
    var showFollow = $("#followCheckBox:checked").length == 0 ? false : true;
    var showComment = $("#commentCheckBox:checked").length == 0 ? false : true;
    var showReply = $("#replyCheckBox:checked").length == 0 ? false : true;
    var kindNum = [];
    if (showFavorite) {kindNum.push(1);}
    if (showFollow) {kindNum.push(2);}
    if (showComment) {kindNum.push(3);}
    if (showReply) {kindNum.push(4);}
    if (!showFollow && !showFavorite && !showComment && !showReply) {kindNum = [1, 2, 3, 4];}
    return filtrateNoticeList(showUnread, kindNum);
}

var filtrateNoticeList = function (showUnread, kindNum) {
    var selectedNotices = [];
    for (var i in allNotices) {
        if (showUnread && allNotices[i].hasread) {
            continue;
        }
        var index = $.inArray(allNotices[i].notice_type, kindNum);
        if (index < 0) {
            continue;
        }
        selectedNotices.push(allNotices[i])
    }
    return selectedNotices;
};

var reloadNotices = function () {
    displayingNotices = loadNotices();
    var parentNode = $(".personalPageContentItem.messageList");
    parentNode.empty();
    if (displayingNotices.length == 0) {
        parentNode.append("<p>没有相关消息</p>");
    } else {
        initNoticeDiv(displayingNotices, parentNode);
    }
};
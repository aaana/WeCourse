/**
 * Created by tina on 2/17/17.
 */

$(document).ready(function() {
    var showCommentDiv = function (t) {

        var weikeId = $(t).parent().parent().attr('id');
        // get weike Comment list

        // for example
        // required info 
        var commentList = [
            {
                'id': 101,
                'user': {
                    'name': '用户名1',
                    'imgSrc': 'resource/img/8.jpg'
                },
                'time': '2016-6-17 13:50',
                'content': '评论内容评论内容评论内容评论内容评论内容评论内容评论内容',
                'commentList': [
                    {
                        'id': 102,
                        'user': {
                            'name': '用户名2',
                            'imgSrc': 'resource/img/8.jpg'
                        },
                        'time': '2016-6-17 13:50',
                        'content': '评论内容评论内容评论内容评论内容评论内容评论内容评论内容',
                        'commentList': [
                            {
                                'id': 103,
                                'user': {
                                    'name': '用户名3',
                                    'imgSrc': 'resource/img/8.jpg'
                                },
                                'time': '2016-6-17 13:50',
                                'content': '评论内容评论内容评论内容评论内容评论内容评论内容评论内容',
                                'commentList': []
                            }
                        ]
                    }
                ]
            },
            {
                'id': 102,
                'user': {
                    'name': '用户名2',
                    'imgSrc': 'resource/img/8.jpg'
                },
                'time': '2016-6-17 13:50',
                'content': '评论内容评论内容评论内容评论内容评论内容评论内容评论内容',
                'commentList': []
            },
            {
                'id': 103,
                'user': {
                    'name': '用户名3',
                    'imgSrc': 'resource/img/8.jpg'
                },
                'time': '2016-6-17 13:50',
                'content': '评论内容评论内容评论内容评论内容评论内容评论内容评论内容',
                'commentList': []
            }

        ];

        $(t).parent().parent().append(
            '<div class="personalPageContentItemComment">' +
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

    }

    var initCommentDiv = function (commentList, parentNode) {
        for (var index in commentList) {
            parentNode.append(initCommentTemplate(commentList[index]));

            if (commentList[index].commentList.length > 0) {
                initCommentDiv(commentList[index].commentList, parentNode.children('#' + commentList[index].id + ':last-child').children('.media-body'));
            }
            console.log(index + ' ' + commentList[index].user.name + ' ' + commentList[index].commentList.length);
        }
    }

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
    }

    var likeWeike = function (t) {
        var weikeId = $(t).parent().parent().attr('id');

        // post like action

        $(t).children('span').text(parseInt($(t).children('span').text()) + 1);
        $(t).css('color', 'white');
        $(t).attr('onclick', '');

    }

    var hideCommentListDiv = function (t) {
        $(t).parent().parent().remove();
    }

    var makeComment2weike = function (t) {

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
                'time': time.getFullYear() + '-' + (time.getMonth() + 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes(),
                'content': content,
                'commentList': []
            }
        }

        var comment = data.comment;
        $(t).parent().prev().val('');
        $(t).parents('.weikeCellComment').next().children('a').before(initCommentTemplate(comment));

    }

    var showCommentInput = function (t) {
        $(t).parent().after(
            '<div class="weikeCellComment input-group">' +
            '<input type="text" class="form-control" placeholder="我来评论">' +
            '<span class="input-group-btn">' +
            '<button class="btn btn-default" type="button" onclick="makeComment2comment(this)">评论</button>' +
            '<button class="btn btn-default" type="button" onclick="hideComment2comment(this)">取消</button>' +
            '</span>' +
            '</div>');
    }
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

    }
    var hideComment2comment = function (t) {
        $(t).parents('.weikeCellComment').remove();

    }

});
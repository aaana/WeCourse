/**
 * Created by tina on 2/26/17.
 */
var search = function () {
    var field = $(".playgroundSearchDiv .btn-default").attr("search_type");
    var searchString = $("#searchInput").val();
    
    if (searchString == "") {
        showHint("关键词为空", "确定");
        return false;
    }

    showUploadModal("请稍等");
    $.ajax({
        data: {
            field: field,
            searchString: searchString
        },
        type: 'post',
        dataType: 'json',
        url: '/searchWeike',
        error: function (data) {
            hideUploadModal();
            showHint("出错,请重试", "确定");
        },
        success: function (data) {
            hideUploadModal();
            btn = $(".js > div:last-child > .btn");
            btn.attr("start_id", data.weikeCells.length);
            if (!data.hasMoreWeike) {
                btn.text("已显示全部结果");
                btn.attr("disabled", "disabled");
            } else {
                btn.text("加载更多");
                btn.attr("disabled", false);
            }
            if(data.weikeCells.length == 0) {
                showHint("没有相符的结果,请更换关键词", "确定");
            } else {
                var parentNode = $(".js .grid");
                parentNode.empty();
                weikeCells = data.weikeCells;
                for (var i in weikeCells) {
                    parentNode.append(initWeikeTemplate(weikeCells[i]));
                }
                new GridFx(document.querySelector('.grid'));
            }
        }
    });
};

var changeSearchField = function (searchType) {
    if (searchType == 1) {
        $(".playgroundSearchDiv .btn-default span:first-child").text("内容");
        $(".playgroundSearchDiv .btn-default").attr("search_type", 1);
    } else if (searchType == 2) {
        $(".playgroundSearchDiv .btn-default span:first-child").text("作者");
        $(".playgroundSearchDiv .btn-default").attr("search_type", 2);
    } else if (searchType == 3) {
        $(".playgroundSearchDiv .btn-default span:first-child").text("课程");
        $(".playgroundSearchDiv .btn-default").attr("search_type", 3);
    }
};
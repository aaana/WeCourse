<%--
  Created by IntelliJ IDEA.
  User: tina
  Date: 2/13/17
  Time: 4:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>WeiKeHome</title>
    <link rel="stylesheet" type="text/css" href="resource/Bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="resource/css/weike.css">
    <link rel="stylesheet" type="text/css" href="resource/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="resource/css/upload.css">
    <link rel="stylesheet" type="text/css" href="resource/css/loading.css">
    <script type="text/javascript" src="resource/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="resource/Bootstrap/js/bootstrap.js"></script>

</head>
<body>
<jsp:include page="template/navbar.jsp" />
<jsp:include page="template/formhelper.jsp" />

<div class="container body-content">
    <div class="content">
        <div class="col-sm-12">
            <h2 class="col-sm-offset-2 col-sm-9">发布微课</h2>
        </div>
        <form class="form-horizontal" action="submitFile" enctype="multipart/form-data" method="post">
            <div class="form-group">
                <label for="title" class="col-sm-2 control-label">标题</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="title" id="title" placeholder="微课标题">
                </div>
            </div>

            <div class="form-group">
                <label for="subject" class="col-sm-2 control-label">科目</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="subject" id="subject" placeholder="微课所属科目">
                </div>
            </div>

            <div class="form-group">
                <label for="description" class="col-sm-2 control-label">微课描述</label>
                <div class="col-sm-9">
                    <textarea class="form-control" name="description" id="description" rows="3" maxlength="100" placeholder="微课描述（限100字数）"></textarea>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">缩略图</label>
                <div class="col-sm-9">
                    <%--<button type="button" class="inputBtn btn btn-default">添加</button>--%>
                    <button id="thumbnailBtn" type="button" class="btn btn-primary" onclick="thumbnailInput.click();event.target.blur();">+</button>
                    <input id="thumbnailInput" style="display:none" type="file" name="thumbnail" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">类型</label>
                <div class="col-sm-9">
                    <div class="radio" id="typeRadio">
                        <label class="radio-inline">
                            <input type="radio" name="mediatype" id="inlineRadio1" value="0" checked> 图片
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="mediatype" id="inlineRadio2" value="1"> 视频
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="mediatype" id="inlineRadio3" value="2"> 文件
                        </label>
                    </div>
                </div>
            </div>

            <div class="form-group" id="publishImgDiv">
                <label class="col-sm-2 control-label">图片</label>
                <div class="col-sm-9">
                    <button id="pictureBtn" type="button" class="btn btn-primary" onclick="pictureInput.click();event.target.blur();">+</button>
                    <input id="pictureInput" style="display:none" type="file" name="picture" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" />
                </div>
            </div>
            <div class="form-group" id="publishVideoDiv">
                <label class="col-sm-2 control-label">视频</label>
                <div class="col-sm-9">
                    <button id="videoBtn" type="button" class="btn btn-primary" onclick="videoInput.click();event.target.blur();">+</button>
                    <input id="videoInput" style="display:none" type="file" name="video" accept="video/mp4,video/mpeg,video/3gpp" />
                </div>
            </div>
            <div class="form-group" id="attachmentDiv">
                <label class="col-sm-2 control-label">添加附件</label>
                <div class="col-sm-9">
                    <button id="attachmentBtn" type="button" class="btn btn-primary" onclick="attachmentInput.click();event.target.blur();">+</button>
                    <input id="attachmentInput" style="display:none" type="file" name="attachment" />
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-9">
                    <button type="button" data-toggle="modal" data-target="#cancelModal" class="btn btn-default">取消</button>
                    <button type="button" onclick="submitForm()" class="btn btn-default">发布</button>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>

<script>
    $(document).ready(function() {

        $('#typeRadio input[type = radio]').change(function () {
            var type = $('input[type = radio]:checked').val();
            if (type == 0) {
                $('#publishVideoDiv').hide();
                $('#publishImgDiv').show();

            } else if (type == 1) {
                $('#publishImgDiv').hide();
                $('#publishVideoDiv').show();
            }
        });

        $("#thumbnailInput").change(function () {
            if ($("#thumbnailInput").val() == "") {
                $("#thumbnailBtn").text("+");
            } else{
                $("#thumbnailBtn").text("已选择文件:" + $("#thumbnailInput").val());
            }
        });

        $("#pictureInput").change(function () {
            if ($("#pictureInput").val() == "") {
                $("#pictureBtn").text("+");
            } else{
                $("#pictureBtn").text("已选择文件:" + $("#pictureInput").val());
            }
        });
        $("#videoInput").change(function () {
            if ($("#videoInput").val() == "") {
                $("#videoBtn").text("+");
            } else{
                $("#videoBtn").text("已选择文件:" + $("#videoInput").val());
            }
        });
        $("#attachmentInput").change(function(){
            if($("#attachmentInput").val() == ""){
                $("#attachmentBtn").text("+");
            }else{
                $("#attachmentBtn").text("已选择文件："+$("#attachmentInput").val());
            }
        })

    });

    function showHint(string) {
        $("#reviewModal .modal-body").html(string);
        $('#reviewModal').modal('show');
    }

    function submitForm() {
        if (checkRequired()) {
            $("form").submit();
            $('#uploadModal').modal('show');
        }
    }

    function checkRequired() {
        if ($("#title").val() == ""){
            showHint("未填写标题");
            return false;
        }

        if ($("#subject").val() == ""){
            showHint("未填写所属课程");
            return false;
        }

        if ($('input[name=mediatype]:checked').val() == 0) {
            if ($('input[name=pictype]:checked').val() == 0 && $('#imgUrl').val()=="") {
                showHint("未选择图片");
                return false;
            } else if($('#pictureInput').val()=="") {
                showHint("未选择图片");
                return false;
            }
        } else if ($('input[name=mediatype]:checked').val() == 1) {
            if ($('#videoInput').val()=="") {
                showHint("未选择视频");
                return false;
            }
            if($('#thumbnailInput').val()=="") {
                showHint("未上传缩略图(视频类型需上传缩略图)");
                return false;
            }
        }

        return true;
    }
</script>

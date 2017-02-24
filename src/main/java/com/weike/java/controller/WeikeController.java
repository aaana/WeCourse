package com.weike.java.controller;

import com.weike.java.entity.*;
import com.weike.java.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tina on 2/16/17.
 */
@Controller
@RequestMapping("/")
public class WeikeController {
    @Autowired
    private FileService fileService;

    @Autowired
    private WeikeService weikeService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<WeikeCell> weikeCells = null;
        if(session.getAttribute("user") != null) {
            UserCell userCell = (UserCell) session.getAttribute("user");
            userCell = userService.findUserById(userCell.getId());
            session.setAttribute("user", userCell);
            model.addAttribute("messageNum", noticeService.getUnreadNoticeNumList(userCell.getId()));
            model.addAttribute("user", userCell);
            weikeCells = weikeService.findAllWeike(userCell.getId());
        } else {
            weikeCells = weikeService.findAllWeike();
        }
        model.addAttribute("weikeCells", weikeCells);
        return "playground";
    }

    @RequestMapping("/upload")
    public String upload(){
        return "publish";
    }

    @RequestMapping("/favorite")
    public @ResponseBody
    Map<String,Object> favorite(HttpServletRequest request) {
        int weikeId = Integer.parseInt(request.getParameter("weikeId"));
        HttpSession session = request.getSession();
        Map<String,Object> map = new HashMap<String,Object>();

        if(session.getAttribute("user") != null) {
            map.put("isLogged", true);
            UserCell userCell = (UserCell) session.getAttribute("user");

            // 新增favorite
            Favorite favorite = favoriteService.favorite(weikeId, userCell.getId());

            // 新增notice
            Weike weike = weikeService.findWeikeByWeikeId(weikeId);
            Notice notice = new Notice(userCell.getId(), weike.getUser_id(), 1, new Timestamp(System.currentTimeMillis()), favorite.getId(), weikeId, false);
            noticeService.saveNotice(notice);

            // weike.star_num ++
            weikeService.weikeGetFavorited(weikeId);
            map.put("result", true);

        } else {
            map.put("isLogged", false);
        }
        return map;
    }

    @RequestMapping("/unfavorite")
    public @ResponseBody
    Map<String,Object> unfavorite(HttpServletRequest request) {
        int weikeId = Integer.parseInt(request.getParameter("weikeId"));
        HttpSession session = request.getSession();
        Map<String,Object> map = new HashMap<String,Object>();

        if(session.getAttribute("user") != null) {
            map.put("isLogged", true);
            UserCell userCell = (UserCell) session.getAttribute("user");
            favoriteService.unfavorite(weikeId, userCell.getId());
            weikeService.weikeGetUnfavorited(weikeId);
            map.put("result", true);

        } else {
            map.put("isLogged", false);
        }
        return map;
    }

    @RequestMapping("/watchWeike")
    public @ResponseBody
    Map<String,Object> watchWeike(HttpServletRequest request) {
        int weikeId = Integer.parseInt(request.getParameter("weikeId"));
        Map<String,Object> map = new HashMap<String,Object>();
        weikeService.weikeGetWatched(weikeId);

        return map;
    }

    @RequestMapping("/makeComment")
    public @ResponseBody
    Map<String,Object> makeComment(HttpServletRequest request) {
        int weikeId = Integer.parseInt(request.getParameter("weikeId"));
        int parentId = Integer.parseInt(request.getParameter("parentId"));
        String content = request.getParameter("content");

        HttpSession session = request.getSession();
        Map<String,Object> map = new HashMap<String,Object>();

        if(session.getAttribute("user") != null) {
            map.put("isLogged", true);
            UserCell userCell = (UserCell) session.getAttribute("user");

            Comment comment = new Comment(userCell.getId(), weikeId, new Timestamp(System.currentTimeMillis()), content, parentId);
            commentService.newComment(comment);

            Weike weike = weikeService.findWeikeByWeikeId(weikeId);
            Notice notice = new Notice(userCell.getId(), weike.getUser_id(), 3, new Timestamp(System.currentTimeMillis()), comment.getId(), parentId, false);
            noticeService.saveNotice(notice);
            if (parentId != -1) {
                Comment comment2 = commentService.getSimpleCommentWithId(parentId);
                notice = new Notice(userCell.getId(), comment2.getPublisher_id(), 4, new Timestamp(System.currentTimeMillis()), comment.getId(), parentId, false);
                noticeService.saveNotice(notice);
            }

            weikeService.weikeGetCommented(weikeId);

            map.put("commentCell", commentService.transComment2CommentCell(comment));

        } else {
            map.put("isLogged", false);
        }
        return map;
    }

    @RequestMapping("/comment")
    public @ResponseBody
    Map<String,Object> getComment(HttpServletRequest request) {
        int weikeId = Integer.parseInt(request.getParameter("weikeId"));
        Map<String,Object> map = new HashMap<String,Object>();

        List<CommentCell> commentCells = commentService.getAllCommentsWithWeikeId(weikeId);

        map.put("comments", commentCells);
        return map;
    }

    @RequestMapping("/submitFile")
    public String submit(HttpServletRequest request)
            throws IllegalStateException, IOException {

        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null) {
            return "redirect:/";
        }

        String title = request.getParameter("title");
        String subject = request.getParameter("subject");
        String description = request.getParameter("description") == null? "" : request.getParameter("description");
        int mediaType = Integer.parseInt(request.getParameter("mediatype"));

        Timestamp today =  new Timestamp(System.currentTimeMillis());

        String fileName = "";
        String fieldName = "";
        UploadFile uploadFile = null;
        int fileid = -1;
        int thumbnailid = -1;

        String attachment = null;
        int userId = ((User)session.getAttribute("user")).getId();

        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();

            while(iter.hasNext()){
                //取得上传文件
                fieldName = iter.next();
                MultipartFile file = multiRequest.getFile(fieldName);
                if(file != null){
                    //取得当前上传文件的文件名称
                    fileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if(fileName.trim() !=""){
                        //定义上传路径
                        String directoryPath= request.getSession().getServletContext().getRealPath("/uploadfiles");
                        File directory = new File(directoryPath);
                        if(!directory.exists()) {
                            directory.mkdirs();
                        }
                        File uploadedFile = new File(directoryPath + File.separator + fileName);
                        if(!uploadedFile.exists()){
                            uploadedFile.createNewFile();
                        }
                        file.transferTo(uploadedFile);

                        if (fieldName.equals("thumbnail")){
                            uploadFile = new UploadFile(0, fileName);
                            thumbnailid = fileService.saveFile(uploadFile);
                        }else if(fieldName.equals("attachment")){
                            attachment = fileName;
                        }else {
                            uploadFile = new UploadFile(mediaType, fileName);
                            fileid = fileService.saveFile(uploadFile);
                        }

                    }
                }
            }
        }


        if (thumbnailid == -1 && mediaType == 0) {
            thumbnailid = fileid;
        }

//        HttpSession session = request.getSession();
//        int userId = -1;
//        if(session.getAttribute("user") != null) {
//            UserCell userCell = (UserCell) session.getAttribute("user");
//            userId = userCell.getId();
//        }
        Weike weike = new Weike(title, subject, userId, description, new Timestamp(System.currentTimeMillis()), fileid, thumbnailid,attachment, 0, 0, 0);
        weikeService.saveWeike(weike);
        return "redirect:/";
    }

    @RequestMapping("/detailWeike")
    public @ResponseBody
    Map<String,Object> getDetailWeike(HttpServletRequest request) {
        int weikeId = Integer.parseInt(request.getParameter("weikeId"));
        Map<String,Object> map = new HashMap<String,Object>();
        HttpSession session = request.getSession();

        if(session.getAttribute("user") != null) {
            UserCell userCell = (UserCell) session.getAttribute("user");

            WeikeCell weikeCell = weikeService.findWeikeByWeikeId(weikeId, userCell.getId());
            List<CommentCell> commentCells = commentService.getAllCommentsWithWeikeId(weikeId);
            map.put("weikeCell", weikeCell);
            map.put("commentCells", commentCells);
        }
        return map;
    }

}

package com.weike.java.controller;

import com.weike.java.entity.UploadFile;
import com.weike.java.entity.Weike;
import com.weike.java.entity.WeikeCell;
import com.weike.java.service.FileService;
import com.weike.java.service.WeikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

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

    @RequestMapping("/show")
    public String show(Model model){
        List<UploadFile> uploadFiles = fileService.getAllFiles();
        model.addAttribute("files", uploadFiles);
        return "show";
    }

    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }
        List<WeikeCell> weikeCells = weikeService.findAllWeike();
        model.addAttribute("weikeCells", weikeCells);
        return "playground";
    }

    @RequestMapping("/submitFile")
    public String submit(HttpServletRequest request)
            throws IllegalStateException, IOException {

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
                        } else {
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
        Weike weike = new Weike(title, subject, 1, description, new Timestamp(System.currentTimeMillis()), fileid, thumbnailid, 0, 0);
        weikeService.saveWeike(weike);
        return "redirect:/playground";
    }


}

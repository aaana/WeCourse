package com.weike.java.service;

import com.weike.java.DAO.FileDAO;
import com.weike.java.DAO.UserDAO;
import com.weike.java.entity.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tina on 2/13/17.
 */
@Service("fileService")
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDAO fileDao;

    public void saveFiles(List<UploadFile> files) {
        for (UploadFile f : files) {
            fileDao.save(f);
        }
    }

    public int saveFile(UploadFile file) {
        return fileDao.save(file);
    }

    public List<UploadFile> getAllFiles() {
        return fileDao.findAllFiles();
    }

    public int getNextUploadFileId() {
        return fileDao.getNextUploadFileId();
    }
}

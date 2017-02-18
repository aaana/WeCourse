package com.weike.java.DAO;

import com.weike.java.entity.UploadFile;
import com.weike.java.entity.User;

import java.util.List;

/**
 * Created by tina on 2/13/17.
 */
public interface FileDAO {
    public int save(UploadFile file);
    public List<UploadFile> findAllFiles();
    public List<UploadFile> findHotFiles();
    public List<UploadFile> findFilesWithUserId(int userId);


}

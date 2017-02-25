package com.weike.java.service;

import com.weike.java.entity.UploadFile;

import java.util.List;

/**
 * Created by tina on 2/13/17.
 */
public interface FileService {
    public void saveFiles(List<UploadFile> files);
    public int saveFile(UploadFile file);

    public List<UploadFile> getAllFiles();
    public int getNextUploadFileId();
}

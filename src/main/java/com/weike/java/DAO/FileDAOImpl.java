package com.weike.java.DAO;

import com.weike.java.entity.UploadFile;
import com.weike.java.entity.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 2/13/17.
 */
@Repository
public class FileDAOImpl implements FileDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(UploadFile file) {
        return (Integer) sessionFactory.getCurrentSession().save(file);
    }

    public List<UploadFile> findAllFiles() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UploadFile.class);
        return criteria.list();
    }

    public List<UploadFile> findHotFiles() {
        return null;
    }

    public List<UploadFile> findFilesWithUserId(int userId) {
        return null;
    }
}

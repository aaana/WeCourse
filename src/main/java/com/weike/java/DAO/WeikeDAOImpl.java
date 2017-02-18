package com.weike.java.DAO;

import com.weike.java.entity.UploadFile;
import com.weike.java.entity.Weike;
import com.weike.java.entity.WeikeCell;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tina on 2/16/17.
 */
@Repository
public class WeikeDAOImpl implements WeikeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(Weike weike) {
        return (Integer) sessionFactory.getCurrentSession().save(weike);
    }

    public void update(Weike weike) {
        sessionFactory.getCurrentSession().update(weike);
    }

    public void remove(Weike weike) {
        sessionFactory.getCurrentSession().createQuery("delete Weike where id=?").setParameter(0, weike.getId()).executeUpdate();
    }

    public WeikeCell findWeikeByWeikeId(int id) {
        Weike weike = (Weike) sessionFactory.getCurrentSession().createQuery("from Weike where id=?").setParameter(0, id).uniqueResult();
        return transWeike2WeikeCell(weike);

    }

    public List<WeikeCell> findAllWeike() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Weike.class);
        List<Weike> weikes = criteria.list();
        List<WeikeCell> weikeCells = new LinkedList<WeikeCell>();

        for (Weike weike : weikes) {
            weikeCells.add(transWeike2WeikeCell(weike));
        }
        return weikeCells;
    }

    public List<WeikeCell> findWeikesWithUserId(int id) {
        List<Weike> weikes = (List<Weike>) sessionFactory.getCurrentSession().createQuery("from Weike where user_id=?").setParameter(0, id).list();
        String user_name = (String) sessionFactory.getCurrentSession().createQuery("select name from User where id=?").setParameter(0, id).uniqueResult();
        List<WeikeCell> weikeCells = new LinkedList<WeikeCell>();

        for (Weike weike : weikes) {
            WeikeCell weikeCell = new WeikeCell(weike);
            String thumbnail_url = (String) sessionFactory.getCurrentSession().createQuery("select url from UploadFile where id=?").setParameter(0, weike.getThumbnail_id()).uniqueResult();
            int file_type = (Integer) sessionFactory.getCurrentSession().createQuery("select type from UploadFile where id=?").setParameter(0, weike.getFile_id()).uniqueResult();
            String file_url = (String) sessionFactory.getCurrentSession().createQuery("select url from UploadFile where id=?").setParameter(0, weike.getFile_id()).uniqueResult();
            weikeCell.setUser_name(user_name);
            weikeCell.setThumbnail_url(thumbnail_url);
            weikeCell.setFile_type(file_type);
            weikeCell.setFile_url(file_url);
            weikeCells.add(weikeCell);
        }
        return weikeCells;
    }

    public List<WeikeCell> findWeikeWithQueryString(String string) {
        List<Weike> weikes =  (List<Weike>) sessionFactory.getCurrentSession().createQuery(string).list();
        List<WeikeCell> weikeCells = new LinkedList<WeikeCell>();

        for (Weike weike : weikes) {
            weikeCells.add(transWeike2WeikeCell(weike));
        }
        return weikeCells;
    }

    public WeikeCell transWeike2WeikeCell(Weike weike) {
        WeikeCell weikeCell = new WeikeCell(weike);
        String user_name = (String) sessionFactory.getCurrentSession().createQuery("select name from User where id=?").setParameter(0, weike.getUser_id()).uniqueResult();
        String thumbnail_url = (String) sessionFactory.getCurrentSession().createQuery("select url from UploadFile where id=?").setParameter(0, weike.getThumbnail_id()).uniqueResult();
        int file_type = (Integer) sessionFactory.getCurrentSession().createQuery("select type from UploadFile where id=?").setParameter(0, weike.getFile_id()).uniqueResult();
        String file_url = (String) sessionFactory.getCurrentSession().createQuery("select url from UploadFile where id=?").setParameter(0, weike.getFile_id()).uniqueResult();
        weikeCell.setUser_name(user_name);
        weikeCell.setThumbnail_url(thumbnail_url);
        weikeCell.setFile_type(file_type);
        weikeCell.setFile_url(file_url);

        return weikeCell;
    }
}

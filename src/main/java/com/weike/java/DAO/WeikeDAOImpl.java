package com.weike.java.DAO;

import com.weike.java.entity.UploadFile;
import com.weike.java.entity.User;
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

    public Boolean update(Weike weike) {
        String hql = "update Weike weike set weike.title = ?,weike.subject=?,weike.description=?,weike.post_date=?," +
                "weike.file_id=?,weike.thumbnail_id=?,weike.view_num=?,weike.star_num=?,weike.comment_num=? where weike.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, weike.getTitle());
        query.setParameter(1, weike.getSubject());
        query.setParameter(2, weike.getDescription());
        query.setParameter(3, weike.getPost_date());
        query.setParameter(4, weike.getFile_id());
        query.setParameter(5, weike.getThumbnail_id());
        query.setParameter(6, weike.getView_num());
        query.setParameter(7, weike.getStar_num());
        query.setParameter(8, weike.getComment_num());
        query.setParameter(9, weike.getId());

        return (query.executeUpdate() > 0);
//        sessionFactory.getCurrentSession().update(weike);
    }

    public void remove(Weike weike) {
        sessionFactory.getCurrentSession().createQuery("delete Weike where id=?").setParameter(0, weike.getId()).executeUpdate();
    }

    public Weike findSimpleWeikeByWeikeId(int id) {
        return (Weike) sessionFactory.getCurrentSession().createQuery("from Weike where id=?").setParameter(0, id).uniqueResult();
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
        User user = (User) sessionFactory.getCurrentSession().createQuery("from User where id=?").setParameter(0, id).uniqueResult();
        List<WeikeCell> weikeCells = new LinkedList<WeikeCell>();

        for (Weike weike : weikes) {
            WeikeCell weikeCell = new WeikeCell(weike);
            UploadFile thumbnail = (UploadFile) sessionFactory.getCurrentSession().createQuery("from UploadFile where id=?").setParameter(0, weike.getThumbnail_id()).uniqueResult();
            UploadFile file = (UploadFile) sessionFactory.getCurrentSession().createQuery("from UploadFile where id=?").setParameter(0, weike.getFile_id()).uniqueResult();
            weikeCell.setUser_name(user.getName());
            weikeCell.setUser_avatar(user.getAvatar());
            weikeCell.setThumbnail_url(thumbnail.getUrl());
            weikeCell.setThumbnail_size("1280x1280");
            weikeCell.setFile_url(file.getUrl());
            weikeCell.setFile_type(file.getType());
            weikeCells.add(weikeCell);
        }
        return weikeCells;
    }

    public int findWeikeNumWithUserId(int id) {
        return findWeikesWithUserId(id).size();
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
        User user = (User) sessionFactory.getCurrentSession().createQuery("from User where id=?").setParameter(0, weike.getUser_id()).uniqueResult();
        UploadFile thumbnail = (UploadFile) sessionFactory.getCurrentSession().createQuery("from UploadFile where id=?").setParameter(0, weike.getThumbnail_id()).uniqueResult();
        UploadFile file = (UploadFile) sessionFactory.getCurrentSession().createQuery("from UploadFile where id=?").setParameter(0, weike.getFile_id()).uniqueResult();
        weikeCell.setUser_name(user.getName());
        weikeCell.setUser_avatar(user.getAvatar());
        weikeCell.setThumbnail_url(thumbnail.getUrl());
        weikeCell.setThumbnail_size("1280x1280");
        weikeCell.setFile_url(file.getUrl());
        weikeCell.setFile_type(file.getType());

        return weikeCell;
    }
}

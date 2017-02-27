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

    private int gap = 5;

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
            weikeCells.add(transWeike2WeikeCell(weike, user));
        }
        return weikeCells;
    }

    public List<WeikeCell> findHotWeikesWithUserId(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Weike where user_id=? and view_num >= 10 order by (3*star_num + 2*comment_num + view_num)").setParameter(0, id);
        query.setMaxResults(5);
        List<Weike> weikes = (List<Weike>) query.list();
        User user = (User) sessionFactory.getCurrentSession().createQuery("from User where id=?").setParameter(0, id).uniqueResult();
        List<WeikeCell> weikeCells = new LinkedList<WeikeCell>();

        for (Weike weike : weikes) {
            weikeCells.add(transWeike2WeikeCell(weike, user));
        }
        return weikeCells;
    }

    public List<WeikeCell> findWeikeFromStartNum(int startNum) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Weike order by id DESC");
        query.setFirstResult(startNum);
        query.setMaxResults(gap);
        List<Weike> weikes = query.list();

        List<WeikeCell> weikeCells = new LinkedList<WeikeCell>();

        for (Weike weike : weikes) {
            weikeCells.add(transWeike2WeikeCell(weike));
        }

        return weikeCells;
    }

    public int findWeikeNumWithUserId(int id) {
        return findWeikesWithUserId(id).size();
    }

    public Boolean haveMoreWeike(int startNum) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Weike order by id DESC");
        query.setFirstResult(startNum);
        query.setMaxResults(gap + 1);
        List<Weike> weikes = query.list();
        return weikes.size() > gap;
    }

    public int getGap() {
        return gap;
    }

    public List<WeikeCell> findWeikeWithQueryString(String string, String searchString) {
        return null;
    }

    public List<WeikeCell> findWeikeWithQueryString(String string, List<Integer> searchUsers) {
        return null;
    }

    public List<WeikeCell> searchWeikeWithContentString(int startNum, String searchString) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Weike WHERE title LIKE ? or description LIKE ? ORDER BY id DESC").setParameter(0, "%" + searchString + "%").setParameter(1, "%" + searchString + "%");
        query.setMaxResults(gap + 1);
        query.setFirstResult(startNum);
        List<Weike> weikes =  (List<Weike>) query.list();
        List<WeikeCell> weikeCells = new LinkedList<WeikeCell>();

        for (Weike weike : weikes) {
            weikeCells.add(transWeike2WeikeCell(weike));
        }
        return weikeCells;
    }

    public List<WeikeCell> searchWeikeWithUserNameString(int startNum, List<Integer> searchUsers) {
        String search = "FROM Weike";
        for (int i = 0; i < searchUsers.size(); i ++) {
            if (i == 0) {
                search = search + " WHERE user_id=?";
            } else {
                search = search + " OR user_id=?";
            }
        }
        search = search + " ORDER BY id DESC";
        Query query = sessionFactory.getCurrentSession().createQuery(search);
        for (int i = 0; i < searchUsers.size(); i ++) {
            query.setParameter(i, searchUsers.get(i));
        }
        query.setMaxResults(gap + 1);
        query.setFirstResult(startNum);
        List<Weike> weikes =  (List<Weike>) query.list();
        List<WeikeCell> weikeCells = new LinkedList<WeikeCell>();
        for (Weike weike : weikes) {
            weikeCells.add(transWeike2WeikeCell(weike));
        }
        return weikeCells;
    }

    public List<WeikeCell> searchWeikeWithSubjectString(int startNum, String searchString) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Weike WHERE subject LIKE ? ORDER BY id DESC").setParameter(0, "%" + searchString + "%");
        query.setMaxResults(gap + 1);
        query.setFirstResult(startNum);
        List<Weike> weikes =  (List<Weike>) query.list();
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
    public WeikeCell transWeike2WeikeCell(Weike weike, User user) {
        WeikeCell weikeCell = new WeikeCell(weike);
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

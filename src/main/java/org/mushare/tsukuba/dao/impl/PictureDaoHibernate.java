package org.mushare.tsukuba.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.PictureDao;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.Picture;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PictureDaoHibernate extends BaseHibernateDaoSupport<Picture> implements PictureDao {

   public PictureDaoHibernate(){
       super();
       setClass(Picture.class);
   }

    public List<Picture> findByMessage(Message message) {
        String hql = "from Picture where message = ? order by createAt";
        return (List<Picture>) getHibernateTemplate().find(hql, message);
    }

    public Picture getOldestByMessage(final Message message) {
        final String hql = "from Picture where message = ? order by createAt desc";
        return getHibernateTemplate().execute(new HibernateCallback<Picture>() {
            public Picture doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0, message);
                query.setMaxResults(1);
                List<Picture> pictures = query.list();
                if (pictures.size() == 0) {
                    return null;
                }
                return pictures.get(0);
            }
        });
    }
}

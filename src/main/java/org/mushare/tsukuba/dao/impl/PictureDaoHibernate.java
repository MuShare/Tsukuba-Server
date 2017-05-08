package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.PictureDao;
import org.mushare.tsukuba.domain.Message;
import org.mushare.tsukuba.domain.Picture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PictureDaoHibernate extends BaseHibernateDaoSupport<Picture> implements PictureDao {

   public PictureDaoHibernate(){
       super();
       setClass(Picture.class);
   }

    public List<Picture> findByMessage(Message message) {
        String hql = "from Picture where message = ? order by createAt desc";
        return (List<Picture>) getHibernateTemplate().find(hql, message);
    }
    
}

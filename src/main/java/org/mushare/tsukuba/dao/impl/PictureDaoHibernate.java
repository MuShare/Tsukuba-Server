package org.mushare.tsukuba.dao.impl;

import org.mushare.common.hibernate.BaseHibernateDaoSupport;
import org.mushare.tsukuba.dao.PictureDao;
import org.mushare.tsukuba.domain.Picture;
import org.springframework.stereotype.Repository;

@Repository
public class PictureDaoHibernate extends BaseHibernateDaoSupport<Picture> implements PictureDao {
   public PictureDaoHibernate(){
       super();
       setClass(Picture.class);
   }
}

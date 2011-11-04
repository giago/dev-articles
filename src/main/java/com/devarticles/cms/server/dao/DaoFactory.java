package com.devarticles.cms.server.dao;

import com.devarticles.cms.server.dao.jdo.JdoArticleDao;
import com.devarticles.cms.server.dao.jdo.JdoProfileDao;
import com.devarticles.cms.server.dao.jdo.JdoTagDao;

public class DaoFactory {
    
    public static final ArticleDao getArticleDao() {
        return new JdoArticleDao();
    }
    
    public static final ProfileDao getProfileDao() {
        return new JdoProfileDao();
    }

    public static final TagDao getTagDao() {
        return new JdoTagDao();
    }

}

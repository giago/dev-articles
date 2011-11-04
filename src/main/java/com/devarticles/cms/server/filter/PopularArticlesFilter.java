
package com.devarticles.cms.server.filter;

import javax.servlet.ServletRequest;

import com.giago.appengine.commons.filter.AbstractBaseFilter;

import com.devarticles.cms.Constants;
import com.devarticles.cms.server.dao.jdo.JdoArticleDao;

public class PopularArticlesFilter extends AbstractBaseFilter {

    private JdoArticleDao articleDao = new JdoArticleDao();

    @Override
    protected void execute(ServletRequest request) {
        request.setAttribute(Constants.Attribute.popularArticleUrls,
                articleDao.getPopularArticlesUrl());
    }

}

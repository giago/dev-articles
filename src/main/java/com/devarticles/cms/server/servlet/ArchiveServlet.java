
package com.devarticles.cms.server.servlet;

import javax.servlet.http.HttpServletRequest;

import com.devarticles.cms.Constants;
import com.devarticles.cms.server.dao.jdo.JdoArticleDao;
import com.devarticles.cms.server.parser.UrlParser;
import com.giago.appengine.commons.util.DateUtils;

public class ArchiveServlet extends DispatcherServlet {
	
    private static final long serialVersionUID = 1L;

    private JdoArticleDao articleDao = new JdoArticleDao();

    @Override
    protected String process(HttpServletRequest req) {
        StringBuffer url = req.getRequestURL();
        if (url != null) {
            Integer year = UrlParser.getArchiveYear(url.toString());
            if(year == null) {
            	year = DateUtils.currentYear();
            } 
            req.setAttribute(Constants.Attribute.archiveYearList, articleDao.getArticleLinksByYear(year));
            req.setAttribute(Constants.Attribute.yearName, "" + year);
            req.setAttribute(Constants.Attribute.yearList, DateUtils.getYears(2010));
        }
        req.setAttribute(Constants.Parameter.url, url);
        return Constants.Page.archive;
    }

}

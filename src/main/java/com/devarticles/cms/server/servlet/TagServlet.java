
package com.devarticles.cms.server.servlet;

import javax.servlet.http.HttpServletRequest;

import com.devarticles.cms.Constants;
import com.devarticles.cms.server.dao.jdo.JdoArticleDao;
import com.devarticles.cms.server.dao.jdo.JdoTagDao;
import com.devarticles.cms.server.parser.UrlParser;

public class TagServlet extends DispatcherServlet {

    private static final long serialVersionUID = 1L;
    private static final String TAG = "tag";

    private JdoArticleDao articleDao = new JdoArticleDao();
    private JdoTagDao tagDao = new JdoTagDao();
    

    @Override
    protected String process(HttpServletRequest req) {
        StringBuffer url = req.getRequestURL();
        if (url != null) {
            String tagName = UrlParser.getLastSegmentWitoutId(url.toString());
            if(tagName == null || tagName.length() == 0 || TAG.equals(tagName)) {
                tagName = tagDao.getMostPopularTag();
            }
            if(tagName != null) {
                tagName = UrlParser.cleanUpFromEncodedSpaces(tagName);
            }
            req.setAttribute(Constants.Attribute.articleTagList,
                    articleDao.getArticleLinksByTag(tagName));
            req.setAttribute(Constants.Attribute.tagName, tagName);
            req.setAttribute(Constants.Attribute.tagList, tagDao.getMostCommonTags());
        }
        req.setAttribute(Constants.Parameter.url, url);
        return Constants.Page.tag;
    }

}

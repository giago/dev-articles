package com.devarticles.cms.server.dao;

import java.util.List;

import com.giago.appengine.commons.dao.BaseDao;

import com.devarticles.cms.server.model.Tag;

public interface TagDao extends BaseDao<Tag>{

    String getMostPopularTag();

    List<String> getMostCommonTags();

    List<String> getMostCommonTags(boolean range);

}

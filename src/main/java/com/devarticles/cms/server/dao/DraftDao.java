package com.devarticles.cms.server.dao;

import java.util.List;

import com.giago.appengine.commons.dao.BaseDao;

import com.devarticles.cms.server.model.Draft;

public interface DraftDao extends BaseDao<Draft> {

    List<Draft> getAll(String userId);

    Draft getForEdit(Long idFromUrl);

}

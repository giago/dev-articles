package com.devarticles.cms.server.dao;

import com.giago.appengine.commons.dao.BaseDao;

import com.devarticles.cms.shared.Profile;

public interface ProfileDao extends BaseDao<Profile> {

    Profile get();

    Profile getByUserId(String userId);

    void getByUsername(String userId);

}

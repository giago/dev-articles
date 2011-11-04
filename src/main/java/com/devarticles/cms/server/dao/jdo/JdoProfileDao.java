package com.devarticles.cms.server.dao.jdo;

import com.giago.appengine.commons.dao.jdo.BaseDaoImpl;

import com.devarticles.cms.server.dao.ProfileDao;
import com.devarticles.cms.shared.Profile;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class JdoProfileDao extends BaseDaoImpl<Profile> implements ProfileDao {
        
    private static final String USERNAME = "username";
    
    private static final String USER_ID = "userId";
    
    public JdoProfileDao() {
        super(Profile.class);
    }
    
    @Override
    public Profile get() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        return getByUserId(user.getUserId());
    }
    
    @Override
    public Profile getByUserId(String userId) {
        return getByProperty(USER_ID, ParameterType.string_type, userId);
    }
    
    @Override
    public void getByUsername(String userId) {
        deleteByProperty(USERNAME, ParameterType.string_type, userId);
    }
    
}

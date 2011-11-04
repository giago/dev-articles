package com.devarticles.cms.server.dao.jdo;

import java.util.List;

import javax.jdo.Query;

import com.giago.appengine.commons.dao.BaseDao;
import com.giago.appengine.commons.dao.jdo.BaseDaoImpl;

import com.devarticles.cms.shared.Invite;

public class JdoInviteDao extends BaseDaoImpl<Invite> implements BaseDao<Invite> {

    private static final String TOKEN = "token";
    
    private static final String USED_DATE = "usedDate";
    
    private static final String RECEIVER_USER_ID = "receiverUserId";
    
    public JdoInviteDao() {
        super(Invite.class);
    }
    
    public Invite getByToken(String token) {
        return getByProperty(TOKEN, ParameterType.string_type, token);
    }
    
    public Invite getByUserId(String userId) {
        return getByProperty(RECEIVER_USER_ID, ParameterType.string_type, userId);
    }

    public List<Invite> search(final String userId) {
        return executeQuery(new QueryPersonalizer(Invite.class) {
            @Override
            public void get(Query q) {
                super.get(q);
                q.setFilter(getEqualStringFilter(RECEIVER_USER_ID, userId) + " && " + USED_DATE + " == null");
            }
        });
    }
    
}

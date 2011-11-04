package com.devarticles.cms.server.dao.jdo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.giago.appengine.commons.dao.jdo.BaseDaoImpl;

import com.devarticles.cms.server.dao.TagDao;
import com.devarticles.cms.server.model.Tag;


public class JdoTagDao extends BaseDaoImpl<Tag> implements TagDao {

    public JdoTagDao() {
        super(Tag.class);
    }
    
    @Override
    public void save(final Tag model) {
        final Tag tag = getByProperty(Tag.Property.tag, ParameterType.string_type, model.getTag());
        executeInTransaction(new TransactionalCommand() {
            @Override
            public void execute(PersistenceManager pm) {
                if(tag == null) {
                    model.setCreatedDate(new Date());
                    model.setModified(new Date());
                    pm.makePersistent(model);
                } else {
                	if(tag.getModified() == null) {
                		tag.setModified(new Date());
                		tag.setCounter(1L);
                	} else if(tag.getModified().getTime() < System.currentTimeMillis() - 60L*1000L) {
                		tag.setModified(new Date());
                		tag.setCounter(1L);
                	} else {           
                		tag.setModified(new Date());
                		tag.increase();
                	}
                    pm.makePersistent(tag);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getMostPopularTag() {
        PersistenceManager pm = getPM();
        Query q = pm.newQuery(Tag.class);
        q.setOrdering("counter desc");
        q.setRange(0, 1);
        List<Tag> result = (List<Tag>) q.execute();
        if(result != null && !result.isEmpty()) {
            Tag m = result.get(0);
            return m.getTag();
        } else {
            return null;
        }
    }

    @Override
    public List<String> getMostCommonTags() {
        return getMostCommonTags(false);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getMostCommonTags(boolean range) {
        PersistenceManager pm = getPM();
        Query q = pm.newQuery(Tag.class);
        q.setOrdering("counter desc");
        if(range) {
            q.setRange(0, PAGE_SIZE);
        }
        List<Tag> result = (List<Tag>) q.execute();
        List<String> tags = new ArrayList<String>();
        if(result != null && !result.isEmpty()) {
            for(Tag tag : result) {
                tags.add(tag.getTag());
            }
        }
        return tags;
    }
    
}

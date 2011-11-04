package com.devarticles.cms.server.dao.jdo;

import java.util.Date;
import java.util.List;

import javax.jdo.JDOOptimisticVerificationException;
import javax.jdo.PersistenceManager;

import com.giago.appengine.commons.dao.jdo.BaseDaoImpl;

import com.devarticles.cms.server.dao.DraftDao;
import com.devarticles.cms.server.model.Draft;

public class JdoDraftDao extends BaseDaoImpl<Draft> implements DraftDao {

    public JdoDraftDao() {
        super(Draft.class);
    }

    @Override
    public List<Draft> getAll(String userId) {
        return searchByProperty("userId", userId);
    }

    @Override
    public Draft getForEdit(Long idFromUrl) {
        try {
            PersistenceManager pm = getPM();
            Draft article = (Draft) pm.getObjectById(Draft.class, idFromUrl);
            return article;
        } catch (Exception e) {
            throw new RuntimeException("Draft with id : " + idFromUrl + " can't be found!");
        }
    }
    
    @Override
    public void save(Draft model) {
    	PersistenceManager pm = getPM();
		pm.currentTransaction().begin();
	    try {
	    	if(model.getId() != null) {
	    		Draft toUpdate = getDraft(pm, model.getId());
	    		toUpdate.setAuthor(model.getAuthor());
	    		
	    		toUpdate.getContents().clear();
	    		toUpdate.setContents(model.getContents());
	    		
	    		toUpdate.setDescription(model.getDescription());
	    		toUpdate.setTags(model.getTags());
	    		
	    		toUpdate.getLinks().clear();
	    		toUpdate.setLinks(model.getLinks());

	    		toUpdate.setTitle(model.getTitle());
	    		toUpdate.setModifiedDate(new Date());
	    		pm.makePersistent(model);
	    	} else {
	    		model.setModifiedDate(new Date());
	    		pm.makePersistent(model);
	    	}
	        pm.currentTransaction().commit();
	    } catch (JDOOptimisticVerificationException e) {
	        //handleVersionConflict(e, article);
	    	throw new RuntimeException("JDOOptimisticVerificationException", e);
	    } finally {
	        if (pm.currentTransaction().isActive()) {
	            pm.currentTransaction().rollback();
	        }
	        pm.close();
	    }
    }
    
    private Draft getDraft(PersistenceManager pm, Long id) {
		try {
			return(Draft) pm.getObjectById(Draft.class, id);
		} catch (Exception e) {
			throw new RuntimeException("Article with id : " + id + " can't be found!");
		}
	}

}

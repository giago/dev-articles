package com.devarticles.cms.server.dao.jdo;

import java.util.List;

import javax.jdo.Query;

import com.devarticles.cms.shared.Correction;
import com.giago.appengine.commons.dao.BaseDao;
import com.giago.appengine.commons.dao.jdo.BaseDaoImpl;

public class JdoCorrectionDao extends BaseDaoImpl<Correction> implements BaseDao<Correction>{

	public JdoCorrectionDao() {
		super(Correction.class);
	}

	public List<Correction> search(final Long articleId) {
        return executeQuery(new QueryPersonalizer(Correction.class) {
            @Override
            public void get(Query q) {
                super.get(q);
                q.setFilter(getEqualLongFilter(Correction.ARTICLE_ID, articleId));
            }
        });
	}
	
//	@Override
//	public void save(Correction model) {
//		if(model.getId() != null) {
//			Correction m = get(model.getId());
//			m.setCorrection(model.getCorrection());
//			super.save(m);
//		} else {
//			super.save(model);
//		}
//	}

}

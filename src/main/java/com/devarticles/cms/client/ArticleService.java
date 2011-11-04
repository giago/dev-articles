package com.devarticles.cms.client;

import java.util.List;

import com.devarticles.cms.client.model.ArticleDto;
import com.devarticles.cms.client.model.DraftDto;
import com.devarticles.cms.shared.Correction;
import com.devarticles.cms.shared.Profile;
import com.devarticles.cms.shared.ServerException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("articleService")
public interface ArticleService extends RemoteService {

	void save(ArticleDto article);
	
	ArticleDto get(String url);

    void twitt(String message);
    
    String getTwitterUrl();

    Profile getProfile();

    void registerTwitterPin(String pin);

    void saveAsDraft(ArticleDto value);

    ArticleDto getDraft(String draftUrl);

    List<DraftDto> getDrafts();

    void sendInvite(String email, String message) throws ServerException;

	void deleteDraft(Long draftId);
	
	void save(List<Correction> correction);

	List<Correction> getCorrections(Long articleId);
	
}

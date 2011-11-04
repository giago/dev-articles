package com.devarticles.cms.client;

import com.devarticles.cms.client.model.ArticleDto;
import com.devarticles.cms.client.model.DraftDto;
import java.util.List;

import com.devarticles.cms.shared.Correction;
import com.devarticles.cms.shared.Profile;
import com.devarticles.cms.shared.ServerException;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ArticleServiceAsync {

	void save(ArticleDto article, AsyncCallback<Void> callback);

	void get(String url, AsyncCallback<ArticleDto> callback);

    void twitt(String text, AsyncCallback<Void> callback);

    void getTwitterUrl(AsyncCallback<String> callback);

    void getProfile(AsyncCallback<Profile> asyncCallback);

    void registerTwitterPin(String pin, AsyncCallback<Void> asyncCallback);

    void saveAsDraft(ArticleDto value, AsyncCallback<Void> asyncCallback);

    void getDraft(String draftUrl, AsyncCallback<ArticleDto> asyncCallback);

    void getDrafts(AsyncCallback<List<DraftDto>> asyncCallback);

    void sendInvite(String email, String message, AsyncCallback<Void> callback) throws ServerException;

	void deleteDraft(Long draftId, AsyncCallback<Void> asyncCallback);
	
	void save(List<Correction> corrections, AsyncCallback<Void> asyncCallback);
	
	void getCorrections(Long articleId, AsyncCallback<List<Correction>> asyncCallback);

}

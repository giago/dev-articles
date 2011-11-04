package com.devarticles.cms.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    public Profile() {
    }
    
    public Profile(String userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Persistent
    private String userId;
    
    @Persistent
    private String nickname;
    
    @Persistent
    private String twitterRequestToken;
    
    @Persistent
    private String twitterSecretToken;
    
    @Persistent
    private String twitterAccessToken;
    
    @Persistent
    private String twitterAccessTokenSecret;
    
    @Persistent
    private boolean twitterPluginEnabled;
    
    @Persistent
    private boolean invitePluginEnabled;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isTwitterAccountSet() {
        if(twitterAccessToken != null && twitterAccessTokenSecret != null) {
            return true;
        }
        return false;
    }

    public void setTwitterRequestToken(String twitterRequestToken) {
        this.twitterRequestToken = twitterRequestToken;
    }

    public String getTwitterRequestToken() {
        return twitterRequestToken;
    }

    public void setTwitterAccessToken(String twitterAccessToken) {
        this.twitterAccessToken = twitterAccessToken;
    }

    public String getTwitterAccessToken() {
        return twitterAccessToken;
    }

    public void setTwitterSecretToken(String twitterSecretToken) {
        this.twitterSecretToken = twitterSecretToken;
    }

    public String getTwitterSecretToken() {
        return twitterSecretToken;
    }

    public void setTwitterAccessTokenSecret(String twitterAccessTokenSecret) {
        this.twitterAccessTokenSecret = twitterAccessTokenSecret;
    }

    public String getTwitterAccessTokenSecret() {
        return twitterAccessTokenSecret;
    }

    public void setTwitterPluginEnabled(boolean twitterPluginEnabled) {
        this.twitterPluginEnabled = twitterPluginEnabled;
    }

    public boolean isTwitterPluginEnabled() {
        return twitterPluginEnabled;
    }

    public void setInvitePluginEnabled(boolean invitePluginEnabled) {
        this.invitePluginEnabled = invitePluginEnabled;
    }

    public boolean isInvitePluginEnabled() {
        return invitePluginEnabled;
    }
    
}

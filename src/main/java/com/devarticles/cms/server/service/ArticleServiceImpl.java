package com.devarticles.cms.server.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import com.devarticles.cms.client.ArticleService;
import com.devarticles.cms.client.model.ArticleDto;
import com.devarticles.cms.client.model.DraftDto;
import com.devarticles.cms.server.dao.jdo.JdoArticleDao;
import com.devarticles.cms.server.dao.jdo.JdoCorrectionDao;
import com.devarticles.cms.server.dao.jdo.JdoDraftDao;
import com.devarticles.cms.server.dao.jdo.JdoInviteDao;
import com.devarticles.cms.server.dao.jdo.JdoProfileDao;
import com.devarticles.cms.server.model.Article;
import com.devarticles.cms.server.model.Draft;
import com.devarticles.cms.shared.Correction;
import com.devarticles.cms.shared.Invite;
import com.devarticles.cms.shared.Profile;
import com.devarticles.cms.shared.ServerException;
import com.devarticles.cms.shared.Correction.Type;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ArticleServiceImpl extends RemoteServiceServlet implements ArticleService {
	
	private static final long serialVersionUID = 1L;
	
	//Request token URL http://twitter.com/oauth/request_token
	//Access token URL http://twitter.com/oauth/access_token
	//Authorize URL http://twitter.com/oauth/authorize
    private static final String CONSUMER_KEY = "pVenTCApXOajSOGMDwQJZw";
    private static final String CONSUMER_SECRET = "Zav94zVdSloG96slW7zO5xd6gB34wN4I2RPf7X5E";
    private static final String CLIENT_URL = "http://www.dev-articles.com";
    private static final String USER_AGENT = "dev-articles http://www.dev-articles.com";
    private static final String TWITTER_URL = "http://twitter.com/oauth/authorize?oauth_token=";

    private TwitterFactory getTwitterFactory() {
        return new TwitterFactory(new ConfigurationBuilder().setClientURL(CLIENT_URL)
                .setUserAgent(USER_AGENT).setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET).build());
    }
    
	private JdoArticleDao articleDao = new JdoArticleDao();
	private JdoCorrectionDao correctionDao = new JdoCorrectionDao();
	private JdoProfileDao profileDao = new JdoProfileDao();
	private JdoDraftDao draftDao = new JdoDraftDao();
	private JdoInviteDao inviteDao = new JdoInviteDao();
	private UserService userService = UserServiceFactory.getUserService();
	private static final Logger logger = Logger.getLogger(ArticleServiceImpl.class.getName());
	
	@Override
	public void save(ArticleDto articleJdo) {
	    logger.info("saving article");
		Article article = new Article(articleJdo);
		
		User user = userService.getCurrentUser();
		article.setUserId(user.getUserId());
		
		articleDao.save(article);
	}

	@Override
	public ArticleDto get(String url) {
	    logger.info("get article");
		User user = userService.getCurrentUser();
		Article article = articleDao.getForEdit(url, user.getUserId());
		if(article == null) {
			return null;			
		} else {
			return article.asDto();
		}
	}

    @Override
    public void twitt(String message) {
        logger.info("twitt message");
        Profile profile = getProfile();
        AccessToken accessToken = new AccessToken(profile.getTwitterAccessToken(), profile.getTwitterAccessTokenSecret());
        Twitter twitter = getTwitterFactory().getOAuthAuthorizedInstance(accessToken);
        try {
            twitter.updateStatus(message);
        } catch(TwitterException te) {
            logger.severe("Problem sending message to twitter : " + te.getMessage());
            throw new RuntimeException("Problem sending message to twitter : " + te.getMessage());
        }
    }
    
    @Override
    public String getTwitterUrl() {
        logger.info("get token: ");
        Twitter twitter = getTwitterFactory().getOAuthAuthorizedInstance(CONSUMER_KEY, CONSUMER_SECRET);
        try {
            RequestToken requestToken = twitter.getOAuthRequestToken();
            String token = requestToken.getToken();
            String tokenSecret = requestToken.getTokenSecret();
            logger.info("requested token : " + token + " tokenSecret : " + tokenSecret);
            Profile profile = getProfile();
            profile.setTwitterRequestToken(token);
            profile.setTwitterSecretToken(tokenSecret);
            profileDao.save(profile);
            return TWITTER_URL + token; 
        } catch (TwitterException te) {
            logger.severe("Problem sending message to twitter : " + te.getMessage());
            throw new RuntimeException("Problem sending message to twitter : " + te.getMessage());
        }
    }
    
    @Override
    public Profile getProfile() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String userId = user.getUserId();
        Profile profile = profileDao.getByUserId(userId);
        if(profile != null) {
            return profile;
        } else {
            if(inviteDao.getByUserId(userId) != null || userService.isUserAdmin()) {                
                profileDao.save(new Profile(userId, user.getEmail().replace("@gmail.com", "")));
                return profileDao.getByUserId(userId);
            }
            return null;
        }
    }

    @Override
    public void registerTwitterPin(String pin) {
        Profile profile = getProfile();
        Twitter twitter = getTwitterFactory().getInstance();
        try {
            AccessToken accessToken = twitter.getOAuthAccessToken(profile.getTwitterRequestToken(), profile.getTwitterSecretToken(), pin);
            profile.setTwitterAccessTokenSecret(accessToken.getTokenSecret());
            profile.setTwitterAccessToken(accessToken.getToken());
            profileDao.save(profile);
        } catch(Exception e) {
            logger.severe("Problem during twitterPin" + e.getMessage());
            throw new RuntimeException("Problem during twitterPin" + e.getMessage());
        }
    }

    @Override
    public void saveAsDraft(ArticleDto articleDto) {
        logger.info("saving draft");
        Draft draft = new Draft(articleDto);
        User user = userService.getCurrentUser();
        draft.setUserId(user.getUserId());
        draftDao.save(draft);
    }

    @Override
    public ArticleDto getDraft(String draftUrl) {
        User user = userService.getCurrentUser();
        Draft draft = draftDao.getForEdit(getIdFromUrl(draftUrl));
        if(draft == null) {
            return null;
        } else if(!user.getUserId().equals(draft.getUserId())) {
            return null;            
        } else {
            return draft.asDto();
        }
    }
    
    private Long getIdFromUrl(String url) {
        String id = url.substring(url.lastIndexOf("-") + 1);
        return Long.valueOf(id);
    }

    @Override
    public List<DraftDto> getDrafts() {
        User user = userService.getCurrentUser();
        List<Draft> drafts = draftDao.getAll(user.getUserId());
        List<DraftDto> dtos = new ArrayList<DraftDto>();
        if(dtos != null) {
            for(Draft draft : drafts) {
                dtos.add(new DraftDto(draft.getTitle(), draft.getId()));
            }
        }
        return dtos;
    }

    @Override
    public void sendInvite(String email, String message) throws ServerException {
        if(email == null) {
            logger.log(Level.SEVERE, "Send invite can't procede because the email is null");
            throw new ServerException("Send invite can't procede because the email is not defined");
        }
        Invite invite = persistInvite(null, email);
        sendEmail(email, invite);
    }
    
    private Invite persistInvite(String recipientUserId, String recipientEmail) throws ServerException {
        Invite invite = new Invite();
        Profile senderProfile = getProfile();
        if(senderProfile != null) {
            invite.setSenderNickname(senderProfile.getNickname());
            invite.setSenderUserId(senderProfile.getUserId());            
        }     
        
        if(recipientUserId != null) {
            invite.setReceiverUserId(recipientUserId);
        }
        String token = UUID.randomUUID().toString();
        invite.setToken(token);
        invite.setSentDate(new Date());
        invite.setReceiverEmail(recipientEmail);
        inviteDao.save(invite);
        return invite;
    }
    
    private void sendEmail(String recipientEmail, Invite invite) throws ServerException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            String nickname = invite.getSenderNickname();
            UserService userService = UserServiceFactory.getUserService();
            User user = userService.getCurrentUser();
            String senderEmail = user.getEmail();
            if(nickname == null) {
                nickname = senderEmail;
            }
            msg.setFrom(new InternetAddress(senderEmail, nickname));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(recipientEmail));
            msg.setSubject(nickname + " send you an invite to join the site dev-articles");
            msg.setText(nickname + " invite you to join the site dev-articles : " +
                    " http://www.dev-articles.com/invite?token=" + invite.getToken() + "");
            Transport.send(msg);
        } catch (AddressException e) {
            logger.log(Level.SEVERE, "AddressException", e);
            throw new ServerException(e.getMessage());
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "MessagingException", e);
            throw new ServerException(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, "UnsupportedEncodingException", e);
            throw new ServerException(e.getMessage());
        }
    }

	@Override
	public void deleteDraft(Long draftId) {
		draftDao.delete(draftId);
	}

	@Override
	public void save(List<Correction> correction) {
		correctionDao.save(correction);
	}
	
	@Override
	public List<Correction> getCorrections(Long articleId) {
        List<Correction> cs = correctionDao.search(articleId);
        List<Correction> ctos = new ArrayList<Correction>();
        if(cs != null) {
            for(Correction c : cs) {
                ctos.add(c);
            }
        }
		return ctos;
	}

}

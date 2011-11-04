
package com.devarticles.cms.server.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devarticles.cms.server.dao.jdo.JdoInviteDao;
import com.devarticles.cms.server.dao.jdo.JdoProfileDao;
import com.devarticles.cms.shared.Invite;
import com.devarticles.cms.shared.Profile;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class InviteRegistrationServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(InviteRegistrationServlet.class
            .getSimpleName());

    private static final long serialVersionUID = 1L;
    
    private JdoInviteDao jdoInvite = new JdoInviteDao();
    private JdoProfileDao jdoProfile = new JdoProfileDao();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }
    
    private void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        logger.info("registering invitation : " + token);
        
        //This is copied from the ServiceImpl!!!
        Invite invite = jdoInvite.getByToken(token);
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String userId = user.getUserId();
        if(invite != null) {
            Profile senderProfile = jdoProfile.getByUserId(invite.getSenderUserId());
            jdoProfile.save(senderProfile);
            
            Profile receiverProfile = jdoProfile.getByUserId(userId);
            if(receiverProfile == null) {
                receiverProfile = new Profile();
            }
            receiverProfile.setNickname(userId);
            receiverProfile.setUserId(userId);
            jdoProfile.save(receiverProfile);
        }
        
        invite.setReceiverUserId(user.getEmail().replace("@gmail.com", ""));
        invite.setUsedDate(new Date());
        jdoInvite.save(invite);
        resp.sendRedirect("/index.jsp");
    }

}

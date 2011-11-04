package com.devarticles.cms.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.giago.appengine.commons.util.RequestUtils;

public abstract class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static RequestUtils requestUtils = new RequestUtils();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
    
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String relativePath = process(req);
        getServletContext().getRequestDispatcher(relativePath).forward(req, resp);
    }

    protected abstract String process(HttpServletRequest req);

    protected void setAttribute(ServletRequest request, String key, Object value) {
        request.setAttribute(key, value);
    }
    
    protected boolean isMobile(HttpServletRequest request) {
		return requestUtils.isMobile(request);
    }
    
}

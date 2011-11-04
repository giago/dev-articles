package com.devarticles.cms.server.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.giago.appengine.commons.filter.AbstractBaseFilter;
import com.giago.appengine.commons.util.RequestUtils;

public class MobileFilter extends AbstractBaseFilter {

	private static final String M_INDEX = "m_index.jsp";
	private static RequestUtils requestUtils = new RequestUtils();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		if(requestUtils.isMobile(req)) {
			res.sendRedirect("/" + M_INDEX);
			return;
		}
		chain.doFilter(request, response);
	}
	
	@Override
	protected void execute(ServletRequest request) {
		//not called
	}

}
package com.devarticles.cms.server.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class ContentLanguage extends BodyTagSupport {

    private static final long serialVersionUID = 1L;
    
    @Override
    public int doAfterBody() throws JspException {
        BodyContent bc = getBodyContent();
        String body = bc.getString();
        bc.clearBody();
        try {
            getPreviousOut().print(transform(body));
         } catch (IOException e) {
            throw new JspTagException("TransformTag: " + e.getMessage());
         }
        return SKIP_BODY;
    }
    
    private String transform(String body) {
        return "Trasformed! " + body;
    }

}

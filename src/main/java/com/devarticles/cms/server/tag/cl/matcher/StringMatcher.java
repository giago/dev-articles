
package com.devarticles.cms.server.tag.cl.matcher;

import com.devarticles.cms.server.tag.cl.tree.ItemNode;

public interface StringMatcher {
    
    class DoNotMatch extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
    
    DoNotMatch DO_NOT_MATCH_EXCEPTION = new DoNotMatch();

    ItemNode match(String token);
}

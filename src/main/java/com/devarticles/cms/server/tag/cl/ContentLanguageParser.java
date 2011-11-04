package com.devarticles.cms.server.tag.cl;

import java.util.ArrayList;
import java.util.List;

import com.devarticles.cms.server.tag.cl.matcher.ListStringMatcher;
import com.devarticles.cms.server.tag.cl.matcher.StringMatcher;
import com.devarticles.cms.server.tag.cl.tree.ItemNode;

public class ContentLanguageParser {

    private List<StringMatcher> matchers = new ArrayList<StringMatcher>(); {
        matchers.add(new ListStringMatcher());        
    }
    
    public ItemNode parse(String token) {
        ItemNode tree = new ItemNode(token);
        return parseTree(tree);
    }

    private ItemNode parseTree(ItemNode tree) {
        String token = tree.getToken();
        if(token == null) {
            return tree;
        }
        for(StringMatcher matcher : matchers) {
            try {
                ItemNode node = matcher.match(token);
                tree.add(node);
            } catch(StringMatcher.DoNotMatch t) {
                //I'm using exception to turn down 
                //the complexity of the code... don't want
                //to manage nulls
                //anyway the exception is static and 
                //costs as a return
            }
        }
        return tree;
    }

}

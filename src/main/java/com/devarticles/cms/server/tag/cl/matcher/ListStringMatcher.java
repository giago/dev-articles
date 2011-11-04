package com.devarticles.cms.server.tag.cl.matcher;

import com.devarticles.cms.server.tag.cl.tree.ItemNode;
import com.devarticles.cms.server.tag.cl.tree.ListItemNode;

public class ListStringMatcher implements StringMatcher {
    
    private static final String FIRST_OPERATOR = "-";
    private static final String SECOND_OPERATOR = ":";
    private static final String THIRD_OPERATOR = ";";
    
    @Override
    public ItemNode match(String token) {
        int firstOperatorIndex = getFirstOperatorIndex(token);
        int secondOperatorIndex = getSecondOperatorIndex(token, firstOperatorIndex);
        int thirdOperatorIndex = getThirdOperatorIndex(token, secondOperatorIndex);
        
        String title = token.substring(firstOperatorIndex, secondOperatorIndex);
        title = title.replace(FIRST_OPERATOR, "");
        title = title.trim();
        String content = token.substring(secondOperatorIndex, thirdOperatorIndex);
        content = content.replace(SECOND_OPERATOR, "");
        content = content.trim();
        
        ListItemNode node = new ListItemNode(token);
        node.setTitleItem(new ItemNode(title));
        node.setContentItem(new ItemNode(content));
        return node;
    }
    
    private int getFirstOperatorIndex(String token) {
        int index = token.indexOf(FIRST_OPERATOR);
        if(index == -1) {
            throw DO_NOT_MATCH_EXCEPTION;
        }
        return index;
    }

    private int getSecondOperatorIndex(String token, int previousIndex) {
        int index = token.indexOf(SECOND_OPERATOR, previousIndex);
        if(index == -1) {
            throw DO_NOT_MATCH_EXCEPTION;
        }
        return index;
    }
    
    private int getThirdOperatorIndex(String token, int previousIndex) {
        int index = token.indexOf(THIRD_OPERATOR, previousIndex);
        if(index == -1) {
            index = token.indexOf(FIRST_OPERATOR, previousIndex);
            if(index == -1) {
                throw DO_NOT_MATCH_EXCEPTION;
            }
        }
        return index;
    }
}

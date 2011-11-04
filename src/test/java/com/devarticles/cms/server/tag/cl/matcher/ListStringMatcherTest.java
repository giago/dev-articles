package com.devarticles.cms.server.tag.cl.matcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;

import com.devarticles.cms.server.tag.cl.tree.ItemNode;
import com.devarticles.cms.server.tag.cl.tree.ListItemNode;

public class ListStringMatcherTest {

    private ListStringMatcher matcher = new ListStringMatcher();
    
    @Test
    public void shouldCheckMatchReturnTrueIfThereIsAProperList() {
        String content = "- point 1 : content 1;";
        
        assertNotNull(matcher.match(content));
    }

    @Ignore
    @Test(expected = StringMatcher.DoNotMatch.class)
    public void shouldReturnNullInCaseOfANonMatchingStringMissingEnd() {
        String content = "- point 1 : content 1 ";
        assertNull(matcher.match(content));
    }
    
    @Ignore
    @Test(expected = StringMatcher.DoNotMatch.class)
    public void shouldReturnNullInCaseOfANonMatchingStringMissingDivider() {
        String content = "- point 1  content 1;";
        assertNull(matcher.match(content));
    }
    
    @Ignore
    @Test(expected = StringMatcher.DoNotMatch.class)
    public void shouldReturnNullInCaseOfANonMatchingStringMissingStart() {
        String content = " point 1 : content 1;";
        assertNull(matcher.match(content));
    }
    
    @Ignore
    @Test
    public void shouldCheckMatchReturnTrueIfThereIsAProperListWithNotClosingChar() {
        String content = "- point 1 : content 1 - ";
        
        assertNotNull(matcher.match(content));
    }
    
    @Ignore
    @Test
    public void shouldGetSomeItemNodeOutOfTheContent() {
        String content = "- point 1 : content 1 - ";
        
        ListItemNode node = (ListItemNode)matcher.match(content);
        
        assertNotNull(node);
        ItemNode titleNode = node.getTitleItem();
        assertNotNull(titleNode);
        assertEquals("point 1", titleNode.getToken());
        assertEquals("point 1", titleNode.getTransformedToken());
        
        ItemNode contentNode = node.getContentItem();
        assertNotNull(node);
        assertEquals("content 1", contentNode.getToken());
        assertEquals("content 1", contentNode.getTransformedToken());
    }
    
    
}

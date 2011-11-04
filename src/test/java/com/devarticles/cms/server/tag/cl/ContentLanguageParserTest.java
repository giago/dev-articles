package com.devarticles.cms.server.tag.cl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.devarticles.cms.server.tag.cl.tree.ItemNode;

public class ContentLanguageParserTest {
    
    private ContentLanguageParser parser = new ContentLanguageParser();
    
    @Ignore
    @Test
    public void shouldGetMeAEmptyTreeEvenOnNullContent() {
        ItemNode tree = parser.parse(null);
        
        assertNotNull("I was expecting empty tree, not null!", tree);
        assertTrue("I was expecting empty tree, not with content : " + tree.dump(), tree.isEmpty());
    }
    
    @Ignore
    @Test
    public void shouldGetMeATreeOfItems() {
        String content = "- point 1 : content 1 - point 2 : content 2";
        
        ItemNode tree = parser.parse(content);
        assertNotNull("I was expecting empty tree, not null!", tree);
        
        List<ItemNode> nodes = tree.children();
        assertNotNull("There should be there two nodes but the list is null", nodes);
    } 

}
